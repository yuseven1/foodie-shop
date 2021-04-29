package com.yuseven.pojo.vo;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/25 22:13
 * @Version v1.0
 */
public class MerchantOrdersVO {

    private String merchantOrderId;     // 商户订单号
    private String merchantUserId;      // 商户方的发起用户的用户主键
    private Integer amount;             // 实际支付总金额
    private Integer payMethod;          // 支付方式：1-微信 2-支付宝
    private String returnUrl;           // 支付成功后的回调地址

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
