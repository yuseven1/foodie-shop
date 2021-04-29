package com.yuseven.controller;

import com.yuseven.pojo.*;
import com.yuseven.pojo.vo.CommentLevelCountsVO;
import com.yuseven.pojo.vo.ItemInfoVO;
import com.yuseven.pojo.vo.ShopcartVO;
import com.yuseven.service.ItemService;
import com.yuseven.utils.JSONResult;
import com.yuseven.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:58
 * @Version v1.0
 */
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})  // 改文档ui的controller名称
@RestController
@RequestMapping("items")
public class ItemsController extends BasicController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable("itemId") String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品不存在");
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemParams = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO(item, itemImgs, itemSpecs, itemParams);
        return JSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级数", notes = "查询商品评价等级数", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam("itemId") String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品不存在");
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return JSONResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam("itemId") String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam("level") Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam("page") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页条数", required = false)
            @RequestParam("pageSize") Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品不存在");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(
            @ApiParam(name = "keywords", value = "搜索关键字", required = true)
            @RequestParam("keywords") String keywords,
            @ApiParam(name = "sort", value = "排序查询条件", required = false)
            @RequestParam("sort") String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam("page") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页条数", required = false)
            @RequestParam("pageSize") Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg("搜索条件为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "通过三级分类id，搜索商品列表", notes = "通过三级分类id，搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult catItems(
            @ApiParam(name = "catId", value = "三级分类id", required = true)
            @RequestParam("catId") Integer catId,
            @ApiParam(name = "sort", value = "排序查询条件", required = false)
            @RequestParam("sort") String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam("page") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页条数", required = false)
            @RequestParam("pageSize") Integer pageSize) {
        if (catId == null) {
            return JSONResult.errorMsg("搜索的分类序列号不能为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    // 用于用户长时间未登陆网站，刷新购物车中的数据，主要是商品价格，类似京东淘宝
    @ApiOperation(value = "通过规格ids查询最新的商品数据", notes = "通过规格ids查询最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public JSONResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规则ids", required = true, example = "1001,1003,1005")
            @RequestParam("itemSpecIds") String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return JSONResult.ok(shopcartVOS);
    }

}
