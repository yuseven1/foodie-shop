package com.yuseven.pojo.vo;

import java.util.List;

/**
 * BO  前端传给后端
 * VO 后端传给前端
 *
 * 二级分类VO
 *
 * @Author Yu Qifeng
 * @Date 2021/3/11 23:14
 * @Version v1.0
 */
public class CategoryVo {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;
    // 三级分类的 vo 集合
    private List<SubCategoryVo> subCatList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public List<SubCategoryVo> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<SubCategoryVo> subCatList) {
        this.subCatList = subCatList;
    }
}
