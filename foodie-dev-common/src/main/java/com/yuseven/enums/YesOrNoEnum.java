package com.yuseven.enums;

/**
 * 是否 枚举
 * @Author Yu Qifeng
 * @Date 2021/2/22 23:15
 * @Version v1.0
 */
public enum YesOrNoEnum {
    NO(0,"否"),
    YES(1,"是");

    public final Integer type;
    public final String value;

    YesOrNoEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
