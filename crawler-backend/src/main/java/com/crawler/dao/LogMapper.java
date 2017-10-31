package com.crawler.dao;

import com.crawler.domain.SysLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Log mapper
 */
@Repository
public interface LogMapper {

    /**
     * 新增系统log
     * @param sysLog
     */
    void logAdd(SysLog sysLog);

    /**
     * 根据条件查找系统log
     * @param sysLog
     * @return
     */
    List<SysLog> listAll(SysLog sysLog);

    /**
     * 根据条件查找系统log数量
     * @param sysLog
     * @return
     */
    int getLogCount(SysLog sysLog);

    /**
     * 删除所有系统log
     */
    void delete();
}