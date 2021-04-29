package com.yuseven.service.impl;

import com.yuseven.enums.YesOrNoEnum;
import com.yuseven.mapper.UserAddressMapper;
import com.yuseven.pojo.UserAddress;
import com.yuseven.pojo.bo.AddressBO;
import com.yuseven.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/18 23:13
 * @Version v1.0
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        return userAddressMapper.select(ua);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        // 1. 校验当前用户是否存在地址，没有就设置成默认地址
        Integer isDefault = 0;
        List<UserAddress> userAddresses = this.queryAll(addressBO.getUserId());
        if (userAddresses == null || userAddresses.isEmpty()) {
            isDefault = 1;
        }
        // 2. 保存地址数据
        UserAddress ua = new UserAddress();
        BeanUtils.copyProperties(addressBO, ua);
        String addressId = sid.nextShort();
        ua.setId(addressId);
        ua.setIsDefault(isDefault);
        ua.setCreatedTime(new Date());
        ua.setUpdatedTime(new Date());
        userAddressMapper.insert(ua);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressBO.getAddressId());
        pendingAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress ua = new UserAddress();
        ua.setId(addressId);
        ua.setUserId(userId);
        userAddressMapper.delete(ua);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 1. 查询默认地址，设置为非默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNoEnum.YES.type);
        List<UserAddress> queryAddressList = userAddressMapper.select(queryAddress);
        for (UserAddress userAddress : queryAddressList) {
            userAddress.setIsDefault(YesOrNoEnum.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(userAddress);
        }
        // 2. 根据address id 设置为默认地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNoEnum.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String userAddressId) {
        UserAddress singleUserAddress = new UserAddress();
        singleUserAddress.setUserId(userId);
        singleUserAddress.setId(userAddressId);
        return userAddressMapper.selectOne(singleUserAddress);
    }
}
