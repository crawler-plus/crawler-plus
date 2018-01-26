package com.crawler.service.api;

import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;

import java.util.List;

/**
 * 文章Service
 */
public interface ArticleService {

    /**
     * 列出所有文章配置
     * @return
     */
    List<TemplateConfig> listAllTemplateConfig();

    /**
     * 添加文章
     * @param cc
     */
    void saveCrawlerContent(CrawlerContent cc);

    /**
     * 执行爬取
     */
    void cronjob();
}
