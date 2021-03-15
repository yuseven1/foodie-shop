package com.yuseven.service;

import com.yuseven.pojo.bo.UserBo;
import com.yuseven.pojo.Users;

/**
 * 用户表数据服务层接口
 */
public interface UserService {

    /**
     * 判断用户是否存在
     * @param username  用户名
     * @return  true存在，false不存在
     */
    boolean queryUsernameIsExist (String username);

    /**
     * 用户创建并返回创建的用户对象
     * @param userBo 前端接收数据封装对象
     * @return
     */
    Users createUser(UserBo userBo);

    /**
     * 检索用户名和密码是否匹配
     * @param Username  用户名
     * @param password  密码
     * @return  检索的用户表信息，不存在则返回null
     */
    public Users queryUserForLogin(String username, String password);
}
