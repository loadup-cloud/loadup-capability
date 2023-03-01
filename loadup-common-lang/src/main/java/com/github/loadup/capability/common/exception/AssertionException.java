package com.github.loadup.capability.common.exception;

import com.github.loadup.capability.common.response.ResultCode;
import com.github.loadup.capability.common.resultcode.CommonResultCodeEnum;

public class AssertionException extends RuntimeException {

    private ResultCode resultCode;

    private AssertionException() {
    }

    public AssertionException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public AssertionException(CommonResultCodeEnum resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public AssertionException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public AssertionException(CommonResultCodeEnum resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public AssertionException(String msg) {
        super(msg);
    }

    public AssertionException(Throwable cause) {
        super(cause);
    }

    public AssertionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
