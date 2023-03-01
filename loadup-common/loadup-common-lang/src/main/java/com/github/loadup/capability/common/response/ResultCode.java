package com.github.loadup.capability.common.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface ResultCode {

    @NotBlank
    @Size(max = 64)
    String getCode();

    @NotBlank
    @Size(max = 2)
    String getStatus();

    @NotBlank
    @Size(max = 256)
    String getMessage();
}
