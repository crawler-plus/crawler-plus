package com.crawler.service;

import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HystrixClientFallback implements RPCApi {

    private static final Logger logger = LoggerFactory.getLogger(HystrixClientFallback.class);

    @Override
    public String checkCaptchaExists(String key) {
        return "";
    }

    @Override
    public void writeUserToken(String tokenId, String token) {
        LoggerUtils.printExceptionLogger(logger, "服务不可用！请检查服务器状态！");
    }

    @Override
    public String getUserToken(String tokenId) {
        return "";
    }

    @Override
    public void deleteUserToken(String tokenId) {
        LoggerUtils.printExceptionLogger(logger, "服务不可用！请检查服务器状态！");
    }

    @Override
    public void writeUserMenuInfoToRedis(Map<String, String> param) {
        LoggerUtils.printExceptionLogger(logger, "服务不可用！请检查服务器状态！");
    }

    @Override
    public String getUserMenuInfo(int userId) {
        return "";
    }

    @Override
    public void deleteUserMenuInfo(int userId) {
        LoggerUtils.printExceptionLogger(logger, "服务不可用！请检查服务器状态！");
    }

    @Override
    public void lockByAdmin() {
        LoggerUtils.printExceptionLogger(logger, "服务不可用！请检查服务器状态！");
    }

    @Override
    public String getLockByAdmin() {
        return "";
    }

    @Override
    public void deleteLockByAdmin() {
        LoggerUtils.printExceptionLogger(logger, "服务不可用！请检查服务器状态！");
    }
}
