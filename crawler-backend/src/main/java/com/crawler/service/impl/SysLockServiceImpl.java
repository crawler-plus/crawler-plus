package com.crawler.service.impl;

import com.crawler.dao.SysLockMapper;
import com.crawler.domain.SysLock;
import com.crawler.service.api.SysLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysLockServiceImpl implements SysLockService {

    @Autowired
    private SysLockMapper sysLockMapper;

    @Override
    @Transactional(readOnly = true)
    public SysLock getSysLock() {
        return sysLockMapper.getSysLock();
    }

    @Override
    public void updateSysLock(SysLock sysLock) {
        sysLockMapper.updateSysLock(sysLock);
    }
}
