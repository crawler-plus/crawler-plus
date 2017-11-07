package com.crawler.components;

import com.crawler.domain.SysUser;
import com.crawler.domain.TokenEntity;
import com.crawler.exception.TokenInvalidException;
import com.crawler.service.api.UserService;
import com.crawler.util.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证token公共类
 */
@Component
public class CheckToken {

    @Autowired
    private CrawlerProperties crawlerProperties;

    @Autowired
    private UserService userService;

    public void checkToken(TokenEntity te) {
        // token不合法标识
        boolean tokenInvalidFlag = false;
        // 首先判断这个用户是否存在
        SysUser sysUser = new SysUser();
        sysUser.setId(Integer.parseInt(te.getUid()));
        int userCount = userService.checkUserExists(sysUser);
        // 如果用户不存在
        if(userCount < 1) {
            tokenInvalidFlag = true;
        }else {
            if(StringUtils.isEmpty(te.getUid()) || StringUtils.isEmpty(te.getTimestamp()) || StringUtils.isEmpty(te.getToken())) {
                tokenInvalidFlag = true;
            }else {
                TokenEntity tokenEntity = TokenUtils.createUserToken(te.getUid(), Long.parseLong(te.getTimestamp()), crawlerProperties.getUserTokenKey());
                if(!StringUtils.equals(tokenEntity.getToken(), te.getToken())) {
                    tokenInvalidFlag = true;
                }else {
                    long now = System.currentTimeMillis();
                    // 得到3H的millis
                    long threeHoursMillis = 1000 * 3600 * 3;
                    if(now - Long.parseLong(te.getTimestamp()) > threeHoursMillis) {
                        tokenInvalidFlag = true;
                    }
                }
            }
        }
        // 如果token不合法
        if(tokenInvalidFlag) {
            throw new TokenInvalidException("invalid token");
        }
    }
}
