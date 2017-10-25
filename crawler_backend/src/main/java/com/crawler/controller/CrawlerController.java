package com.crawler.controller;

import com.crawler.components.CheckToken;
import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.TokenEntity;
import com.crawler.domain.TransferEntity;
import com.crawler.exception.CrawlerException;
import com.crawler.util.JsoupUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单爬取网站Controller
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

	@Autowired
	private CheckToken checkToken;

	@ApiOperation(value="根据参数爬取网站内容", notes="根据参数爬取网站内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "te", value = "传递的参数Entity", required = true, dataType = "TransferEntity"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@PostMapping("/crawler")
	public BaseEntity crawler(@RequestBody TransferEntity te, TokenEntity t) throws CrawlerException {
		// 验证token
		checkToken.checkToken(t);
		Map<String, String> reqHeadersMap = new HashMap<>();
		reqHeadersMap.put("Accept", "*/*");
		reqHeadersMap.put("Accept-Encoding", "gzip, deflate");
		reqHeadersMap.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		reqHeadersMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0");
		BaseEntity be = new BaseEntity();
		Elements contents = null;
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
			List<String> contentList = new ArrayList<>();
			if(null != contents) {
				for(Element e : contents) {
					contentList.add(e.toString());
				}
				be.setContent(contentList.toArray());
			}
		}
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}

}
