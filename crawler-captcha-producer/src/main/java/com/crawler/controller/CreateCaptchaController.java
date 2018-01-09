package com.crawler.controller;

import com.crawler.components.CrawlerProperties;
import com.crawler.domain.BaseEntity;
import com.crawler.service.RPCApi;
import com.crawler.util.FtpUtils;
import com.crawler.util.LoggerUtils;
import com.xiaoleilu.hutool.captcha.CaptchaUtil;
import com.xiaoleilu.hutool.captcha.LineCaptcha;
import com.xiaoleilu.hutool.io.IoUtil;
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

import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_ERROR;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

@RestController
public class CreateCaptchaController {

    @Autowired
    private CrawlerProperties crawlerProperties;

    @Autowired
    private RPCApi rpcApi;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/create")
    public BaseEntity createCaptcha() {
        BaseEntity be = new BaseEntity();
        // 生成验证码图片的名称
        String imgName = UUID.randomUUID().toString().replace("-", "") + ".png";
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(125, 70);
        String capText = lineCaptcha.getCode();
        BufferedImage bi = lineCaptcha.getImage();
        be.setCaptchaCode(capText);
        ByteArrayOutputStream os = null;
        // 向redis的set中写入验证码
        rpcApi.writeCaptchaCodeToRedis("captchaSet", capText);
        try {
            os = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", os);
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
            FtpUtils.fileUploadByFtp(crawlerProperties.getCaptchaFtpServerHost(),
                    crawlerProperties.getCaptchaFtpServerUserName(),
                    crawlerProperties.getCaptchaFtpServerPassword(),
                    crawlerProperties.getCaptchaFtpServerUrl(),
                    inputStream, imgName);
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
            // 设置图片地址
            be.setContent("http://" + crawlerProperties.getCaptchaFtpServerHost() + ":" + crawlerProperties.getCaptchaFtpServerPort() + "/" + imgName);
        } catch (Exception e) {
            LoggerUtils.printExceptionLogger(logger, e);
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
            // 设置图片地址
            be.setContent("");
        }finally {
            IoUtil.close(os);
        }
        return be;
    }
}
