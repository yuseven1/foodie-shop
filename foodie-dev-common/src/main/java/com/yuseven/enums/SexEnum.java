package com.yuseven.enums;

/**
 * 性别枚举
 * 0 女
 * 1 男
 * 2 保密
 *
 * @Author Yu Qifeng
 * @Date 2021/2/22 23:15
 * @Version v1.0
 */
public enum SexEnum {
    women(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final Integer type;
    public final String value;

    SexEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
