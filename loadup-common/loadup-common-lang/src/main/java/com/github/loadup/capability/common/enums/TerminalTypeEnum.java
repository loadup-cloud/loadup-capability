package com.github.loadup.capability.common.enums;

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
