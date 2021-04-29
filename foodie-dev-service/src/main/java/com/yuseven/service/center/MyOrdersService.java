package com.yuseven.service.center;

import com.yuseven.pojo.Orders;
import com.yuseven.pojo.vo.OrderStatusCountsVO;
import com.yuseven.utils.PagedGridResult;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/30 20:00
 */
public interface MyOrdersService {

    PagedGridResult queryMyOrders(String userId,
                                  Integer orderStatus,
                                  Integer page,
                                  Integer pageSize);

    /**
     * 订单发运状态更新
     * @param orderId
     */
    void updateDeliverOrderStatsu(String orderId);

    /**
     * 查询我的订单
     * @param userId
     * @param orderId
     * @return
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 删除我的订单
     * @param orderId
     * @return
     */
    boolean updateRecieveOrderStatus(String orderId);

    /**
     * 删除我的订单 (逻辑删除)
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteMyOrder(String userId, String orderId);

    /**
     * 查询用户订单数
     * @param userId
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 获取分页订单动向信息
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
