package com.crawler.service.api;

import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 文章Service
 */
@CacheConfig(cacheNames = "templateConfig")
public interface ArticleService {

    /**
     * 保存文章配置
     * @param tc
     */
    void saveTemplateConfig(TemplateConfig tc);

    /**
     * 修改文章配置
     * @param tc
     */
    @CachePut(value = "templateConfig", key = "#tc.getId()")
    void editTemplateConfig(TemplateConfig tc);

    /**
     * 根据id删除文章配置
     * @param id
     */
    @CacheEvict(value = "templateConfig")
    void removeTemplateConfig(String id);

    /**
     * 列出所有文章配置
     * @return
     */
    List<TemplateConfig> listAllTemplateConfig();

    /**
     * 根据id获得文章配置
     * @param id
     * @return
     */
    @Cacheable
    TemplateConfig getTemplateConfig(String id);

    /**
     * 列出所有查询出的文章
     * @return
     */
    List<CrawlerContent> listAllCrawlerContents();

    /**
     * 根据id获得指定文章
     * @param id
     * @return
     */

    CrawlerContent getCrawlerContent(String id);

    /**
     * 批量添加文章
     * @param cList
     */
    void batchSaveCrawlerContent(List<CrawlerContent> cList);

    /**
     * 添加文章
     * @param cc
     */
    void saveCrawlerContent(CrawlerContent cc);

    /**
     * 获取所有数据库文章urls
     * @return
     */
    List<String> fetchAllArticleUrls();

    /**
     * 执行爬取
     */
    void cronjob();
}
