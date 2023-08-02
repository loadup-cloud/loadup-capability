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
import org.apache.commons.lang3.StringUtils;

public enum Base64HeaderEnum implements IEnum {
    /**
     * png
     */
    PNG("data:image/png;base64,", "png image"),
    /**
     * gif
     */
    GIF("data:image/gif;base64,", "gif image"),
    /**
     * jpg
     */
    JPG("data:image/jpeg;base64", "jpg/jpeg image"),

    ;

    private final String code;
    private final String description;

    Base64HeaderEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Base64HeaderEnum getByCode(String code) {
        return Arrays.stream(Base64HeaderEnum.values()).filter(v -> StringUtils.equals(v.getCode(), code)).findFirst().orElse(null);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}