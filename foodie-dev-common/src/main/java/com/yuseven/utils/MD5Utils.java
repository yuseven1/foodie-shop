package com.yuseven.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具类
 *
 * @Author Yu Qifeng
 * @Date 2021/2/22 22:48
 * @Version v1.0
 */
public class MD5Utils {

    public static String getMD5Str(String valueStr) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(valueStr.getBytes());
        String newStr = Base64.encodeBase64String(digest);
        return newStr;
    }
}
