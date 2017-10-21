package com.crawler.controller;

import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import com.crawler.service.api.ArticleService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * 验证码Controller
 */

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

	@Autowired
	private Producer captchaProducer;

	@GetMapping("/create")
	public BaseEntity createCaptcha() {
		// create the text for the image
		String capText = captchaProducer.createText();
		System.out.println(capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		OutputStream os = null;
		try {
			os = new FileOutputStream("E:/captcha.png");
			ImageIO.write(bi, "jpg", os);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(os);
		}
		return null;
	}


}
