package com.github.loadup.capability.common.resultcode;

/*-
 * #%L
 * loadup-common-lang
 * %%
 * Copyright (C) 2022 - 2023 loadup_cloud
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

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
