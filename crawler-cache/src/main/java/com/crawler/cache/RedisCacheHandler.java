package com.crawler.cache;

import com.crawler.components.RedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheHandler implements CacheHandler {

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void clearCacheByKey(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void clearCacheByKeys(List<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    @Override
    public void expireByKey(String key, long timeout, TimeUnit tu) {
        stringRedisTemplate.expire(key, timeout, tu);
    }

    @Override
    public void setCache(String key, String value) {
        redisConfiguration.valueOperations(redisTemplate).set(key, value);
    }

    @Override
    public void setCacheWithTimeout(String key, String value, long timeout, TimeUnit timeUnit) {
        redisConfiguration.valueOperations(redisTemplate).set(key, value, timeout, timeUnit);
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

    public void setCollectionsToList(String key, Object o) {
        redisConfiguration.listOperations(redisTemplate).leftPushAll(key, o);
    }

    public void setValueToList(String key, Object o) {
        redisConfiguration.listOperations(redisTemplate).leftPush(key, o);
    }

    public List getCollectionsValueFromList(String key) {
        return redisConfiguration.listOperations(redisTemplate).range(key, 0, -1);
    }

    public void clearValueFromList(String key) {
        redisTemplate.delete(key);
    }
}
