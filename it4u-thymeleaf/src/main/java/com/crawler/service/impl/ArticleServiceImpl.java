package com.crawler.service.impl;

import com.crawler.dao.ArticleMapper;
import com.crawler.domain.ArticleTransferEntity;
import com.crawler.domain.CrawlerContent;
import com.crawler.service.api.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

    @Override
    public List<CrawlerContent> listAllSimpleCrawlerContents(ArticleTransferEntity at) {
        return articleMapper.listAllSimpleCrawlerContents(at);
    }

    @Override
    public CrawlerContent getCrawlerContent(String id) {
        return articleMapper.getCrawlerContent(id);
    }
}
