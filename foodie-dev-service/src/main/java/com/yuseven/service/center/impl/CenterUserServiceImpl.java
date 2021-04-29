package com.yuseven.service.center.impl;

import com.yuseven.enums.OrderStatusEnum;
import com.yuseven.enums.YesOrNoEnum;
import com.yuseven.mapper.OrderStatusMapper;
import com.yuseven.mapper.OrdersMapper;
import com.yuseven.mapper.UsersMapper;
import com.yuseven.pojo.OrderStatus;
import com.yuseven.pojo.Orders;
import com.yuseven.pojo.Users;
import com.yuseven.pojo.bo.center.CenterUsersBO;
import com.yuseven.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/28 15:23
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        // 查询处用户过滤掉隐私信息
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUsersBO centerUsersBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUsersBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return this.queryUserInfo(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return this.queryUserInfo(userId);
    }
}
