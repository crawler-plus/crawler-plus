package com.crawler.service.api;

import java.util.List;

/**
 * 系统验证码Service
 */
public interface SysCaptchaService {

    /**
     * 列出所有验证码
     * @return
     */
    List<String> listAllSysCaptcha();

    /**
     * 清除所有验证码
     */
    void clearAllSysCaptchas();
}
