package com.crawler.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存接口
 */
public interface CacheHandler {

    /**
     * 根据一个key删除缓存
     * @param key
     */
    void clearCacheByKey(String key);

    /**
     * 根据多个key删除缓存
     * @param keys
     */
    void clearCacheByKeys(List<String> keys);

    /**
     * 通过key设置缓存的过期时间
     * @param key
     * @param tu
     */
    void expireByKey(String key, long timeout, TimeUnit tu);

    /**
     * 设置缓存
     * @param key
     * @param value
     * @throws Exception
     */
    void setCache(String key, String value);

    /**
     * 设置缓存（指定过期时间）
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    void setCacheWithTimeout(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 从缓存中取出数据
     * @param key
     * @return
     * @throws Exception
     */
    Object getCache(String key);

    /**
     * 往缓存中追加数据
     * @param key
     * @param value
     * @throws Exception
     */
    void appendToCache(String key, String value);
}
