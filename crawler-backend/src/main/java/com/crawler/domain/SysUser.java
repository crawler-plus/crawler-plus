package com.crawler.domain;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户entity
 */
public class SysUser extends BaseEntity {

    // 自增ID
    private int id;

    // 登录名称
    @NotBlank(message = "{sysUser.loginAccount.not.null}", groups = {First.class, Second.class})
    private String loginAccount;

    // 用户姓名
    @NotBlank(message = "{sysUser.name.not.null}", groups = {First.class, Third.class})
    private String name;

    // 密码
    @NotBlank(message = "{sysUser.password.not.null}", groups = {Second.class, Third.class})
    private String password;

    // 用户角色字符串，用逗号分割
    private String userRoleStr;

    // 图片验证码
    private String captcha;

    // sys_user表的版本号（乐观锁）
    private int version;

    // 是否简单修改用户信息（如果来自用户管理，为0代表否，如果来自自服务管理，为1代表是）
    private int simpleUpdate;

    // 是否为注册用户来的还是系统用户添加的，如果是注册用户为0，系统用户添加的用户为1
    private int addUserFrom;

    public interface First {}

    public interface Second {}

    public interface Third {}

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

    public int getSimpleUpdate() {
        return simpleUpdate;
    }

    public void setSimpleUpdate(int simpleUpdate) {
        this.simpleUpdate = simpleUpdate;
    }

    public int getAddUserFrom() {
        return addUserFrom;
    }

    public void setAddUserFrom(int addUserFrom) {
        this.addUserFrom = addUserFrom;
    }
}
