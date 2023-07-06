
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
 * 捕捉到异常的时候，我们通常会使用<code>logger.error("xxxx",e)</code>方式打印日常堆栈日志<br>
 * 但是这种方式会造成错误日志打印两遍，精益求精，日志也追求极致，错误日志尽量使用本工具类输出。
 *
 * @author peng.lanqp
 * @author wuhui
 * @version $Id: ExceptionLogUtil.java, v 0.2 2014-5-14 下午6:12:15 peng.lanqp Exp $
 */
public final class ExceptionLogUtils {

    /**
     * errorLogger，sofa框架默认生成的
     */
    private static final Logger logger = LoggerFactory.getLogger("ERROR");

    /**
     * 禁用构造函数
     */
    private ExceptionLogUtils() {
        // 禁用构造函数
    }

    /**
     * 捕捉错误日志并输出到日志文件：common-error.log
     *
     * @param e       异常堆栈
     * @param message 错误日志上下文信息描述，尽量带上业务特征
     */
    public static void error(Throwable e, Object... message) {
        LogUtils.error(logger, e, message);
    }

    /**
     * 捕捉错误日志并输出到日志文件：common-error.log
     *
     * @param message 错误日志上下文信息描述，尽量带上业务特征
     */
    public static void error(Object... message) {
        logger.error(LogUtils.getLogString(message));
    }

}
