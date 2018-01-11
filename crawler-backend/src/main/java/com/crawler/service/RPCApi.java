package com.crawler.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
