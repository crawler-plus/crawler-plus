package com.crawler.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取yml中自定义配置
 */
@Component
@ConfigurationProperties(prefix = "crawler")
public class CrawlerProperties {

    // MD5加密默认salt
    private String md5Salt;

    // 用户默认密码
    private String defaultPassword;

    // 登录系统是否使用验证码
    private boolean loginUseCaptcha;

    // 注册系统是否使用验证码
    private boolean registerUseCaptcha;

    // 用户token生成密钥
    private String userTokenKey;

    public String getMd5Salt() {
        return md5Salt;
    }

    public void setMd5Salt(String md5Salt) {
        this.md5Salt = md5Salt;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public boolean isLoginUseCaptcha() {
        return loginUseCaptcha;
    }

    public void setLoginUseCaptcha(boolean loginUseCaptcha) {
        this.loginUseCaptcha = loginUseCaptcha;
    }

    public boolean isRegisterUseCaptcha() {
        return registerUseCaptcha;
    }

    public void setRegisterUseCaptcha(boolean registerUseCaptcha) {
        this.registerUseCaptcha = registerUseCaptcha;
    }

    public String getUserTokenKey() {
        return userTokenKey;
    }

    public void setUserTokenKey(String userTokenKey) {
        this.userTokenKey = userTokenKey;
    }
}
