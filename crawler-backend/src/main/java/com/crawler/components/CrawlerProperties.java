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

    // vsftpd-nginx默认host
    private String captchaFtpServerHost;

    // vsftpd默认用户名
    private String captchaFtpServerUserName;

    // vsftpd默认密码
    private String captchaFtpServerPassword;

    // vsftpd图片存放路径
    private String captchaFtpServerUrl;

    // vsftpd-nginx默认port
    private String captchaFtpServerPort;

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

    public String getCaptchaFtpServerHost() {
        return captchaFtpServerHost;
    }

    public void setCaptchaFtpServerHost(String captchaFtpServerHost) {
        this.captchaFtpServerHost = captchaFtpServerHost;
    }

    public String getCaptchaFtpServerUserName() {
        return captchaFtpServerUserName;
    }

    public void setCaptchaFtpServerUserName(String captchaFtpServerUserName) {
        this.captchaFtpServerUserName = captchaFtpServerUserName;
    }

    public String getCaptchaFtpServerPassword() {
        return captchaFtpServerPassword;
    }

    public void setCaptchaFtpServerPassword(String captchaFtpServerPassword) {
        this.captchaFtpServerPassword = captchaFtpServerPassword;
    }

    public String getCaptchaFtpServerUrl() {
        return captchaFtpServerUrl;
    }

    public void setCaptchaFtpServerUrl(String captchaFtpServerUrl) {
        this.captchaFtpServerUrl = captchaFtpServerUrl;
    }

    public String getCaptchaFtpServerPort() {
        return captchaFtpServerPort;
    }

    public void setCaptchaFtpServerPort(String captchaFtpServerPort) {
        this.captchaFtpServerPort = captchaFtpServerPort;
    }
}
