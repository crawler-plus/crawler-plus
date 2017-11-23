package com.crawler.dao;

import com.crawler.domain.SysUserToken;
import org.springframework.stereotype.Repository;

/**
 * 用户token mapper
 */
@Repository
public interface UserTokenMapper {

    /**
     * 新增用户token
     * @param sysUserToken
     */
    void tokenAdd(SysUserToken sysUserToken);

    /**
     * 根据token删除数据
     * @param token
     */
    void deleteByToken(String token);

    /**
     * 根据token获得数据
     * @param token
     * @return
     */
    SysUserToken getByToken(String token);
}