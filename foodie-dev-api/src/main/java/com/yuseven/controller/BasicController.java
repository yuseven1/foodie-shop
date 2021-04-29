package com.yuseven.controller;

import org.springframework.stereotype.Controller;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/16 22:31
 * @Version v1.0
 */
@Controller
public class BasicController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    // 微信支付成功 --> 支付中心  --> 天天吃货平台
    //                          --> 回调通知的url
    public String payReturnUrl = "http://192.168.196.128:8088/orders/notifyMerchantOrderPaid";

    // 支付中心的调用地址
//    public String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    public String paymentUrl = "payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    // 用户上传图像的位置
    public static final String IMAGE_USER_FACE_LOCATION = "C:\\Users\\Yu Qifeng\\Desktop\\foodie-center\\images\\foodie\\faces";
}
