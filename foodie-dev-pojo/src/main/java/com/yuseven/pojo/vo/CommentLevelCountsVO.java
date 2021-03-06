package com.yuseven.pojo.vo;

/**
 * 用于展示商品评价数的VO
 *
 * @Author Yu Qifeng
 * @Date 2021/3/16 20:09
 * @Version v1.0
 */
public class CommentLevelCountsVO {

    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;

    public CommentLevelCountsVO() {
    }

    public CommentLevelCountsVO(Integer totalCounts, Integer goodCounts, Integer normalCounts, Integer badCounts) {
        this.totalCounts = totalCounts;
        this.goodCounts = goodCounts;
        this.normalCounts = normalCounts;
        this.badCounts = badCounts;
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }
}
