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
     * @param value
     */
    @RequestMapping(value = "/redis/checkCaptchaExists/{key}/{value}", method = RequestMethod.GET)
    boolean checkCaptchaExists(@PathVariable("key") String key, @PathVariable("value") String value);

}
