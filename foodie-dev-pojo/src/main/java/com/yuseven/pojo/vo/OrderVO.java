package com.yuseven.pojo.vo;

/**
 * @author Yu Qifeng
 * @date 2021/3/25 22:19
 * @version v1.0
 */
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}
