package com.yuseven.controller.center;

import com.yuseven.config.resource.FileUpload;
import com.yuseven.controller.BasicController;
import com.yuseven.pojo.Users;
import com.yuseven.pojo.bo.center.CenterUsersBO;
import com.yuseven.service.center.CenterUserService;
import com.yuseven.utils.CookieUtils;
import com.yuseven.utils.JSONResult;
import com.yuseven.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/28 15:48
 */
@RestController
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RequestMapping("userInfo")
public class ConterUserController extends BasicController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId")String userId,
            @RequestBody @Valid CenterUsersBO centerUsersBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 判断 BindingResult 是否保存错误的验证信息，如果有，则直接 return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return JSONResult.errorMap(errorMap);
        }
        Users userResult = centerUserService.updateUserInfo(userId, centerUsersBO);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 后续要改，增加令牌Toekn，会整合进redis，分布式会话

        return JSONResult.ok();
    }

    /**
     * 用于获取前端数据验证结果信息
     * @param result
     * @return
     */
    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> fieldErrorList = result.getFieldErrors();
        for (FieldError error : fieldErrorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField,errorMsg);
        }
        return map;
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


    @ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "POST")
    @PostMapping("uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId")String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
            MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 定义图像保存地址
//        String faceSpace = IMAGE_USER_FACE_LOCATION;
        String faceSpace = fileUpload.getImageUserFaceLocation();
        // 区分每个用户的图像位置
        String uploadPathPrefix = File.separator + userId;
        // 开始文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                // 获取上传文件的名称
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    String[] fileNameArr = fileName.split("\\.");
                    // 获取文件后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    // 判断文件后缀，是否是图片文件
                    if (!suffix.equalsIgnoreCase("png")
                            && !suffix.equalsIgnoreCase("jpg")
                            && !suffix.equalsIgnoreCase("jpeg")
                            ) {
                        return JSONResult.errorMsg("图片格式不正确！");
                    }
                    // 文件名称重组
                    String newFileName = "face-" + userId + "." + suffix;
                    // 上传的头像最终保存的位置
                    String finalFacePath = faceSpace + uploadPathPrefix + File.separator + newFileName;
                    // web访问的地址
                    uploadPathPrefix += ("/" + newFileName);
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return JSONResult.errorMsg("文件不能为空");
        }
        // 获取服务器图片地址
        String imageServerUrl = fileUpload.getImageServerUrl();
        // 由于浏览器可能存在缓存的情况，可以加上时间戳保证图片及时更新
        String finalServerUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + System.currentTimeMillis();

        Users userResult = centerUserService.updateUserFace(userId, finalServerUrl);
        // 抹掉私密信息
        userResult = setNullProperty(userResult);
        // 设置新的用户信息cookies
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 后续要改，增加令牌Toekn，会整合进redis，分布式会话

        return JSONResult.ok(userResult);
    }

}
