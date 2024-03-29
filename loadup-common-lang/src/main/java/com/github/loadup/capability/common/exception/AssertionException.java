package com.github.loadup.capability.common.exception;

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
