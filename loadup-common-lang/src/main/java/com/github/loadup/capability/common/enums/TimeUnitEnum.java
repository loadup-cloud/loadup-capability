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

public enum TimeUnitEnum implements IEnum {
    /**
     * SECOND
     */
    SECOND("S", "SECOND"),
    /**
     * MINUTE
     */
    MINUTE("I", "MINUTE"),
    /**
     * HOUR
     */
    HOUR("H", "HOUR"),
    /**
     * DAY
     */
    DAY("D", "DAY"),
    /**
     * MONTH
     */
    MONTH("D", "MONTH"),
    /**
     * YEAR
     */
    YEAR("Y", "YEAR"),
    ;
    private String code;
    private String description;

    TimeUnitEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static TimeUnitEnum getByCode(String code) {

        return Arrays.stream(values()).filter(eunm -> eunm.getCode().equals(code)).findFirst().orElse(null);
    }

}
