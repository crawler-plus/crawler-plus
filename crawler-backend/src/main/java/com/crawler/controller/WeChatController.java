package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.TokenEntity;
import com.crawler.domain.TransferEntity;
import com.crawler.domain.WeChatOfficialAccounts;
import com.crawler.exception.CrawlerException;
import com.crawler.util.JsoupUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.crawler.constant.PermissionsConst.WECHAT_PUBLIC_SEARCH;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 微信公众号搜索Controller
 */
@RestController
@RequestMapping("/weChat")
public class WeChatController {

	@ApiOperation(value="根据微信公众号名称搜索微信公众号", notes="根据微信公众号名称搜索微信公众号")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "weChatTitle", value = "微信公众号名称", required = true, dataType = "String"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping(path = "/weChat/{weChatTitle}")
	@RequirePermissions(value = WECHAT_PUBLIC_SEARCH)
	public BaseEntity barCode(@PathVariable("weChatTitle") String weChatTitle, TokenEntity t) throws CrawlerException {
		BaseEntity be = new BaseEntity();
		TransferEntity te = new TransferEntity();
		te.setUrl("http://weixin.sogou.com/weixin?type=1&query=" + weChatTitle + "&ie=utf8&s_from=input&page=1");
		te.setHttpMethod("GET");
		Map<String, String> reqHeadersMap = new HashMap<>();
		reqHeadersMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		reqHeadersMap.put("User-Agent", "Mozilla/5.0 (Windows N" +
				"T 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		reqHeadersMap.put("Accept-Encoding", "gzip, deflate");
		reqHeadersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
		try {
			Thread.sleep(1000);
			// 得到文档得结构
			Document urlDocument = JsoupUtils.getUrlDocument(te, reqHeadersMap, null);
			// 得到分页栏，判断是否有这个微信公众号
			Elements pageEles = urlDocument.select("#pagebar_container");
			int pageSize = pageEles.size();
			// 如果没有公众号数据，向前台返回信息
			if(pageSize == 0) {
				// 业务message
				be.setMsgCode("300");
				be.setContent("对不起，暂时没有您想要的公众号信息，请重试！");
			}else {
				// 承装所有公众号信息
				List<WeChatOfficialAccounts> wcoa = new ArrayList<>();
				// 得到能查询出来得页数（没登录状态下最多能查出10页）
				int totalSearchSize = pageEles.select("a").size();
				// 得到所有待爬取得页面url
				List<String> urlList = new ArrayList<>();
				for(int i = 1; i <= totalSearchSize; i ++) {
					String url = "http://weixin.sogou.com/weixin?type=1&query=" + weChatTitle + "&ie=utf8&s_from=input&page=" + i;
					urlList.add(url);
				}
				// 遍历这些url，进行页面得爬取
				for(String s : urlList) {
					Thread.sleep(1000);
					te.setUrl(s);
					// 得到文档得结构
					Document urlDoc = JsoupUtils.getUrlDocument(te, reqHeadersMap, null);
					Elements targetEles = urlDoc.select("a[uigs^=account_name_]");
					// 得到标题
					for(Element eachE : targetEles) {
						String title = eachE.text();
						String url = eachE.attr("href").replace("amp;", "");
						WeChatOfficialAccounts wc = new WeChatOfficialAccounts();
						wc.setTitle(title);
						wc.setUrl(url);
						wcoa.add(wc);
					}
				}
				// 业务message
				be.setMsgCode(MESSAGE_CODE_OK.getCode());
				be.setContent(wcoa);
			}
		} catch (Exception e) {
			throw new CrawlerException(e);
		}
		return be;
	}
}