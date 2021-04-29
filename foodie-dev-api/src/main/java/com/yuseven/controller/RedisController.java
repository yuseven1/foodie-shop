package com.yuseven.controller;

import com.yuseven.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/4/29 19:30
 */
@ApiIgnore
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisOperator redisOperator;

    @GetMapping("/set")
    public Object set(String key, String value) {
        redisOperator.set(key, value);
        return "ok";
    }

    @GetMapping("/get")
    public Object get(String key) {
        Object value = redisOperator.get(key);
        return value;
    }

    @GetMapping("/delete")
    public Object delete(String key) {
        redisOperator.del(key);
        return "ok~";
    }
}
