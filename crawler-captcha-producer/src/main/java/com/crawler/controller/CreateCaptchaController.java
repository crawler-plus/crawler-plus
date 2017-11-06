package com.crawler.controller;

import com.crawler.components.CrawlerProperties;
import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.util.FtpUtils;
import com.google.code.kaptcha.Producer;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

@RestController
public class CreateCaptchaController {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private CrawlerProperties crawlerProperties;

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        be.setCaptchaCode(capText);
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
            be.setContent("http://" + crawlerProperties.getCaptchaFtpServerHost() + ":" + crawlerProperties.getCaptchaFtpServerPort() + "/" + imgName);
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
