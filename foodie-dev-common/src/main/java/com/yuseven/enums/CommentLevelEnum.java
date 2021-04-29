package com.yuseven.enums;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/16 20:22
 * @Version v1.0
 */
public enum CommentLevelEnum {

    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");

    public final Integer type;
    public final String value;

    CommentLevelEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
