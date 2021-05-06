package com.yuseven.controller;

import com.yuseven.pojo.bo.ShopcartBo;
import com.yuseven.utils.JSONResult;
import com.yuseven.utils.JsonUtils;
import com.yuseven.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
public class ShopcartController extends BasicController {

    @Autowired
    private RedisOperator redisOperator;

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
        // 需要额外判断，当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBo> shopcartList = null;
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis中已经有购物车了
            shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBo.class);
            // 判断购物车中是否已有存在商品，如果有的话，count叠加
            boolean isHaving = false;
            for (ShopcartBo sc : shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(shopcartBo.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopcartBo.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopcartList.add(shopcartBo);
            }
        } else {
            // redis 中没有购物车
            shopcartList = new ArrayList<>();
            shopcartList.add(shopcartBo);
        }
        // 覆盖现有 redis 中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartList));

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
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotBlank(shopcartJson)) {
            // 购物车中有数据了
            List<ShopcartBo> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBo.class);
            // 判断购物车是否有商品了，如果有的话则删除
            for (ShopcartBo sc : shopcartList) {
                if (sc.getSpecId().equals(itemSpecId)) {
                    shopcartList.remove(sc);
                    break;
                }
            }
            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartList));
        }

        return JSONResult.ok();
    }
}
