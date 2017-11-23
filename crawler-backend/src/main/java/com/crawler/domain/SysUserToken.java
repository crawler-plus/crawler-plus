package com.crawler.domain;

/**
 * 用户token entity
 */
public class SysUserToken {

    private int userId; // 用户id

    private String token; // 用户token

    private String ip; // 登录ip地址

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
