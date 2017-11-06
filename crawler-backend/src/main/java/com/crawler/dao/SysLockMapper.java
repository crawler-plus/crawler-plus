package com.crawler.dao;

import com.crawler.domain.SysLock;
import org.springframework.stereotype.Repository;

/**
 * 系统锁mapper
 */
@Repository
public interface SysLockMapper {

    /**
     * 得到系统锁数据
     * @return
     */
    SysLock getSysLock();

    /**
     * 更新系统锁表数据
     * @param sysLock
     */
    void updateSysLock(SysLock sysLock);
}