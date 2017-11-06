package com.crawler.service.api;

import com.crawler.domain.SysLock;

/**
 * 系统锁Service
 */
public interface SysLockService {

    /**
     * 得到系统锁表数据
     * @return
     */
    SysLock getSysLock();

    /**
     * 更新系统锁表数据
     * @param sysLock
     */
    void updateSysLock(SysLock sysLock);
}
