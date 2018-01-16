package com.crawler.components;

import com.crawler.service.api.ArticleService;
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
    private ArticleService articleService;

    /**
     * 根据模板爬取文章信息,每天0点，6点，12点，18点自动执行
     */
    @Scheduled(cron = "0 0 0,6,12,18 * * ?")
    public void cronJob() {
        // 执行爬取任务
        this.articleService.cronjob();
    }
}