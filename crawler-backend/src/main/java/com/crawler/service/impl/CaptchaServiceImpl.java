package com.crawler.service.impl;

import com.crawler.domain.BaseEntity;
import com.crawler.service.api.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RestTemplate restTemplate;

    public BaseEntity createCaptcha() {
        BaseEntity baseEntity = restTemplate.getForObject("http://captcha-service/create", BaseEntity.class);
        return baseEntity;
    }
}
