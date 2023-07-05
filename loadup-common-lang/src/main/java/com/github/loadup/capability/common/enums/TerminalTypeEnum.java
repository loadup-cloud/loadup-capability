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

public enum TerminalTypeEnum implements IEnum {

    /**
     * APP
     */
    APP("APP", "APP客户端"),

    /**
     * WEB
     */
    WEB("WEB", "PC浏览器终端"),

    /**
     * WAP
     */
    WAP("WAP", "H5浏览器终端"),

    /**
     * IT系统终端
     */
    SYSTEM("SYSTEM", "系统终端"),

    /**
     * PC客户端终端
     */
    PC("PC", "PC客户端"),

    ;

    /**
     * 终端类型代码
     */
    private final String code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 构造器
     *
     * @param code        终端类型代码
     * @param description 描述
     */
    private TerminalTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code 终端类型代码
     * @return 终端类型枚举
     */
    public static TerminalTypeEnum getByCode(String code) {

        return Arrays.stream(values()).filter(terminalTypeEnum -> terminalTypeEnum.getCode().equals(code)).findFirst().orElse(null);
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
