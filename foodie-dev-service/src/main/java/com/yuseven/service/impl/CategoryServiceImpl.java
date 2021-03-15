package com.yuseven.service.impl;

import com.yuseven.mapper.CategoryMapper;
import com.yuseven.mapper.CategoryMapperCustom;
import com.yuseven.pojo.Category;
import com.yuseven.pojo.vo.CategoryVo;
import com.yuseven.pojo.vo.NewItemsVo;
import com.yuseven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:48
 * @Version v1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVo> getSubCatList(Integer rootCatId) {
        List<CategoryVo> subCatList = categoryMapperCustom.getSubCatList(rootCatId);
        return subCatList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
