package com.crawler.components;

import com.crawler.domain.BaseEntity;
import com.crawler.exception.CrawlerException;
import com.crawler.exception.SecurityException;
import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_ERROR;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_SECURITY_INVALID;

/**
 * 全局异常处理Controller
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Runtime异常页面控制
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="server error")
    public BaseEntity runtimeExceptionHandler(RuntimeException e) {
        LoggerUtils.printExceptionLogger(logger, e);
        BaseEntity be = new BaseEntity();
        be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
        be.setContent("发生系统异常，异常信息为:" + e.getMessage());
        return be;
    }

    /**
     * SecurityException异常页面控制
     * @param e
     * @return
     */
    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="insecurity access")
    public BaseEntity securityExceptionHandler(SecurityException e) {
        LoggerUtils.printExceptionLogger(logger, e);
        BaseEntity be = new BaseEntity();
        be.setMsgCode(MESSAGE_CODE_SECURITY_INVALID.getCode());
        be.setContent("发生权限异常，异常信息为:" + e.getMessage());
        return be;
    }

    /**
     * CrawlerException异常页面控制
     * @param e
     * @return
     */
    @ExceptionHandler(CrawlerException.class)
    public BaseEntity exceptionHandler(CrawlerException e) {
        LoggerUtils.printExceptionLogger(logger, e);
        BaseEntity be = new BaseEntity();
        be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
        be.setContent("发生业务异常，异常信息为:" + e.getMessage());
        return be;
    }

}
