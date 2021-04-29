package com.yuseven.service;

import com.yuseven.pojo.OrderStatus;
import com.yuseven.pojo.bo.SubmitOrderBO;
import com.yuseven.pojo.vo.OrderVO;

/**
 * @author Yu Qifeng
 * @date 2021/3/22 22:40
 * @version v1.0
 */
public interface OrderService {

    /**
     * 创建订单相关信息
     * @param submitOrderBO
     */
    OrderVO createOreder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
