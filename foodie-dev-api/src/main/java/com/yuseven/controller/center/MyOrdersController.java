package com.yuseven.controller.center;

import com.yuseven.controller.BasicController;
import com.yuseven.pojo.Orders;
import com.yuseven.pojo.vo.OrderStatusCountsVO;
import com.yuseven.service.center.CenterUserService;
import com.yuseven.service.center.MyOrdersService;
import com.yuseven.utils.JSONResult;
import com.yuseven.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/28 15:18
 */
@Api(value = "center - 用户中心-我的订单", tags = {"用户中心我的订单相关的接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BasicController {

    @Autowired
    private MyOrdersService myOrdersService;
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("query")
    public JSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId")String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam("orderStatus")Integer orderStatus,
            @ApiParam(name = "page", value = "第几页", required = false)
            @RequestParam("page")Integer page,
            @ApiParam(name = "pageSize", value = "每页显示条目数", required = false)
            @RequestParam("pageSize")Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空！");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    // 商家发货后，因无后端，所以该接口仅用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("deliver")
    public JSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam("orderId") String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }
        myOrdersService.updateDeliverOrderStatsu(orderId);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam("orderId") String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId") String userId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        Orders myOrder = myOrdersService.queryMyOrder(userId, orderId);
        if (myOrder == null) {
            return JSONResult.errorMsg("用户订单不存在");
        }
        boolean result = myOrdersService.updateRecieveOrderStatus(orderId);
        if (!result) {
            return JSONResult.errorMsg("订单确认收获失败");
        }

        return JSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam("orderId") String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId") String userId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        boolean result = myOrdersService.deleteMyOrder(userId, orderId);
        if (!result) {
            return JSONResult.errorMsg("订单删除失败！");
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户中心-查询用户订单数", notes = "用户中心-查询用户订单数", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public JSONResult statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId") String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        OrderStatusCountsVO orderStatusCounts = myOrdersService.getOrderStatusCounts(userId);
        return JSONResult.ok(orderStatusCounts);
    }

    @ApiOperation(value = "用户中心-查询用户订单动向", notes = "用户中心-查询用户订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public JSONResult trend(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId") String userId,
            @ApiParam(name = "page", value = "第几页", required = false)
            @RequestParam("page")Integer page,
            @ApiParam(name = "pageSize", value = "每页显示条目数", required = false)
            @RequestParam("pageSize")Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult grid = myOrdersService.getOrdersTrend(userId, page, pageSize);
        return JSONResult.ok(grid);
    }

}
