package com.wzh.myapp.common;

/**
 * @author 75654
 * @date 2024/5/9 11:27
 */
public enum UserEnum {

    GENDER_MALE("male"),
    GENDER_FEMALE("female"),
    GENDER_OTHER("other"),
    USER_SALT("wang"),
    ;

    private final String value;

    UserEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
