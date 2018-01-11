package com.crawler.controller;

import com.crawler.cache.RedisCacheHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisServiceProvider {

    @Autowired
    private RedisCacheHandler redisCacheHandler;

    /**
     * 将验证码写入redis
     */
    @RequestMapping(value = "/writeCaptchaCodeToRedis/{key}/{value}", method = RequestMethod.GET)
    public void writeCaptchaCodeToRedis(@PathVariable("key") String key, @PathVariable("value") String value) {
        redisCacheHandler.setCacheWithTimeout(key, value, 60L, TimeUnit.SECONDS);
    }

    /**
     * 判断验证码是否正确
     */
    @RequestMapping(value = "/checkCaptchaExists/{key}", method = RequestMethod.GET)
    public String checkCaptchaExists(@PathVariable("key") String key) {
        return (String)redisCacheHandler.getCache(key);
    }

    /**
     * 将token写入redis
     */
    @RequestMapping(value = "/writeUserToken/{tokenId}/{token}", method = RequestMethod.GET)
    public void writeUserToken(@PathVariable("tokenId") String tokenId, @PathVariable("token") String token) {
        redisCacheHandler.setCacheWithTimeout(tokenId, token, 3L, TimeUnit.HOURS);
    }

    /**
     * 从redis中获取token
     */
    @RequestMapping(value = "/getUserToken/{tokenId}", method = RequestMethod.GET)
    public String getUserToken(@PathVariable("tokenId") String tokenId) {
        return (String)redisCacheHandler.getCache(tokenId);
    }

    /**
     * 从redis中删除token
     */
    @RequestMapping(value = "/deleteUserToken/{tokenId}", method = RequestMethod.GET)
    public void deleteUserToken(@PathVariable("tokenId") String tokenId) {
        redisCacheHandler.clearCacheByKey(tokenId);
    }

    /**
     * 将用户的菜单字符串写到redis中
     */
    @RequestMapping(value = "/writeUserMenuInfoToRedis", method = RequestMethod.GET)
    public void writeUserMenuInfoToRedis(@RequestParam Map<String, String> param) {
        String userId = param.get("userId");
        String menuInfo = param.get("userInfo");
        redisCacheHandler.setCacheWithTimeout("menu:" + userId, menuInfo, 3L, TimeUnit.HOURS);
    }

    /**
     * 从redis中获取用户的菜单字符串
     */
    @RequestMapping(value = "/getUserMenuInfo/{userId}", method = RequestMethod.GET)
    public String getUserMenuInfo(@PathVariable("userId") int userId) {
        return (String)redisCacheHandler.getCache("menu:" + userId);
    }

    /**
     * 从redis中删除用户菜单字符串
     */
    @RequestMapping(value = "/deleteUserMenuInfo/{userId}", method = RequestMethod.GET)
    public void deleteUserMenuInfo(@PathVariable("userId") int userId) {
        redisCacheHandler.clearCacheByKey("menu:" + userId);
    }
}