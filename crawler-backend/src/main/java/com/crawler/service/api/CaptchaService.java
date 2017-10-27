package com.crawler.service.api;

import com.crawler.domain.BaseEntity;

/**
 * 验证码生成service
 */
public interface CaptchaService {

    BaseEntity createCaptcha();
}
