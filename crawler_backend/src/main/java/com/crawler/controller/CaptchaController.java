package com.crawler.controller;

import com.crawler.components.CrawlerProperties;
import com.crawler.components.RedisConfiguration;
import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.util.FtpUtils;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

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

	@Autowired
	private RedisConfiguration redisConfiguration;

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ApiOperation(value="生成验证码", notes="生成验证码")
	@GetMapping("/create")
	public BaseEntity createCaptcha() {
		BaseEntity be = new BaseEntity();
		// 生成验证码图片的名称
		String imgName = UUID.randomUUID().toString().replace("-", "") + ".png";
		// create the text for the image
		String capText = captchaProducer.createText();
		BufferedImage bi = captchaProducer.createImage(capText);
		// 转化为小写字母
		capText = capText.toLowerCase();
		// 将验证码字符串写入redis
		redisConfiguration.setOperations(redisTemplate).add("captchaSet", capText);
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", os);
			InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
			FtpUtils.fileUploadByFtp(crawlerProperties.getCaptchaFtpServerHost(),
					crawlerProperties.getCaptchaFtpServerUserName(),
					crawlerProperties.getCaptchaFtpServerPassword(),
					crawlerProperties.getCaptchaFtpServerUrl(),
					inputStream, imgName);
			be.setMsgCode(Const.MESSAGE_CODE_OK);
			// 设置图片地址
			be.setContent("http://" + crawlerProperties.getCaptchaFtpServerHost() + ":80/" + imgName);
		} catch (Exception e) {
			if(logger.isWarnEnabled()) {
				logger.warn("验证码生成失败，原因：" + e.getMessage());
			}
			be.setMsgCode(Const.MESSAGE_CODE_ERROR);
			// 设置图片地址
			be.setContent("");
		}finally {
			IOUtils.closeQuietly(os);
		}
		return be;
	}
}
