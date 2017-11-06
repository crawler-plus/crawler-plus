package com.crawler.components;

import com.crawler.domain.TokenEntity;
import com.crawler.exception.TokenInvalidException;
import com.crawler.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证token公共类
 */
@Component
public class CheckToken {

    @Autowired
    private CrawlerProperties crawlerProperties;

    public void checkToken(TokenEntity te) {
        // token不合法标识
        boolean tokenInvalidFlag = false;
        // 得到前台传过来的用户id
        String uid = te.getUid();
        // 得到前台传过来的sign
        String token = te.getToken();
        // 得到前台传过来的时间
        long timestamp = Long.parseLong(te.getTimestamp());
        long now = System.currentTimeMillis();
        // 得到30分钟的millis
        long halfHourMillis = 1000 * 60 * 30;
        // 如果token超过30分钟
        if(now - timestamp > halfHourMillis) {
            tokenInvalidFlag = true;
        }else {
            TokenEntity tokenEntity = TokenUtils.createUserToken(uid, timestamp, crawlerProperties.getUserTokenKey());
            if(!tokenEntity.getToken().equals(token)) {
                tokenInvalidFlag = true;
            }
        }
        // 如果token不合法
        if(tokenInvalidFlag) {
            throw new TokenInvalidException("invalid token");
        }
    }
}
