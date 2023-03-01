package com.github.loadup.capability.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum GenderEnum implements IEnum {
    MAIL("MAIL", "男"),
    FEMALE("FEMALE", "女"),
    UNKNOWN("UNKNOWN", "未知"),

    ;

    private final String code;
    private final String description;

    GenderEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static GenderEnum getByCode(String code) {
        return Arrays.stream(GenderEnum.values())
                .filter(v -> StringUtils.equals(v.getCode(), code))
                .findFirst().orElse(null);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
