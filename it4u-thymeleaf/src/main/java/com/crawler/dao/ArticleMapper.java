package com.crawler.dao;

import com.crawler.domain.ArticleTransferEntity;
import com.crawler.domain.CrawlerContent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章mapper
 */
@Repository
public interface ArticleMapper {

    /**
     * 列出所有查询出的文章（只包含标题和id）
     * @return
     */
    List<CrawlerContent> listAllSimpleCrawlerContents(ArticleTransferEntity at);

    /**
     * 根据id获得指定文章
     * @param id
     * @return
     */
    CrawlerContent getCrawlerContent(String id);

}