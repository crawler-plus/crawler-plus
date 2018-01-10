package com.crawler.controller;

import com.crawler.cache.RedisCacheHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        redisCacheHandler.setValueToSet(key, value);
    }

    /**
     * 判断验证码是否正确
     */
    @RequestMapping(value = "/checkCaptchaExists/{key}/{value}", method = RequestMethod.GET)
    public boolean checkCaptchaExists(@PathVariable("key") String key, @PathVariable("value") String value) {
        return redisCacheHandler.valueIsMemberInSet(key, value);
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

}
