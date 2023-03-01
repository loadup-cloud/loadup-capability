package com.github.loadup.capability.common.resultcode;

import com.github.loadup.capability.common.response.ResultCode;
import com.github.loadup.capability.common.response.ResultStatusEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum CommonResultCodeEnum implements ResultCode {
    SUCCESS(ResultStatusEnum.SUCCESS, "Success"),
    UNKNOWN_EXCEPTION(ResultStatusEnum.UNKNOWN, "Unknown Error"),
    PARAM_ILLEGAL(ResultStatusEnum.FAIL, "Parameter Illegal"),
    PROCESS_FAIL(ResultStatusEnum.FAIL, "Process Fail"),

    INVALID_API(ResultStatusEnum.FAIL, "Invalid API"),
    INVALID_CODE(ResultStatusEnum.FAIL, "Invalid Code"),
    INVALID_CLIENT(ResultStatusEnum.FAIL, "Invalid Client"),
    INVALID_SIGNATURE(ResultStatusEnum.FAIL, "Invalid Signature"),

    METHOD_NOT_SUPPORTED(ResultStatusEnum.FAIL, "HTTP Method Not Support"),
    MEDIA_NOT_SUPPORTED(ResultStatusEnum.FAIL, "Media Type Not Support"),
    BUSINESS_NOT_SUPPORTED(ResultStatusEnum.FAIL, "Business Not Support"),

    KEY_NOT_FOUND(ResultStatusEnum.FAIL, "Key Not Found"),
    ACCESS_DENIED(ResultStatusEnum.FAIL, "Access Deny"),

    ;

    private String status;
    private String message;

    CommonResultCodeEnum(ResultStatusEnum status, String message) {
        this.status = status.getCode();
        this.message = message;
    }

    public static CommonResultCodeEnum getByCode(String code) {
        return Arrays.stream(CommonResultCodeEnum.values())
                .filter(resultStatusEnum -> StringUtils.equals(resultStatusEnum.getCode(), code))
                .findFirst().orElse(null);
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
