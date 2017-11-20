package com.crawler.controller;

import com.crawler.domain.BaseEntity;
import com.crawler.service.api.CaptchaService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_ERROR;

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
	@HystrixCommand(fallbackMethod = "captchaFallBack")
	public BaseEntity createCaptcha() {
		return captchaService.createCaptcha();
	}

	/**
	 * 调用生成验证码服务失败回调方法
	 * @return
	 */
	public BaseEntity captchaFallBack(){
		BaseEntity baseEntity = new BaseEntity();
		baseEntity.setMsgCode(MESSAGE_CODE_ERROR.getCode());
		return baseEntity;
	}
}
