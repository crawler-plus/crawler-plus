package com.crawler.components;

import com.crawler.domain.SysLock;
import com.crawler.service.api.ArticleService;
import com.crawler.service.api.SysCaptchaService;
import com.crawler.service.api.SysLockService;
import com.crawler.util.LoggerUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * 定时任务
 */
@Configuration
@EnableScheduling
public class JobsConfiguration {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SysLockService sysLockService;

    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private CrawlerProperties crawlerProperties;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 每隔4个小时定时跑一次定时任务，根据网站配置模版抓取网页信息
    @Scheduled(cron = "0 0 10,14,18 * * ?")
    public void cronJob() {
        // 当网站正在运行模版定时任务的时候，向数据库中写入值
        SysLock sysLock = new SysLock();
        sysLock.setSystemCron("1");
        sysLockService.updateSysLock(sysLock);
        List<Integer> allUserId = articleService.fetchAllUserIdFromTemplateConfig();
        if(!CollectionUtils.isEmpty(allUserId)) {
            for (Integer id : allUserId) {
                this.articleService.cronjob(id);
            }
        }
    	// 当网站运行模版定时任务结束后，将值从数据库中移除
        sysLock.setSystemCron("0");
        sysLockService.updateSysLock(sysLock);
    }

    /**
     * 清除数据库中的验证码,每天2点和14点定时清理
     */
    @Scheduled(cron = "0 0 2,14 * * ?")
    public void clearRedisCaptcha() {
        sysCaptchaService.clearAllSysCaptchas();
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