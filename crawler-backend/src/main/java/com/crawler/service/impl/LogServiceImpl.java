package com.crawler.service.impl;

import com.crawler.dao.LogMapper;
import com.crawler.domain.SysLog;
import com.crawler.service.api.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void logAdd(SysLog sysLog) {
        logMapper.logAdd(sysLog);
    }

    @Override
    public List<SysLog> listAll(SysLog sysLog) {
        return logMapper.listAll(sysLog);
    }

    @Override
    public int getLogCount(SysLog sysLog) {
        return logMapper.getLogCount(sysLog);
    }

    @Override
    public void delete() {
        logMapper.delete();
    }
}
