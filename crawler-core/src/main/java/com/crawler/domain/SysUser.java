package com.crawler.domain;

/**
 * 用户entity
 */
public class SysUser extends BaseEntity {

    // 自增ID
    private int id;

    // 登录名称
    private String loginAccount;

    // 用户姓名
    private String name;

    // 密码
    private String password;

    // 用户角色字符串，用逗号分割
    private String userRoleStr;

    // 图片验证码
    private String captcha;

    // sys_user表的版本号（乐观锁）
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRoleStr() {
        return userRoleStr;
    }

    public void setUserRoleStr(String userRoleStr) {
        this.userRoleStr = userRoleStr;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
