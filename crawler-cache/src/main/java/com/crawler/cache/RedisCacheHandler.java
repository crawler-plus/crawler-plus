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
    public void clearCacheByKey(String key) throws Exception {
        redisTemplate.delete(key);
    }

    @Override
    public void clearCacheByKeys(List<String> keys) throws Exception {
        redisTemplate.delete(keys);
    }

    @Override
    public void expireByKey(String key, long timeout, TimeUnit tu) throws Exception {
        redisTemplate.expire(key, timeout, tu);
    }

    @Override
    public void setCache(String key, String value) throws Exception {
        redisConfiguration.valueOperations(redisTemplate).set(key, value);
    }

    @Override
    public Object getCache(String key) throws Exception {
        return redisConfiguration.valueOperations(redisTemplate).get(key);
    }

    @Override
    public void appendToCache(String key, String value) throws Exception {
        redisConfiguration.valueOperations(redisTemplate).append(key, value);
    }
}
