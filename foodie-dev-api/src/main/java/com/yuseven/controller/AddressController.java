package com.yuseven.controller;

import com.yuseven.pojo.UserAddress;
import com.yuseven.pojo.bo.AddressBO;
import com.yuseven.service.AddressService;
import com.yuseven.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.utils.MobileEmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/2 19:58
 * @Version v1.0
 */
@Api(value = "地址相关", tags = {"地址相关接口"})  // 改文档ui的controller名称
@RestController
@RequestMapping("address")
public class AddressController {
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户ID，查询收货地址", notes = "根据用户ID，查询收货地址", httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam("userId")String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        List<UserAddress> userAddresses = addressService.queryAll(userId);
        return JSONResult.ok(userAddresses);
    }

    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestBody AddressBO addressBO) {
        JSONResult jsonResult = checkAddress(addressBO);
        if (jsonResult.getStatus() != 200) {
            return jsonResult;
        }
        addressService.addNewUserAddress(addressBO);
        return JSONResult.ok();
    }

    private JSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人名称不能太长");
        }
        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("收货人手机号码不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("手机号码长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JSONResult.errorMsg("手机号码格式不正确");
        }
        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province)
                || StringUtils.isBlank(city)
                || StringUtils.isBlank(district)
                || StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("请完善收货地址信息！");
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "更新收货地址", notes = "更新收货地址", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getUserId())) {
            return JSONResult.errorMsg("修改地址错误：AddressId不能为空");
        }
        JSONResult jsonResult = checkAddress(addressBO);
        if (jsonResult.getStatus() != 200) {
            return jsonResult;
        }
        addressService.updateUserAddress(addressBO);
        return JSONResult.ok();
    }

    @ApiOperation(value = "删除用户收货地址", notes = "删除用户收货地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(
            @RequestParam String userId,
            @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }
        addressService.deleteUserAddress(userId, addressId);
        return JSONResult.ok();
    }

    @ApiOperation(value = "设置用户收货地址为默认收货地址", notes = "设置用户收货地址为默认收货地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public JSONResult setDefault(
            @RequestParam String userId,
            @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }
        addressService.updateUserAddressToBeDefault(userId, addressId);
        return JSONResult.ok();
    }
}
