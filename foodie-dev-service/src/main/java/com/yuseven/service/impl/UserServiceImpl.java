package com.yuseven.service.impl;

import com.yuseven.service.UserService;
import com.yuseven.enums.SexEnum;
import com.yuseven.mapper.UsersMapper;
import com.yuseven.pojo.bo.UserBo;
import com.yuseven.pojo.Users;
import com.yuseven.utils.DateUtils;
import com.yuseven.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Override
    // 声明该方法的事务传播级别 required表示如果当前上下文中已经有事务了，就加入到事务中，没有则新建一个事务，这个级别通常能满足处理大多数的业务场景。
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username",username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUser(UserBo userBo) {
        Users user = new Users();
        String userId = sid.nextShort();
        user.setId(userId);
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setNickname(userBo.getUsername()); // 默认用户昵称为用户名
        user.setFace(USER_FACE); // 默认图像地址
        user.setBirthday(DateUtils.stringToDate("1990-01-01")); // 设置默认生日
        user.setSex(SexEnum.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)  // 查询使用supports就可以了
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password", password);
        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }
}
