package com.crawler.components;

import com.crawler.domain.SysUser;
import com.crawler.domain.TokenEntity;
import com.crawler.exception.SecurityException;
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

    void checkToken(TokenEntity te) {
        // token不合法标识
        boolean tokenInvalidFlag = false;
        if(StringUtils.isEmpty(te.getUid()) || StringUtils.isEmpty(te.getTimestamp()) || StringUtils.isEmpty(te.getToken())) {
            tokenInvalidFlag = true;
        }else {
            TokenEntity tokenEntity = TokenUtils.createUserToken(te.getUid(), Long.parseLong(te.getTimestamp()), crawlerProperties.getUserTokenKey());
            if(!StringUtils.equals(tokenEntity.getToken(), te.getToken())) {
                tokenInvalidFlag = true;
            }else {
                // 判断用户是否已经退出系统
                SysUser sysUserByUserId = userService.getSysUserByUserId(Integer.parseInt(te.getUid()));
                String token = sysUserByUserId.getLoginToken();
                // 如果用户token已被删除
                if(!StringUtils.equals(token, te.getToken())) {
                    tokenInvalidFlag = true;
                }else {
                    long now = System.currentTimeMillis();
                    // 得到3H的millis
                    long threeHoursMillis = 1000 * 3600 * 3;
                    // token有效期只有3小时，过期自动失效
                    if(now - Long.parseLong(te.getTimestamp()) > threeHoursMillis) {
                        tokenInvalidFlag = true;
                    }
                }
            }
        }
        // 如果token不合法
        if(tokenInvalidFlag) {
            throw new SecurityException("insecurity access");
        }
    }
}
