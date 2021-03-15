package com.yuseven.service;

import com.yuseven.pojo.Carousel;

import java.util.List;

/**
 * 轮播图数据处理服务层
 *
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:46
 * @Version v1.0
 */
public interface CarouselService {

    /**
     *查询轮播图列表
     *
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
