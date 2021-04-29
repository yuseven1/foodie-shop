package com.yuseven.enums;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/22 22:36
 * @Version v1.0
 */
public enum PayMethod {

    WEIXIN(1, "微信"),
    ALIPAY(2, "支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
