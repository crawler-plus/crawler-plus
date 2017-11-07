package com.crawler.service.api;

import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;

import java.util.List;

/**
 * 文章Service
 */
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
    void editTemplateConfig(TemplateConfig tc);

    /**
     * 根据id删除文章配置
     * @param id
     */
    void removeTemplateConfig(String id);

    /**
     * 列出所有文章配置
     * @return
     */
    List<TemplateConfig> listAllTemplateConfig(int userId);

    /**
     * 根据id获得文章配置
     * @param id
     * @return
     */
    TemplateConfig getTemplateConfig(String id);

    /**
     * 列出所有查询出的文章
     * @return
     */
    List<CrawlerContent> listAllCrawlerContents(int userId);

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
    void cronjob(int userId);

    /**
     * 判断文章配置否存在
     * @param templateConfig
     * @return
     */
    int checkTemplateConfigExists(TemplateConfig templateConfig);

    /**
     * 得到文章数量
     * @return
     */
    int getCrawlerContentSize(int userId);

    /**
     * 得到文章配置表里所有不重复的userId
     * @return
     */
    List<Integer> fetchAllUserIdFromTemplateConfig();
}
