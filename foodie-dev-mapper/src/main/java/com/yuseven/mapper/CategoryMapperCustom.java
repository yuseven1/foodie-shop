package com.yuseven.mapper;

import com.yuseven.pojo.vo.CategoryVo;
import com.yuseven.pojo.vo.NewItemsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    List<CategoryVo> getSubCatList(Integer rootCatId);

    List<NewItemsVo> getSixNewItemsLazy(@Param("paramMap") Map<String, Object> map);
}