package com.github.loadup.capability.common.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 报警日志，不需要输出整个异常堆栈，通常是以摘要或者概要的信息描述出现<br>
 * 具备一定的结构化规则，能够通过monitor完成日志解析，实现邮件或者短信方式报警<br>
 *
 * 结构化规则要求：1 不能使用换行，保证一行日志就是一次报警
 *
 * @author peng.lanqp
 * @version $Id: AlertLogUtil.java, v 0.1 2014-5-19 上午10:02:58 peng.lanqp Exp $
 */
public final class AlertLogUtil {

    /**
     * alertLogger，sofa框架默认生成的
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
     * <p>输出格式：  <code>[sofaId]objs...</code>
     *
     * @param message 报警日志上下文信息描述，尽量带上业务特征
     */
    public static void alert(Object... message) {
        LogUtils.error(logger, message);
    }

    /**
     * 记录报警日志并输出到日志文件：common-critical.log
     * <p>输出格式：  <code>[sofaId][messageName]objs...</code>
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
     * <p>alert输出格式：  <code>[sofaId][messageName]objs...</code>
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
