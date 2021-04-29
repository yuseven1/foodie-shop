package com.yuseven.pojo.vo;

/**
 * 展示商品搜索列表结果的VO
 * @Author Yu Qifeng
 * @Date 2021/3/17 20:04
 * @Version v1.0
 */
public class SearchItemsVO {
    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price;

    public SearchItemsVO() {
    }

    public SearchItemsVO(String itemId, String itemName, Integer sellCounts, String imgUrl, Integer price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.sellCounts = sellCounts;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(Integer sellCounts) {
        this.sellCounts = sellCounts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
