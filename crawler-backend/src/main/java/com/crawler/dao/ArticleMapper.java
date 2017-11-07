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
     * 添加文章
     * @param cc
     */
    void saveCrawlerContent(CrawlerContent cc);

    /**
     * 批量添加文章
     * @param cList
     */
    void batchSaveCrawlerContent(List<CrawlerContent> cList);

    /**
     * 获取所有数据库文章urls
     * @return
     */
    List<String> fetchAllArticleUrls();
    
    /**
     * 判断是否存在url
     * @return
     */
    Integer isExistUrl(CrawlerContent content);

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
     * 根据用户id删除文章配置
     * @param id
     */
    void removeTemplateConfigByUserId(int id);

    /**
     * 根据用户id删除文章
     * @param id
     */
    void removeCrawlerContentByUserId(int id);

    /**
     * 得到文章配置表里所有不重复的userId
     * @return
     */
    List<Integer> fetchAllUserIdFromTemplateConfig();
}