package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.annotation.RequireToken;
import com.crawler.domain.BarCodeEntity;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.TransferEntity;
import com.crawler.exception.CrawlerException;
import com.crawler.util.JsoupUtils;
import com.google.common.collect.Maps;
import com.xiaoleilu.hutool.json.JSONUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.crawler.constant.PermissionsConst.QRCODE_SEARCH;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 条形码Controller
 */
@RestController
@RequestMapping("/barCode")
public class BarCodeController {

	@ApiOperation(value="根据条形码查商品信息", notes="根据条形码查商品信息")
	@ApiImplicitParam(name = "code", value = "条形码", required = true, dataType = "String")
	@GetMapping(path = "/barCode/{code}")
	@RequirePermissions(value = QRCODE_SEARCH)
	@RequireToken
	public BaseEntity barCode(@PathVariable("code") String code, BaseEntity be) throws CrawlerException {
		TransferEntity te = new TransferEntity();
		te.setUrl("http://www.liantu.com/tiaoma/query.php");
		te.setHttpMethod("POST");
		Map<String, String> reqHeadersMap = Maps.newHashMap();
		reqHeadersMap.put("Host", "www.liantu.com");
		reqHeadersMap.put("Accept", "*/*");
		reqHeadersMap.put("X-Requested-With", "XMLHttpRequest");
		reqHeadersMap.put("User-Agent", "Mozilla/5.0 (Windows N" +
				"T 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		reqHeadersMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		reqHeadersMap.put("Referer", "http://www.liantu.com/tiaoma/");
		reqHeadersMap.put("Accept-Encoding", "gzip, deflate");
		reqHeadersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
		Map<String, String> postParamsMap = Maps.newHashMap();
		postParamsMap.put("ean", code);
		try {
			Document urlDocument = JsoupUtils.getUrlDocument(te, reqHeadersMap, postParamsMap);
			Element bodyEle = urlDocument.body();
			String jsonStr = bodyEle.text();
			BarCodeEntity bce = JSONUtil.toBean(jsonStr, BarCodeEntity.class);
			be.setMsgCode(MESSAGE_CODE_OK.getCode());
			be.setContent(bce);
		} catch (Exception e) {
			throw new CrawlerException(e);
		}
		return be;
	}
}