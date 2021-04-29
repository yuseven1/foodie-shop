package com.yuseven.pojo.vo;

import com.yuseven.pojo.Items;
import com.yuseven.pojo.ItemsImg;
import com.yuseven.pojo.ItemsParam;
import com.yuseven.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品详情VO
 *
 * @Author Yu Qifeng
 * @Date 2021/3/15 21:59
 * @Version v1.0
 */
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

    public ItemInfoVO() {
    }

    public ItemInfoVO(Items item, List<ItemsImg> itemImgList, List<ItemsSpec> itemSpecList, ItemsParam itemParams) {
        this.item = item;
        this.itemImgList = itemImgList;
        this.itemSpecList = itemSpecList;
        this.itemParams = itemParams;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }
}
