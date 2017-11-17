package com.crawler.domain;

/**
 * 系统log Entity
 */
public class SysLog extends BaseEntity {

    /**
     * 主键id
     */
    private int id;

    /**
     * 登录账户
     */
    private String loginAccount;

    /**
     * 执行时间
     */
    private String executeTime;

    /**
     * 执行类型（1：登录操作，2：登出操作）
     */
    private int typeId;

    /**
     * 执行类型中文显示（登录/登出）
     */
    private String typeName;

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

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
