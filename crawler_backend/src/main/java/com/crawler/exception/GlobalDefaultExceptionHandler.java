package com.crawler.exception;

import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public BaseEntity runtimeExceptionHandler(RuntimeException e) {
        if(logger.isWarnEnabled()) {
            logger.warn(e.getMessage());
        }
        BaseEntity be = new BaseEntity();
        be.setMsgCode(Const.MESSAGE_CODE_ERROR);
        be.setContent("发生异常，异常信息为:" + e.getMessage());
        return be;
    }

    /**
     * TokenInvalidException异常页面控制
     * @param e
     * @return
     */
    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="token invalid")
    public BaseEntity tokenInvalidException(TokenInvalidException e) {
        if(logger.isWarnEnabled()) {
            logger.warn(e.getMessage());
        }
        BaseEntity be = new BaseEntity();
        be.setMsgCode(Const.MESSAGE_CODE_INVALID_TOKEN);
        be.setContent("发生异常，异常信息为:" + e.getMessage());
        return be;
    }

    /**
     * CrawlerException异常页面控制
     * @param e
     * @return
     */
    @ExceptionHandler(CrawlerException.class)
    public BaseEntity exceptionHandler(CrawlerException e) {
        if(logger.isWarnEnabled()) {
            logger.warn("发生异常，异常信息为:" + e.getMessage());
        }
        BaseEntity be = new BaseEntity();
        be.setMsgCode(Const.MESSAGE_CODE_ERROR);
        be.setContent("发生异常，异常信息为:" + e.getMessage());
        return be;
    }

}
