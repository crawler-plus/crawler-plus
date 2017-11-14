package com.crawler.util;

import org.slf4j.Logger;

/**
 * 日志工具类
 */
public class LoggerUtils {

    /**
     * 打印异常日志
     * @param logger
     * @param e
     */
    public static void printExceptionLogger(Logger logger, Exception e) {
        if(logger.isWarnEnabled()) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * 打印日志(info 级别)
     * @param logger
     * @param message
     */
    public static void printLogger(Logger logger, String message) {
        if(logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    /**
     * 打印日志(debug 级别)
     * @param logger
     * @param message
     */
    public static void printDebugLogger(Logger logger, String message) {
        if(logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }
}
