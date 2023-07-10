package com.github.loadup.capability.common.enums;

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
