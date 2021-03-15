package com.yuseven.service;

import com.yuseven.pojo.Category;
import com.yuseven.pojo.vo.CategoryVo;
import com.yuseven.pojo.vo.NewItemsVo;

import java.util.List;

/**
 * 主页分类菜单服务层接口
 *
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:46
 * @Version v1.0
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类ID 查询子分类信息
     * @param rootCatId
     * @return
     */
    List<CategoryVo> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每一个分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    public List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId);
}
