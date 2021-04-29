package com.yuseven.service.center.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuseven.enums.OrderStatusEnum;
import com.yuseven.enums.YesOrNoEnum;
import com.yuseven.mapper.OrderStatusMapper;
import com.yuseven.mapper.OrdersMapper;
import com.yuseven.mapper.OrdersMapperCustom;
import com.yuseven.pojo.OrderStatus;
import com.yuseven.pojo.Orders;
import com.yuseven.pojo.vo.MyOrdersVO;
import com.yuseven.pojo.vo.OrderStatusCountsVO;
import com.yuseven.service.center.MyOrdersService;
import com.yuseven.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/30 20:02
 */
@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        if (orderStatus != null) {
            paramMap.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> myOrdersVOList = ordersMapperCustom.queryMyOrders(paramMap);
        return setterPageGrid(myOrdersVOList, page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatsu(String orderId) {
        OrderStatus updateOrderStaus = new OrderStatus();
        updateOrderStaus.setOrderId(orderId);
        updateOrderStaus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrderStaus.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        orderStatusMapper.updateByExampleSelective(updateOrderStaus, example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        Orders myQueryOrder = new Orders();
        myQueryOrder.setUserId(userId);
        myQueryOrder.setId(orderId);
        myQueryOrder.setIsDelete(YesOrNoEnum.NO.type);

        return ordersMapper.selectOne(myQueryOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateRecieveOrderStatus(String orderId) {
        OrderStatus myUpdateOrderStatus = new OrderStatus();
        myUpdateOrderStatus.setOrderId(orderId);
        myUpdateOrderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        myUpdateOrderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int result = orderStatusMapper.updateByExampleSelective(myUpdateOrderStatus, example);
        return result == 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteMyOrder(String userId, String orderId) {
        // 这里的删除并非是清除掉数据库数据，而是将订单记录更新为逻辑删除状态

        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNoEnum.YES.type);
        updateOrder.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",orderId);
        criteria.andEqualTo("userId",userId);

        int result = ordersMapper.updateByExampleSelective(updateOrder, example);

        return result == 1;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("orderStatus",OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(paramMap);

        paramMap.put("orderStatus",OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(paramMap);

        paramMap.put("orderStatus",OrderStatusEnum.WAIT_RECEIVE.type);
        int waitRecieveCounts = ordersMapperCustom.getMyOrderStatusCounts(paramMap);

        paramMap.put("orderStatus",OrderStatusEnum.SUCCESS.type);
        paramMap.put("isComment",YesOrNoEnum.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(paramMap);

        return new OrderStatusCountsVO(waitPayCounts, waitDeliverCounts, waitRecieveCounts, waitCommentCounts);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        PageHelper.startPage(page,pageSize);
        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(paramMap);

        return setterPageGrid(list, page);
    }
}
