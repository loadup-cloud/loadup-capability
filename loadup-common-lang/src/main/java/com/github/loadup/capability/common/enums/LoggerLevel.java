package com.github.loadup.capability.common.enums;

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

import java.util.Arrays;

public enum LoggerLevel implements IEnum {

    /**
     * DEBUG
     */
    DEBUG("DEBUG", "debug级别的日志输出"),

    /**
     * INFO
     */
    INFO("INFO", "info级别的日志输出"),

    /**
     * INFO
     */
    WARN("WARN", "warn级别的日志输出"),

    /**
     * ERROR
     */
    ERROR("ERROR", "error级别的日志输出");

    /**
     * 码Code
     */
    private final String code;

    /**
     * 描述
     */
    private final String description;

    LoggerLevel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 通过枚举code获得枚举。
     *
     * @param code 码
     * @return 枚举
     */
    public static LoggerLevel getByCode(String code) {
        return Arrays.stream(values()).filter(each -> each.getCode().equals(code)).findFirst().orElse(null);
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
}
