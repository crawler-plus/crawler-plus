package com.crawler.service.api;

import com.crawler.domain.ArticleTransferEntity;
import com.crawler.domain.CrawlerContent;

import java.util.List;

/**
 * 文章Service
 */
public interface ArticleService {

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
