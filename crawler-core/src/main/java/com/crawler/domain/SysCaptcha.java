package com.crawler.domain;

/**
 * 系统验证码
 */
public class SysCaptcha {

    private int id; // 主键id

    private String captcha; // 验证码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
