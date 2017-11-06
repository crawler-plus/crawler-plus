package com.crawler.domain;

/**
 * 系统锁Entity
 */
public class SysLock {

    private String systemCron; // 系统是否正在运行爬取任务（0：否，1：是）

    private String businessCron; // 用户是否正在运行爬取任务（0：否，1：是）

    public String getSystemCron() {
        return systemCron;
    }

    public void setSystemCron(String systemCron) {
        this.systemCron = systemCron;
    }

    public String getBusinessCron() {
        return businessCron;
    }

    public void setBusinessCron(String businessCron) {
        this.businessCron = businessCron;
    }
}
