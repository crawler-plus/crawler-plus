package com.crawler.dao;

import com.crawler.domain.SysCaptcha;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统验证码mapper
 */
@Repository
public interface SysCaptchaMapper {

    /**
     * 增加系统验证码到表
     * @param sysCaptcha
     */
    void addSysCaptcha(SysCaptcha sysCaptcha);

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