package com.yuseven.service.impl;

import com.yuseven.mapper.CarouselMapper;
import com.yuseven.pojo.Carousel;
import com.yuseven.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:48
 * @Version v1.0
 */
@Service
public class CarouselServiceImpl  implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
