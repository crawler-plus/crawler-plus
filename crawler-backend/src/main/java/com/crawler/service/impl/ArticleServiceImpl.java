package com.crawler.service.impl;

import com.crawler.dao.ArticleMapper;
import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import com.crawler.domain.TransferEntity;
import com.crawler.service.api.ArticleService;
import com.crawler.util.JsoupUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public void saveTemplateConfig(TemplateConfig tc) {
		articleMapper.saveTemplateConfig(tc);
	}

	@Override
	public void editTemplateConfig(TemplateConfig tc) {
		articleMapper.editTemplateConfig(tc);
	}

	@Override
	public void removeTemplateConfig(String id) {
		articleMapper.removeTemplateConfig(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TemplateConfig> listAllTemplateConfig() {
		return articleMapper.listAllTemplateConfig();
	}

	@Override
	@Transactional(readOnly = true)
	public TemplateConfig getTemplateConfig(String id) {
		return articleMapper.getTemplateConfig(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CrawlerContent> listAllCrawlerContents() {
		return articleMapper.listAllCrawlerContents();
	}

	@Override
	@Transactional(readOnly = true)
	public CrawlerContent getCrawlerContent(String id) {
		return articleMapper.getCrawlerContent(id);
	}

	@Override
	public void batchSaveCrawlerContent(List<CrawlerContent> cList) {
		articleMapper.batchSaveCrawlerContent(cList);
	}

    @Override
    public void saveCrawlerContent(CrawlerContent cc) {
        articleMapper.saveCrawlerContent(cc);
    }

    @Override
	@Transactional(readOnly = true)
	public List<String> fetchAllArticleUrls() {
		return articleMapper.fetchAllArticleUrls();
	}
	
	@Override
	public void cronjob() {
        // 得到当前所有配置列表信息
        List<TemplateConfig> templateConfigs = listAllTemplateConfig();
        // 如果模版信息不为空
        if (!CollectionUtils.isEmpty(templateConfigs)) {
            Map<String, String> reqHeadersMap = new HashMap<>();
            reqHeadersMap.put("Accept", "*/*");
            reqHeadersMap.put("Accept-Encoding", "gzip, deflate");
            reqHeadersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
            reqHeadersMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
            for (TemplateConfig tc : templateConfigs) {
                // 查出的待爬取的url
                List<String> crawlerUrl = new ArrayList<>();
                // 得到一级url
                String url = tc.getUrl();
                // 构造其他参数信息
                TransferEntity te = new TransferEntity();
                te.setUrl(url);
                te.setPattern(tc.getFirstLevelPattern());
                te.setHttpMethod("GET");
                // 先查询是否能找到记录
                Elements elements = null;
                try {
                    // 得到匹配的所有的区域
                    elements = JsoupUtils.getUrlContentsByPattern(te, reqHeadersMap, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 继续下次循环
                    continue;
                }
                Elements aLink = elements.select("a");
                int size = aLink.size();
                if(size == 0) continue;
                for (int j = 0; j < size; j++) {
                    String link = aLink.get(j).attr("href");
                    crawlerUrl.add(link);
                }
                if (!CollectionUtils.isEmpty(crawlerUrl)) {
                    for (String eachUrl : crawlerUrl) {
                    	if(this.articleMapper.isExistUrl(eachUrl) > 0) {
                    		continue;
                    	}
                        // 间隔1秒再爬
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Document d = null;
                        // 构造其他参数信息
                        TransferEntity t = new TransferEntity();
                        t.setUrl(eachUrl);
                        t.setHttpMethod("GET");
                        try {
                            d = JsoupUtils.getUrlDocument(t, reqHeadersMap, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // 继续下次循环
                            continue;
                        }
                        String timeExp = tc.getTimePattern();
                        Integer timeIndex = 0;
                        String[] timeExps = timeExp.split(":");
                        if(timeExps.length > 1) {
                        	timeExp = timeExps[0];
                        	timeIndex = Integer.parseInt(timeExps[1])-1;
                        	if(timeIndex < 0) timeIndex = 0;
                        }
                        String time = "";
                        // 得到标题元素
                        Elements titleE = d.select(tc.getTitlePattern());
                        // 证明是无效链接，继续下次循环
                        if (titleE.size() == 0) continue;
                        // 得到时间元素
                        Elements timeE = d.select(timeExp);
                        // 证明是无效链接，继续下次循环
                        //if (timeE.size() == 0) continue;
                        // 得到内容元素
                        Elements bodyE = d.select(tc.getContentPattern());
                        // 证明是无效链接，继续下次循环
                        if (bodyE.size() == 0) continue;
                        String title = titleE.get(0).html();
                        if(timeIndex >= timeE.size()) {
                        	timeIndex = timeE.size() - 1;
                        }
                        time = timeE.get(timeIndex).html();
                        String body = bodyE.get(0).html();
                        CrawlerContent c = new CrawlerContent();
                        c.setId(UUID.randomUUID().toString().replace("-", ""));
                        c.setUrl(eachUrl);
                        c.setTitle(title);
                        c.setTime(time);
                        c.setContentBody(body);
                        // 改为每次插入单条数据！！！
                        saveCrawlerContent(c);
                    }
                }
            }
        }
	}

    @Override
    @Transactional(readOnly = true)
    public int checkTemplateConfigExists(TemplateConfig templateConfig) {
        return articleMapper.checkTemplateConfigExists(templateConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public int getCrawlerContentSize() {
        return articleMapper.getCrawlerContentSize();
    }
}
