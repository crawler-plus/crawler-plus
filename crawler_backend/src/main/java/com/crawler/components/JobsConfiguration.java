package com.crawler.components;

import com.crawler.service.api.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
}