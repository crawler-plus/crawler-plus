package com.crawler.service;

import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HystrixClientFallback implements RPCApi {

    private static final Logger logger = LoggerFactory.getLogger(HystrixClientFallback.class);

    @Override
    public void writeCaptchaCodeToRedis(String key, String value) {
        LoggerUtils.printExceptionLogger(logger, "服务不可用！请检查服务器状态！");
    }
}
