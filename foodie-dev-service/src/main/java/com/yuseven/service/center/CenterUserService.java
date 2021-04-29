package com.yuseven.service.center;

import com.yuseven.pojo.Orders;
import com.yuseven.pojo.Users;
import com.yuseven.pojo.bo.center.CenterUsersBO;
import org.apache.ibatis.annotations.Update;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/28 15:21
 */
public interface CenterUserService {

    /**
     * 根据用户id，查询用户信息
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUsersBO
     */
    Users updateUserInfo(String userId, CenterUsersBO centerUsersBO);

    /**
     * 更新用户头像
     * @param userId
     * @param faceUrl
     * @return
     */
    Users updateUserFace(String userId, String faceUrl);

}
