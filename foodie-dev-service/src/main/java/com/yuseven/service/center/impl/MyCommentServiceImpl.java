package com.yuseven.service.center.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuseven.enums.YesOrNoEnum;
import com.yuseven.mapper.ItemsCommentsMapperCustom;
import com.yuseven.mapper.OrderItemsMapper;
import com.yuseven.mapper.OrderStatusMapper;
import com.yuseven.mapper.OrdersMapper;
import com.yuseven.pojo.OrderItems;
import com.yuseven.pojo.OrderStatus;
import com.yuseven.pojo.Orders;
import com.yuseven.pojo.bo.center.OrderItemsCommentBO;
import com.yuseven.pojo.vo.MyCommentVO;
import com.yuseven.service.center.MyCommentService;
import com.yuseven.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/31 0:11
 */
@Service
public class MyCommentServiceImpl extends BaseService implements MyCommentService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String userId, String orderId, List<OrderItemsCommentBO> list) {
        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO orderItemsCommentBO : list) {
            orderItemsCommentBO.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", list);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表订单为已评价
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNoEnum.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 更新订单状态留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComment(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",  userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setterPageGrid(list, page);
    }
}
