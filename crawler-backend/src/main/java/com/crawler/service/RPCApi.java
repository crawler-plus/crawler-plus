package com.crawler.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("crawler-redis-service-provider")
public interface RPCApi {

    /**
     * 判断验证码是否存在
     * @param key
     */
    @RequestMapping(value = "/redis/checkCaptchaExists/{key}", method = RequestMethod.GET)
    String checkCaptchaExists(@PathVariable("key") String key);

    /**
     * 向redis中插入token数据
     * @param tokenId
     * @param token
     */
    @RequestMapping(value = "/redis/writeUserToken/{tokenId}/{token}", method = RequestMethod.GET)
    void writeUserToken(@PathVariable("tokenId") String tokenId, @PathVariable("token") String token);

    /**
     * 从redis中获取token
     * @param tokenId
     */
    @RequestMapping(value = "/redis/getUserToken/{tokenId}", method = RequestMethod.GET)
    String getUserToken(@PathVariable("tokenId") String tokenId);

    /**
     * 从redis中获取token
     * @param tokenId
     */
    @RequestMapping(value = "/redis/deleteUserToken/{tokenId}", method = RequestMethod.GET)
    void deleteUserToken(@PathVariable("tokenId") String tokenId);

    /**
     * 将用户的菜单字符串写到redis中
     */
    @RequestMapping(value = "/redis/writeUserMenuInfoToRedis", method = RequestMethod.GET)
    void writeUserMenuInfoToRedis(@RequestParam Map<String, String> param);

    /**
     * 从redis中获取用户的菜单字符串
     * @param userId
     */
    @RequestMapping(value = "/redis/getUserMenuInfo/{userId}", method = RequestMethod.GET)
    String getUserMenuInfo(@PathVariable("userId") int userId);


    /**
     * 从redis中删除用户菜单字符串
     * @param userId
     */
    @RequestMapping(value = "/redis/deleteUserMenuInfo/{userId}", method = RequestMethod.GET)
    void deleteUserMenuInfo(@PathVariable("userId") int userId);
}
