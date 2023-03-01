package com.github.loadup.capability.common.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class ToStringUtils {
    static {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
    }

    public static String toString(Object object) {
        return ToStringBuilder.reflectionToString(object);
    }
}
