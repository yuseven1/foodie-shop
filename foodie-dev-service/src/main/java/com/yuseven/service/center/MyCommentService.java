package com.yuseven.service.center;

import com.yuseven.pojo.OrderItems;
import com.yuseven.pojo.bo.center.OrderItemsCommentBO;
import com.yuseven.utils.PagedGridResult;

import java.util.List;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/30 20:00
 */
public interface MyCommentService {
    /**
     * 根据订单id，查询关联的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param userId
     * @param orderId
     */
    void saveComments(String userId, String orderId, List<OrderItemsCommentBO> list);

    /**
     * 查询我的评论列表
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyComment(String userId, Integer page, Integer pageSize);
}
