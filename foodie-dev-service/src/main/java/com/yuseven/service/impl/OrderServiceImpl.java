package com.yuseven.service.impl;

import com.yuseven.enums.OrderStatusEnum;
import com.yuseven.enums.YesOrNoEnum;
import com.yuseven.mapper.OrderItemsMapper;
import com.yuseven.mapper.OrderStatusMapper;
import com.yuseven.mapper.OrdersMapper;
import com.yuseven.pojo.*;
import com.yuseven.pojo.bo.ShopcartBo;
import com.yuseven.pojo.bo.SubmitOrderBO;
import com.yuseven.pojo.vo.MerchantOrdersVO;
import com.yuseven.pojo.vo.OrderVO;
import com.yuseven.service.AddressService;
import com.yuseven.service.ItemService;
import com.yuseven.service.OrderService;
import com.yuseven.utils.DateUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.annotation.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/22 22:42
 * @Version v1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOreder(SubmitOrderBO submitOrderBO, List<ShopcartBo> shopcartList) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为 0
        Integer postAmount = 0;
        UserAddress address = addressService.queryUserAddress(userId, addressId);
        // 1. 新订单数据保存
        String orderId = sid.nextShort();
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + "" + address.getCity() + "" + address.getDistrict() + "" + address.getDetail());
//        newOrder.setTotalAmount();
//        newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);

        newOrder.setIsComment(YesOrNoEnum.NO.type);
        newOrder.setIsDelete(YesOrNoEnum.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        // 2. 循环根据itemSpecIds保存订单商品信息
        String[] itemSpecIdArr = itemSpecIds.split(",");
        Integer totalAmount = 0;    // 订单商品原价
        Integer realPayAmount = 0;  // 订单商品优惠后的价格
        List<ShopcartBo> toBeRemovedShopcartList = new ArrayList<>();

        for (String itemSpecId : itemSpecIdArr) {
            // TODO 整合reais后，商品购买的数量重新从redis的购物车中获取
            ShopcartBo cartItem = getBuycountFromShopcart(shopcartList, itemSpecId);
            int buyCount = cartItem.getBuyCounts();
            toBeRemovedShopcartList.add(cartItem);
            // 2.1 根据规格id，查询具体规格信息，主要获取价格数据
            ItemsSpec itemSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCount;
            realPayAmount += itemSpec.getPriceDiscount() * buyCount;
            // 2.2 根据商品id，获取商品信息以及商品图片+
            String itemId = itemSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);
            // 2.3 循环保存订单数据到数据库
            OrderItems subOrderItem = new OrderItems();
            String subOrderId = sid.nextShort();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCount);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);
            // 2.4 在用户提交订单以后，规格表中需要扣除库存
            itemService.discreaseItemSpecStock(itemSpecId, buyCount);
        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);
        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setToBeRemovedShopcartList(toBeRemovedShopcartList);
        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        System.out.println(orderId);
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {
        // 查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> orderStatuses = orderStatusMapper.select(queryOrder);
        for (OrderStatus os : orderStatuses) {
            // 获得订单创建时间
            Date createdTime = os.getCreatedTime();
            // 和当前时间进行对比
            int days = DateUtils.daysBetween(createdTime, new Date());
            if (days >= 1) {
                // 超过一天，关闭订单
                doCloseOrder(os.getOrderId());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId) {
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }

    /**
     * 从redis购物车缓存数据中获取已经加入到购物车的具体商品的购买数量
     * @param shopcartList
     * @param specId
     * @return
     */
    private ShopcartBo getBuycountFromShopcart(List<ShopcartBo> shopcartList, String specId) {
        for (ShopcartBo sc : shopcartList) {
            if (sc.getSpecId().equals(specId)) {
                return sc;
            }
        }
        return null;
    }
}
