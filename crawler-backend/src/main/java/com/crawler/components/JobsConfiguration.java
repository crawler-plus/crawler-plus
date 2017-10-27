package com.crawler.components;

import com.crawler.service.api.ArticleService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * 定时任务
 */
@Configuration
@EnableScheduling
public class JobsConfiguration {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CrawlerProperties crawlerProperties;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 每隔4个小时定时跑一次定时任务，根据网站配置模版抓取网页信息
    @Scheduled(cron = "0 0 10,14,18 * * ?")
    public void cronJob() {
    	this.articleService.cronjob();
    }

    /**
     * 清除redis中的验证码,每天2点和14点定时清理
     */
    @Scheduled(cron = "0 0 2,14 * * ?")
    public void clearRedisCaptcha() {
        redisTemplate.delete("captchaSet");
    }

    /**
     * 清除ftp服务器上的验证码图片,每天2点和14点定时清理
     */
    @Scheduled(cron = "0 0 2,14 * * ?")
    public void clearFtpCaptchaImg() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(crawlerProperties.getCaptchaFtpServerHost());
            ftpClient.login(crawlerProperties.getCaptchaFtpServerUserName(), crawlerProperties.getCaptchaFtpServerPassword());
            ftpClient.changeWorkingDirectory(crawlerProperties.getCaptchaFtpServerUrl());
            FTPFile[] ftpFileArr = ftpClient.listFiles(crawlerProperties.getCaptchaFtpServerUrl());
            for (FTPFile ftpFile : ftpFileArr) {
                ftpClient.deleteFile(ftpFile.getName());
            }
            ftpClient.logout();
        }catch (Exception e) {
            if(logger.isWarnEnabled()) {
                logger.warn(e.getMessage());
            }
        }finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                if(logger.isWarnEnabled()) {
                    logger.warn(e.getMessage());
                }
            }
        }
    }
}