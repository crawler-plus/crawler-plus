package com.crawler.cache;

import com.crawler.components.RedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheHandler implements CacheHandler {

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void clearCacheByKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void clearCacheByKeys(List<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public void expireByKey(String key, long timeout, TimeUnit tu) {
        redisTemplate.expire(key, timeout, tu);
    }

    @Override
    public void setCache(String key, String value) {
        redisConfiguration.valueOperations(redisTemplate).set(key, value);
    }

    @Override
    public Object getCache(String key) {
        return redisConfiguration.valueOperations(redisTemplate).get(key);
    }

    @Override
    public void appendToCache(String key, String value) {
        redisConfiguration.valueOperations(redisTemplate).append(key, value);
    }

    public void setValueToSet(String key, String value) {
        redisConfiguration.setOperations(redisTemplate).add(key, value);
    }

    public boolean valueIsMemberInSet(String key, String value) {
        return redisConfiguration.setOperations(redisTemplate).isMember(key, value);
    }
}
