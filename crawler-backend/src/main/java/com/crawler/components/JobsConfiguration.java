package com.crawler.components;

import com.crawler.util.LoggerUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
    private CrawlerProperties crawlerProperties;

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
            LoggerUtils.printExceptionLogger(logger, e);
        }finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                LoggerUtils.printExceptionLogger(logger, e);
            }
        }
    }
}