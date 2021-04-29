package com.yuseven.controller.center;

import com.yuseven.controller.BasicController;
import com.yuseven.enums.YesOrNoEnum;
import com.yuseven.pojo.OrderItems;
import com.yuseven.pojo.Orders;
import com.yuseven.pojo.bo.center.OrderItemsCommentBO;
import com.yuseven.service.center.CenterUserService;
import com.yuseven.service.center.MyCommentService;
import com.yuseven.service.center.MyOrdersService;
import com.yuseven.utils.JSONResult;
import com.yuseven.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/28 15:18
 */
@Api(value = "center - 用户中心评价模块", tags = {"用户中心评价模块相关的接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentController extends BasicController {

    @Autowired
    private MyCommentService myCommentService;
    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("pending")
    public JSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId")String userId,
            @ApiParam(name = "orderId", value = "订单编号", required = true)
            @RequestParam("orderId")String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        Orders myOrder = myOrdersService.queryMyOrder(userId, orderId);
        // 判断该笔订单是否与用户关联
        if (myOrder == null) {
            return JSONResult.errorMsg("用户订单不存在");
        }
        // 判断该笔订单是否已经评价过
        if (myOrder.getIsComment() == YesOrNoEnum.YES.type) {
            return JSONResult.errorMsg("该笔订单已经评价过了！");
        }

        List<OrderItems> result = myCommentService.queryPendingComment(orderId);
        return JSONResult.ok(result);
    }

    @ApiOperation(value = "保存商品评价列表信息", notes = "保存商品评价列表信息", httpMethod = "POST")
    @PostMapping("saveList")
    public JSONResult saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId")String userId,
            @ApiParam(name = "orderId", value = "订单编号", required = true)
            @RequestParam("orderId")String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        Orders myOrder = myOrdersService.queryMyOrder(userId, orderId);
        // 判断该笔订单是否与用户关联
        if (myOrder == null) {
            return JSONResult.errorMsg("用户订单不存在");
        }
        // 判断评论内容
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return JSONResult.errorMsg("评论内容不能为空！");
        }

        myCommentService.saveComments(userId, orderId, commentList);

        return JSONResult.ok();
    }

    @ApiOperation(value = "用户中心-查询我的评论", notes = "用户中心-查询我的评论", httpMethod = "POST")
    @PostMapping("query")
    public JSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId")String userId,
            @ApiParam(name = "page", value = "查询当前的页码数", required = false)
            @RequestParam("page")Integer page,
            @ApiParam(name = "pageSize", value = "每页显示条目数", required = false)
            @RequestParam("pageSize")Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = myCommentService.queryMyComment(userId, page, pageSize);

        return JSONResult.ok(pagedGridResult);
    }

}
