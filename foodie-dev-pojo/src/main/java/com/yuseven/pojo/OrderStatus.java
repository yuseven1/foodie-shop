package com.yuseven.pojo;

import javax.persistence.*;
import java.util.Date;

@Table(name = "order_status")
public class OrderStatus {
    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单状态
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 订单创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 支付成功时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 发货时间
     */
    @Column(name = "deliver_time")
    private Date deliverTime;

    /**
     * 交易成功时间
     */
    @Column(name = "success_time")
    private Date successTime;

    /**
     * 交易关闭时间
     */
    @Column(name = "close_time")
    private Date closeTime;

    /**
     * 留言时间
     */
    @Column(name = "comment_time")
    private Date commentTime;

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单状态
     *
     * @return order_status - 订单状态
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
     *
     * @param orderStatus 订单状态
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取订单创建时间
     *
     * @return created_time - 订单创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createdTime 订单创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取支付成功时间
     *
     * @return pay_time - 支付成功时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付成功时间
     *
     * @param payTime 支付成功时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取发货时间
     *
     * @return deliver_time - 发货时间
     */
    public Date getDeliverTime() {
        return deliverTime;
    }

    /**
     * 设置发货时间
     *
     * @param deliverTime 发货时间
     */
    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    /**
     * 获取交易成功时间
     *
     * @return success_time - 交易成功时间
     */
    public Date getSuccessTime() {
        return successTime;
    }

    /**
     * 设置交易成功时间
     *
     * @param successTime 交易成功时间
     */
    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    /**
     * 获取交易关闭时间
     *
     * @return close_time - 交易关闭时间
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * 设置交易关闭时间
     *
     * @param closeTime 交易关闭时间
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * 获取留言时间
     *
     * @return comment_time - 留言时间
     */
    public Date getCommentTime() {
        return commentTime;
    }

    /**
     * 设置留言时间
     *
     * @param commentTime 留言时间
     */
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }
}