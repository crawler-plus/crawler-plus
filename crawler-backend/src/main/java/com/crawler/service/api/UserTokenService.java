package com.crawler.service.api;

import com.crawler.domain.SysUserToken;

/**
 * 用户Token Service
 */
public interface UserTokenService {

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
