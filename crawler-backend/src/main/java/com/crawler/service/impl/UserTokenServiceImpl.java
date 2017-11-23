package com.crawler.service.impl;

import com.crawler.dao.UserTokenMapper;
import com.crawler.domain.SysUserToken;
import com.crawler.service.api.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
   private UserTokenMapper userTokenMapper;

    @Override
    public void deleteByToken(String token) {
        userTokenMapper.deleteByToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUserToken getByToken(String token) {
        return userTokenMapper.getByToken(token);
    }
}
