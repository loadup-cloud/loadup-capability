package com.github.loadup.capability.common.response;

import com.github.loadup.capability.common.model.DTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class BaseResponse extends DTO {
    @NotNull
    @Valid
    private Result result = null;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
