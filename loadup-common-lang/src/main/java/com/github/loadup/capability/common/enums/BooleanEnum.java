package com.github.loadup.capability.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum BooleanEnum implements IEnum {
    TRUE("Y", "是"),
    FALSE("N", "否");

    private final String code;
    private final String description;

    BooleanEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static BooleanEnum getByCode(String code) {
        return Arrays.stream(BooleanEnum.values())
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
