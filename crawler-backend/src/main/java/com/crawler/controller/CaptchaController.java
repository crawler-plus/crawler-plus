package com.crawler.controller;

import com.crawler.domain.BaseEntity;
import com.crawler.service.api.CaptchaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码Controller
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

	@Autowired
	private CaptchaService captchaService;

	@ApiOperation(value="生成验证码", notes="生成验证码")
	@GetMapping("/create")
	public BaseEntity createCaptcha() {
		return captchaService.createCaptcha();
	}
}
