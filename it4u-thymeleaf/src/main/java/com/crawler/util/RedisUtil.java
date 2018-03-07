package com.crawler.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

     //Redis服务器IP
     private static String ADDR = "172.31.141.221";

    //Redis的端口号
     private static int PORT = 6379;

     private static JedisPool jedisPool = null;

    /**
     * 初始化连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            jedisPool = new JedisPool(config, ADDR, PORT);
            } catch (Exception e) {
                e.printStackTrace();
            }
      }

    /**
     * 获取jedis实例
     * @return
     */
    public static Jedis getJedis() {
        try {
        if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void returnResource(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
}
