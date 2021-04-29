package com.yuseven.mapper;

import com.yuseven.my.mapper.MyMapper;
import com.yuseven.pojo.ItemsComments;
import com.yuseven.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    void saveComments(Map<String, Object> map);

    List<MyCommentVO> queryMyComments(@Param("paramMap")Map<String, Object> map);

}