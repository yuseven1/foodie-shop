package com.yuseven.mapper;

import com.yuseven.pojo.vo.ItemCommentVO;
import com.yuseven.pojo.vo.SearchItemsVO;
import com.yuseven.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramMap") Map<String, Object> map);

    public List<ShopcartVO> queryItemsBySpecIds(@Param("paramList")List specIdList);

    public int discreaseItemSpecStock(@Param("specId")String specId,
                                      @Param("pendingCounts")int pendingCounts);
}