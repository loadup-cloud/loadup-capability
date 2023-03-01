package com.github.loadup.capability.common.response;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum ResultStatusEnum {
    SUCCESS("S"),
    FAIL("F"),
    UNKNOWN("U");
    private String code;

    ResultStatusEnum(String code) {
        this.code = code;
    }

    public static ResultStatusEnum getByCode(String code) {
        return Arrays.stream(ResultStatusEnum.values())
                .filter(resultStatusEnum -> StringUtils.equals(resultStatusEnum.getCode(), code))
                .findFirst().orElse(null);
    }

    public String getCode() {
        return code;
    }
}
