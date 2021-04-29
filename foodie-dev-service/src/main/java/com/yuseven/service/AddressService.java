package com.yuseven.service;

import com.yuseven.pojo.UserAddress;
import com.yuseven.pojo.bo.AddressBO;

import java.util.List;

/**
 * 地址相关 服务层
 *
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:46
 * @Version v1.0
 */
public interface AddressService {
    /**
     * 根据用户ID，查询收货地址列表
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 添加新的用户收货地址
     * @param addressBO
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 修改用户收货地址
     * @param addressBO
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id + 地址id, 删除用户收货地址
     * @param userId
     * @param addressId
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 根据用户id + 地址id, 设置用户收货地址为默认地址
     * @param userId
     * @param addressId
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址di，查询用户具体的地址信息
     * @param userId
     * @param userAddressId
     * @return
     */
    UserAddress queryUserAddress(String userId, String userAddressId);
}
