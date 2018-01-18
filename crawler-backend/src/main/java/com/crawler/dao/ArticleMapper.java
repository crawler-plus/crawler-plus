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
    List<TemplateConfig> listAllTemplateConfig();

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
    List<CrawlerContent> listAllCrawlerContents();

    /**
     * 列出所有查询出的文章（只包含标题和id）
     * @return
     */
    List<CrawlerContent> listAllSimpleCrawlerContents();

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
}