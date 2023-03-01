package com.github.loadup.capability.common.response;

public class Result implements ResultCode {

    /**
     * result code
     */
    private String code;

    /**
     * result status
     **/
    private String status;

    /**
     * result message
     */
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
