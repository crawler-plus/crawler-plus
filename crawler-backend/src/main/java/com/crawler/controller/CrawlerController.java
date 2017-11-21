package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.annotation.RequireToken;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.TransferEntity;
import com.crawler.exception.CrawlerException;
import com.crawler.util.JsoupUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.crawler.constant.PermissionsConst.NET_CRAWLER_SEARCH;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 简单爬取网站Controller
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

	@ApiOperation(value="根据参数爬取网站内容", notes="根据参数爬取网站内容")
	@ApiImplicitParam(name = "te", value = "传递的参数Entity", dataType = "TransferEntity")
	@PostMapping("/crawler")
	@RequirePermissions(value = NET_CRAWLER_SEARCH)
	@RequireToken()
	public BaseEntity crawler(@RequestBody TransferEntity te) throws CrawlerException {
		Map<String, String> reqHeadersMap = Maps.newHashMap();
		reqHeadersMap.put("Accept", "*/*");
		reqHeadersMap.put("Accept-Encoding", "gzip, deflate");
		reqHeadersMap.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		reqHeadersMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0");
		BaseEntity be = new BaseEntity();
		Elements contents;
		try {
			 contents = JsoupUtils.getUrlContentsByPattern(te, reqHeadersMap, null);
		}catch (Exception e) {
			throw new CrawlerException(e);
		}
		// 如果只查文本
		if(te.isOnlyText()) {
			String text = contents.text();
			be.setContent(text);
		}else {
			List<String> contentList = Lists.newArrayList();
			if(null != contents) {
				for(Element e : contents) {
					contentList.add(e.toString());
				}
				be.setContent(contentList.toArray());
			}
		}
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

}
