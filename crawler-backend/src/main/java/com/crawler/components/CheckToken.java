package com.crawler.components;

import com.crawler.domain.TokenEntity;
import com.crawler.exception.TokenInvalidException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 验证token公共类
 */
@Component
public class CheckToken {

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    public void checkToken(TokenEntity te) {
        String uid = te.getUid();
        String token = te.getToken();
        if(StringUtils.isEmpty(uid) || StringUtils.isEmpty(token) || !token.equals(redisConfiguration.valueOperations(redisTemplate).get(te.getUid()))) {
            throw new TokenInvalidException("invalid token");
        }
        // 刷新默认过期时间为30分钟
        redisTemplate.expire(String.valueOf(te.getUid()), 30, TimeUnit.MINUTES);
    }

}
