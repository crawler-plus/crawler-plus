package com.crawler.controller;

import com.crawler.domain.BaseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisServiceProvider {

    /**
     * 测试
     */
    @GetMapping("/test")
    public BaseEntity test() {
        return null;
    }
}
