package com.crawler.controller;

import com.crawler.components.CrawlerProperties;
import com.crawler.domain.BaseEntity;
import com.crawler.util.FtpUtils;
import com.google.code.kaptcha.Producer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 验证码Controller
 */

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

	@Autowired
	private Producer captchaProducer;

	@Autowired
	private CrawlerProperties crawlerProperties;

	@GetMapping("/create")
	public BaseEntity createCaptcha() {
		// create the text for the image
		String capText = captchaProducer.createText();
		System.out.println(capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", os);
			InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
			FtpUtils.fileUploadByFtp(crawlerProperties.getCaptchaFtpServerHost(),
					crawlerProperties.getCaptchaFtpServerUserName(),
					crawlerProperties.getCaptchaFtpServerPassword(),
					crawlerProperties.getCaptchaFtpServerUrl(),
					inputStream, "testCaptCha.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(os);
		}
		return null;
	}


}
