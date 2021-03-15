package com.yuseven.controller;

import com.yuseven.enums.YesOrNoEnum;
import com.yuseven.pojo.Carousel;
import com.yuseven.pojo.Category;
import com.yuseven.pojo.vo.CategoryVo;
import com.yuseven.pojo.vo.NewItemsVo;
import com.yuseven.service.CarouselService;
import com.yuseven.service.CategoryService;
import com.yuseven.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:58
 * @Version v1.0
 */
@Api(value = "首页", tags = {"首页信息展示的相关接口"})  // 改文档ui的controller名称
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel() {
        List<Carousel> carousels = carouselService.queryAll(YesOrNoEnum.YES.type);
        return JSONResult.ok(carousels);
    }

    /**
     *  首页分类展示需求：
     *  1. 第一次刷新主页查询大分类，渲染展示到页面
     *  2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     *
     */
    @ApiOperation(value = "获取商品（一级分类）", notes = "获取商品（一级分类）", httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats() {
        List<Category> categories = categoryService.queryAllRootLevelCat();
        return JSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCats(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable("rootCatId") Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<CategoryVo> categoryVos = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(categoryVos);
    }

    @ApiOperation(value = "查询首页每一个分类下的6条最新商品数据", notes = "查询首页每一个分类下的6条最新商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable("rootCatId") Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVo> newItemsVos = categoryService.getSixNewItemsLazy(rootCatId);
        return JSONResult.ok(newItemsVos);
    }

}
