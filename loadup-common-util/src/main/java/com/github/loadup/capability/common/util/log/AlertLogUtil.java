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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 报警日志，不需要输出整个异常堆栈，通常是以摘要或者概要的信息描述出现<br>
 * 具备一定的结构化规则，能够通过monitor完成日志解析，实现邮件或者短信方式报警<br>
 *
 * 结构化规则要求：1 不能使用换行，保证一行日志就是一次报警
 */
public final class AlertLogUtil {

    /**
     * alertLogger
     */
    private static final Logger logger = LoggerFactory.getLogger("CRITICAL");

    /**
     * 禁用构造函数
     */
    private AlertLogUtil() {
        // 禁用构造函数
    }

    /**
     * 记录报警日志并输出到日志文件：common-critical.log
     * <p>输出格式：  <code>objs...</code>
     *
     * @param message 报警日志上下文信息描述，尽量带上业务特征
     */
    public static void alert(Object... message) {
        LogUtils.error(logger, message);
    }

    /**
     * 记录报警日志并输出到日志文件：common-critical.log
     * <p>输出格式：  <code>[messageName]objs...</code>
     *
     * @param messageName 报警日志的标题
     * @param message     报警日志上下文信息描述，尽量带上业务特征
     */
    public static void alert(String messageName, Object... message) {
        LogUtils.error(messageName, logger, message);
    }

    /**
     * 非常关键的异常日志，分别在报警日志和错误日志记录（两份日志）<br>
     *
     * 记录报警日志并输出到日志文件：common-critical.log<br>
     * 记录错误日志并输出到日志文件：common-error.log （可以在monitor错误大盘及时发现）
     *
     * @param message 报警日志上下文信息描述，尽量带上业务特征
     */
    public static void error(Object... message) {
        alert(message);
        ExceptionLogUtils.error(message);
    }

    /**
     * 非常关键的异常日志，分别在报警日志和错误日志记录（两份日志）<br>
     *
     * 记录报警日志并输出到日志文件：common-critical.log<br>
     * 记录错误日志并输出到日志文件：common-error.log （可以在monitor错误大盘及时发现）
     *
     * @param e       异常堆栈
     * @param message 报警日志上下文信息描述，尽量带上业务特征
     */
    public static void error(Throwable e, Object... message) {
        alert(message);
        ExceptionLogUtils.error(e, message);
    }

    /**
     * 非常关键的异常日志，分别在报警日志和错误日志记录（两份日志）<br>
     * <p>alert输出格式：  <code>[messageName]objs...</code>
     *
     * 记录报警日志并输出到日志文件：common-critical.log<br>
     * 记录错误日志并输出到日志文件：common-error.log （可以在monitor错误大盘及时发现）
     *
     * @param messageName 报警日志的标题
     * @param message     报警日志上下文信息描述，尽量带上业务特征
     */
    public static void error(String messageName, Object... message) {
        alert(messageName, message);
        ExceptionLogUtils.error(messageName, message);
    }

}
