package com.yuseven.pojo.vo;

/**
 * 三级分类VO
 *
 * @Author Yu Qifeng
 * @Date 2021/3/11 23:18
 * @Version v1.0
 */
public class SubCategoryVo {
    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(Integer subFatherId) {
        this.subFatherId = subFatherId;
    }
}
