package com.crawler.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "crawler-redis-service-provider", fallback = HystrixClientFallback.class)
public interface RPCApi {

    /**
     * 向redis的set中写入验证码
     * @param key
     * @param value
     */
    @RequestMapping(value = "/redis/writeCaptchaCodeToRedis/{key}/{value}", method = RequestMethod.GET)
    void writeCaptchaCodeToRedis(@PathVariable("key") String key, @PathVariable("value") String value);

}
