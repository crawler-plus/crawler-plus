package com.crawler.service.impl;

import com.crawler.dao.ArticleMapper;
import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import com.crawler.domain.TransferEntity;
import com.crawler.service.api.ArticleService;
import com.crawler.util.JsoupUtils;
import com.crawler.util.LoggerUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
	public List<TemplateConfig> listAllTemplateConfig() {
		return articleMapper.listAllTemplateConfig();
	}

	@Override
	public TemplateConfig getTemplateConfig(String id) {
		return articleMapper.getTemplateConfig(id);
	}

	@Override
	public List<CrawlerContent> listAllCrawlerContents() {
		return articleMapper.listAllCrawlerContents();
	}

    @Override
	public CrawlerContent getCrawlerContent(String id) {
		return articleMapper.getCrawlerContent(id);
	}

    @Override
    public void saveCrawlerContent(CrawlerContent cc) {
        articleMapper.saveCrawlerContent(cc);
    }
	
	@Override
	public void cronjob() {
        // 得到当前所有配置列表信息
        List<TemplateConfig> templateConfigs = listAllTemplateConfig();
        // 如果模版信息不为空
        if (!CollectionUtils.isEmpty(templateConfigs)) {
            Map<String, String> reqHeadersMap = Maps.newHashMap();
            reqHeadersMap.put("Accept", "*/*");
            reqHeadersMap.put("Accept-Encoding", "gzip, deflate");
            reqHeadersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
            reqHeadersMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
            for (TemplateConfig tc : templateConfigs) {
                // 查出的待爬取的url
                List<String> crawlerUrl = Lists.newArrayList();
                // 得到一级url
                String url = tc.getUrl();
                String crawlerPrefix = tc.getCrawlerPrefix();
                String mainId = tc.getId();
                // 构造其他参数信息
                TransferEntity te = new TransferEntity();
                te.setUrl(url);
                te.setPattern(tc.getFirstLevelPattern());
                te.setHttpMethod("GET");
                // 先查询是否能找到记录
                Elements elements;
                try {
                    // 得到匹配的所有的区域
                    elements = JsoupUtils.getUrlContentsByPattern(te, reqHeadersMap, null);
                } catch (Exception e) {
                    LoggerUtils.printExceptionLogger(logger, e);
                    // 继续下次循环
                    continue;
                }
                Elements aLink = elements.select("a");
                int size = aLink.size();
                if(size == 0) continue;
                for (Element anALink : aLink) {
                    String link = anALink.attr("href");
                    if(link.startsWith("http")) continue;
                    link = crawlerPrefix + link;
                    crawlerUrl.add(link);
                }
                if (!CollectionUtils.isEmpty(crawlerUrl)) {
                    for (String eachUrl : crawlerUrl) {
                        CrawlerContent contentParam = new CrawlerContent();
                        contentParam.setUrl(eachUrl);
                    	if(this.articleMapper.isExistUrl(contentParam) > 0) {
                    		continue;
                    	}
                        // 间隔1秒再爬
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            LoggerUtils.printExceptionLogger(logger, e);
                        }
                        Document d;
                        // 构造其他参数信息
                        TransferEntity t = new TransferEntity();
                        t.setUrl(eachUrl);
                        t.setHttpMethod("GET");
                        try {
                            d = JsoupUtils.getUrlDocument(t, reqHeadersMap, null);
                        } catch (Exception e) {
                            LoggerUtils.printExceptionLogger(logger, e);
                            // 继续下次循环
                            continue;
                        }
                        // 得到标题元素
                        Elements titleE = d.select(tc.getTitlePattern());
                        // 得到时间元素
                        Elements timeE = d.select(tc.getTimePattern());
                        // 得到内容元素
                        Elements bodyE = d.select(tc.getContentPattern());
                        String title = titleE.size() == 0 ? "" : titleE.get(0).text();
                        String time = timeE.size() == 0 ? "" : timeE.get(0).text();
                        String body = bodyE.size() == 0 ? "" : bodyE.get(0).html();
                        CrawlerContent c = new CrawlerContent();
                        c.setId(UUID.randomUUID().toString().replace("-", ""));
                        c.setUrl(eachUrl);
                        c.setTitle(title);
                        c.setTime(time);
                        c.setContentBody(body);
                        c.setRefId(mainId);
                        // 改为每次插入单条数据！！！
                        saveCrawlerContent(c);
                    }
                }
            }
        }
	}

    @Override
    public int checkTemplateConfigExists(TemplateConfig templateConfig) {
        return articleMapper.checkTemplateConfigExists(templateConfig);
    }
}
