package com.yuseven.controller;

import com.yuseven.pojo.Items;
import com.yuseven.pojo.ItemsImg;
import com.yuseven.pojo.ItemsParam;
import com.yuseven.pojo.ItemsSpec;
import com.yuseven.pojo.bo.ShopcartBo;
import com.yuseven.pojo.vo.ItemInfoVO;
import com.yuseven.service.ItemService;
import com.yuseven.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:58
 * @Version v1.0
 */
@Api(value = "购物车接口", tags = {"购物车接口相关的API"})  // 改文档ui的controller名称
@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam("userId") String userId,
            @RequestBody ShopcartBo shopcartBo,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return  JSONResult.errorMsg("");
        }
        System.out.println(shopcartBo);
        // TODO 前端用户在登录的情况下添加商品到购物车，会同时在后端同步购物车到 redis 缓存

        return JSONResult.ok();
    }

    @ApiOperation(value = "删除购物车商品", notes = "删除购物车商品", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam("userId") String userId,
            @RequestParam("itemSpecId") String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return  JSONResult.errorMsg("参数不能为空");
        }

        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除redis中的购物车数据

        return JSONResult.ok();
    }
}
