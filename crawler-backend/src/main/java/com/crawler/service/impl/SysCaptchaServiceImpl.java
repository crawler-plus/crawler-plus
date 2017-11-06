package com.crawler.service.impl;

import com.crawler.dao.SysCaptchaMapper;
import com.crawler.service.api.SysCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysCaptchaServiceImpl implements SysCaptchaService {

    @Autowired
    private SysCaptchaMapper sysCaptchaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<String> listAllSysCaptcha() {
        return sysCaptchaMapper.listAllSysCaptcha();
    }

    @Override
    public void clearAllSysCaptchas() {
        sysCaptchaMapper.clearAllSysCaptchas();
    }
}
