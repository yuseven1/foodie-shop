package com.yuseven.controller;

import com.yuseven.pojo.Users;
import com.yuseven.pojo.bo.UserBo;
import com.yuseven.service.UserService;
import com.yuseven.utils.CookieUtils;
import com.yuseven.utils.JSONResult;
import com.yuseven.utils.JsonUtils;
import com.yuseven.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("passport")
//@ApiIgnore //swagger2的注解，如果该接口不想被别人看到，可以加上这个注解
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})  // 改文档ui的controller名称
public class PassportController {

    @Autowired
    private UserService usersService;

    @ApiOperation(value = "检查用户名是否存在", notes = "检查用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist (@RequestParam String username) {
        // 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空");
        }
        // 2. 查找注册的用户名是否存在
        boolean isExist = usersService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已存在");
        }
        // 3. 请求成功，用户名没有重复
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBo userBo, HttpServletRequest request, HttpServletResponse response) {
        /**后端接口对参数也需要做校验，这样做的目的是为了防止有人通过接口对系统进行攻击*/
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();
        // 1. 校验用户名和密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return JSONResult.errorMsg("用户名或者密码不能为空！");
        }
        // 2. 校验两次密码是否相同
        if (!StringUtils.equals(password, confirmPassword)) {
            return JSONResult.errorMsg("两次输入的密码不一致！");
        }
        // 3. 校验密码长度是否大于等于6位
        if (password.length()<6) {
            return JSONResult.errorMsg("密码太简单了，请确保密码长度不小于6位！");
        }
        // 4. 校验用户名是否存在
        boolean isExist = usersService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("该用户名已经存在");
        }
        // 5. 实现用户注册
        Users userResult = usersService.createUser(userBo);
        // 6. 注册成功后去除用户隐私数据，并通过cookie返回前端
        userResult = setNullProperty(userResult);
        // 7. 设置cookies
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBo userBo, HttpServletRequest request, HttpServletResponse response) {
        /**后端接口校验*/
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        // 1. 校验用户名和密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用户名或者密码不能为空！");
        }
        // 2. 实现用户登录
        // 2.1 密码加密转换
        try {
            password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 2.2 转换后查询数据库信息
        Users userResult = usersService.queryUserForLogin(username, password);
        // 3. 判断查询结果
        if (userResult == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }
        // 4. 因为数据是要返回给到前端的，所以隐私数据要设置为null
        userResult = setNullProperty(userResult);
        // 5. 设置cookies
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        return JSONResult.ok(userResult);
    }

    /**
     * 返回用户信息去除隐私属性
     * @param userResult
     * @return
     */
    private Users setNullProperty(Users userResult) {
        if (userResult != null) {
            userResult.setPassword(null);
            userResult.setRealname(null);
            userResult.setMobile(null);
            userResult.setUpdatedTime(null);
            userResult.setCreatedTime(null);
        }
        return userResult;
    }

    /**
     * 用户退出登录
     * @param userId
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        // 清除用户相关信息的 cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO  用户退出登录，需要清空购物车
        // TODO 在分布式会话中，需要清除用户数据

        return JSONResult.ok();
    }
}
