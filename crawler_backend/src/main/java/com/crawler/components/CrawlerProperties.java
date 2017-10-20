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
}
