package com.yuseven.controller;

import com.yuseven.enums.OrderStatusEnum;
import com.yuseven.enums.PayMethod;
import com.yuseven.pojo.OrderStatus;
import com.yuseven.pojo.bo.ShopcartBo;
import com.yuseven.pojo.bo.SubmitOrderBO;
import com.yuseven.pojo.vo.MerchantOrdersVO;
import com.yuseven.pojo.vo.OrderVO;
import com.yuseven.service.OrderService;
import com.yuseven.utils.CookieUtils;
import com.yuseven.utils.JSONResult;
import com.yuseven.utils.JsonUtils;
import com.yuseven.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:58
 * @Version v1.0
 */
@Api(value = "订单相关", tags = {"订单相关接口"})  // 改文档ui的controller名称
@RestController
@RequestMapping("orders")
public class OrdersController extends BasicController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult list(@RequestBody SubmitOrderBO submitOrderBO,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type) {
            return JSONResult.errorMsg("支付方式不支持");
        }

        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId());
        if (StringUtils.isBlank(shopcartJson)) {
            return JSONResult.errorMsg("购物车数据不正确");
        }
        List<ShopcartBo> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBo.class);
        // 1. 创建订单
        OrderVO orderVO = orderService.createOreder(submitOrderBO, shopcartList);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已提交的商品信息

        // 清理覆盖现有的redis中的购物车数据
        shopcartList.removeAll(orderVO.getToBeRemovedShopcartList());
        redisOperator.set(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId(), JsonUtils.objectToJson(shopcartList));
        // TODO 整合 redis 后，完善购物车中的已结算商品清除，并且同步到前端的cookies
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartList), true);
        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);
        // 为了方便测试购买，所有的支付金额都统一设置成1分钱
        merchantOrdersVO.setAmount(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId","imooc");
        headers.add("password","imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<JSONResult> jsonResultResponseEntity = restTemplate.postForEntity(paymentUrl, entity, JSONResult.class);
        JSONResult body = jsonResultResponseEntity.getBody();
        if (body.getStatus() != 200) {
            return JSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }
        return JSONResult.ok(orderId);
    }

    /**
     * 支付成功后，接收微信支付回调通知的接口
     * @param merchantOrderId
     * @return
     */
    @PostMapping("notifyMerchantOrderPaid")
    public int notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    /**
     * 查询订单支付状态
     * @param orderId
     * @return
     */
    @PostMapping("getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }
}
