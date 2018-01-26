package com.crawler.dao;

import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章mapper
 */
@Repository
public interface ArticleMapper {

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
     * 判断是否存在url
     * @return
     */
    Integer isExistUrl(CrawlerContent content);
}