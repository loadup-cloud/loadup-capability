package com.github.loadup.capability.common.util.log;

/*-
 * #%L
 * loadup-common-util
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/**
 * 规范化日志打印工具，注意日志的级别选择：<br>
 *
 * <p>
 *   <ol>
 *     <li>DEBUG <b>开发环境</b>应用调试，输出详细的应用状态
 *     <li>INFO <b>生产环境</b>运行状态观察，输出应用生命周期的中<b>正常重要事件</b>
 *     <li>WARN <b>生产环境</b>故障诊断，输出应用中的<b>可预期的异常事件</b>
 *     <li>ERROR <b>生产环境</b>境故障诊断，输出应用中的<b>未预期的异常事件</b>
 *   </ol>
 * </p>
 *
 * <p>
 *
 * @notice <li>ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
 * <li>报警日志记录请使用{@link AlertLogUtil}，只记录一些非常关键的异常信息
 * <li>日志记录支持数组参数，禁止使用“+”拼接入参，全部使用“,”由工具自行拼接
 * </p>
 */
public class LogUtils {

    /**
     * 摘要日志的内容分隔符
     */
    public static final String SEP = ",";

    /**
     * 修饰符
     */
    private static final char RIGHT_TAG = ']';

    /**
     * 修饰符
     */
    private static final char LEFT_TAG = '[';

    /**
     * 打印info日志。
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void info(Logger logger, Object... objs) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(objs));
        }
    }

    /**
     * 打印info日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void info(Logger logger, Throwable e, Object... objs) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(objs), e);
        }
    }

    /**
     * 打印warn日志。
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void warn(Logger logger, Object... objs) {
        logger.warn(getLogString(objs));
    }

    /**
     * 打印warn日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void warn(Logger logger, Throwable e, Object... objs) {
        logger.warn(getLogString(objs), e);
    }

    /**
     * 打印error日志。
     *
     * ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void error(Logger logger, Throwable e, Object... objs) {
        logger.error(getLogString(objs), e);
    }

    /**
     * 打印error日志。
     *
     * ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void error(Logger logger, Object... objs) {
        logger.error(getLogString(objs));
    }

    /**
     * 打印error日志。
     *
     * ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
     *
     * @param messageName 报警日志的标题
     * @param logger      日志对象
     * @param objs        任意个要输出到日志的参数
     */
    public static void error(String messageName, Logger logger, Object... objs) {
        logger.error(getLogString(messageName, objs));
    }

    /**
     * 打印error日志。
     *
     * ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
     *
     * @param messageName 报警日志的标题
     * @param logger      日志对象
     * @param objs        任意个要输出到日志的参数
     */
    public static void alert(String messageName, Logger logger, Object... objs) {
        logger.error(getLogString(messageName, objs));
    }

    /**
     * 打印debug日志。
     *
     * @param logger 日志对象
     * @param objs   任意个要输出到日志的参数
     */
    public static void debug(Logger logger, Object... objs) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(objs));
        }
    }

    /**
     * 打印debug日志。
     *
     * @param logger 日志对象
     * @param e      异常信息
     * @param objs   任意个要输出到日志的参数
     */
    public static void debug(Logger logger, Throwable e, Object... objs) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(objs), e);
        }
    }

    /**
     * 生成输出到日志的字符串
     * <p>输出格式:[messageName]objs...
     *
     * @param messageName 报警日志的标题
     * @param objs        任意个要输出到日志的参数
     * @return 日志字符串
     */
    public static String getLogString(String messageName, Object... objs) {
        StringBuilder log = new StringBuilder();
        log.append(LEFT_TAG);
        log.append(fetchInvokeId()).append(SEP);
        // 预留扩展位
        log.append(SEP).append(SEP).append(RIGHT_TAG);

        log.append(LEFT_TAG).append(messageName).append(RIGHT_TAG);

        if (objs != null) {
            for (Object o : objs) {
                log.append(o);
            }
        }

        return log.toString();
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param objs 任意个要输出到日志的参数
     * @return 日志字符串
     */
    public static String getLogString(Object... objs) {
        StringBuilder log = new StringBuilder();
        log.append(LEFT_TAG);
        log.append(fetchInvokeId()).append(SEP);
        // 预留扩展位
        log.append(SEP).append(SEP).append(RIGHT_TAG);

        if (objs != null) {
            for (Object o : objs) {
                log.append(o);
            }
        }
        return log.toString();
    }

    /**
     * 获取上下文的调用id。
     *
     * @return 调用id
     */
    public static String fetchInvokeId() {
        String traceId = "";// TracerContextUtil.getTraceId();
        return StringUtils.defaultIfBlank(traceId, String.valueOf(Thread.currentThread().getId()));
    }

}
