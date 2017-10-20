package com.crawler.components;

import com.crawler.service.api.ArticleService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 定时任务
 */
@Configuration
@EnableScheduling
public class JobsConfiguration {

    @Resource
    private ArticleService articleService;

    /**
     * 定时根据网站配置模版去相应的网站爬取数据，插入数据库
     */
    // 每个60秒执行一次@Scheduled(cron = "*/60 * * * * ?")
    // 每隔10分钟执行一次@Scheduled(cron = "0 0/10 * * * ?")
    // 每隔1小时执行一次@Scheduled(cron = "0 0 0/1 * * ?")
    // 每天中午12点触发@Scheduled(cron = "0 0 12 * * ?")
    /**
     * 0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
     0 0/30 9-17 * * ? 朝九晚五工作时间内每半小时
     0 0 12 ? * WED 表示每个星期三中午12点
     "0 0 12 * * ?" 每天中午12点触发
     "0 15 10 ? * *" 每天上午10:15触发
     "0 15 10 * * ?" 每天上午10:15触发
     "0 15 10 * * ? *" 每天上午10:15触发
     "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
     "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
     "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
     "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
     "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
     "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
     "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
     "0 15 10 15 * ?" 每月15日上午10:15触发
     "0 15 10 L * ?" 每月最后一日的上午10:15触发
     "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
     "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
     "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
     */

//    @Scheduled(cron = "*/60 * * * * ?")
    // 每隔3个小时定时跑一次定时任务，根据网站配置模版抓取网页信息
    @Scheduled(cron = "0 0 10,14,18 * * ?")
    public void cronJob() {
    	this.articleService.cronjob();
    }
}