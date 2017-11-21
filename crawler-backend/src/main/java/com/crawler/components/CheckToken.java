package com.crawler.components;

import com.crawler.domain.SysUser;
import com.crawler.domain.TokenEntity;
import com.crawler.exception.SecurityException;
import com.crawler.service.api.UserService;
import com.crawler.util.TokenUtils;
import com.xiaoleilu.hutool.date.SystemClock;
import com.xiaoleilu.hutool.util.StrUtil;
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
        // 用户id
        String uid = te.getUid();
        // 时间戳
        String timeStamp = te.getTimestamp();
        // token
        String token = te.getToken();
        TokenEntity tokenEntity = TokenUtils.createUserToken(uid, Long.parseLong(timeStamp), crawlerProperties.getUserTokenKey());
        if(!StrUtil.equals(tokenEntity.getToken(), token)) {
            tokenInvalidFlag = true;
        }else {
            // 判断用户是否已经退出系统
            SysUser sysUserByUserId = userService.getSysUserByUserId(Integer.parseInt(uid));
            String userToken = sysUserByUserId.getLoginToken();
            // 如果用户token已被删除
            if(!StrUtil.equals(userToken, token)) {
                tokenInvalidFlag = true;
            }else {
                long now = SystemClock.now();
                // 得到3H的millis
                long threeHoursMillis = 1000 * 3600 * 3;
                // token有效期只有3小时，过期自动失效
                if(now - Long.parseLong(timeStamp) > threeHoursMillis) {
                    tokenInvalidFlag = true;
                }
            }
        }
        // 如果token不合法
        if(tokenInvalidFlag) {
            throw new SecurityException("insecurity access");
        }
    }
}
