package com.crawler.service.impl;

import com.crawler.dao.SysCaptchaMapper;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysCaptcha;
import com.crawler.service.api.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SysCaptchaMapper sysCaptchaMapper;

    public BaseEntity createCaptcha() {
        BaseEntity baseEntity = restTemplate.getForObject("http://captcha-service/create", BaseEntity.class);
        // 将验证码写入数据库
        String captchaCode = baseEntity.getCaptchaCode();
        SysCaptcha sysCaptcha = new SysCaptcha();
        sysCaptcha.setCaptcha(captchaCode);
        sysCaptchaMapper.addSysCaptcha(sysCaptcha);
        return baseEntity;
    }
}
