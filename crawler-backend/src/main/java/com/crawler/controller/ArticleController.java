package com.crawler.controller;

import com.crawler.components.CheckToken;
import com.crawler.components.RedisConfiguration;
import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import com.crawler.domain.TokenEntity;
import com.crawler.service.api.ArticleService;
import com.crawler.util.JsonUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 文章Controller
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CheckToken checkToken;

	@Autowired
	private RedisConfiguration redisConfiguration;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 执行cron
	 */
	@ApiOperation(value="执行cron", notes="执行cron")
	@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	@GetMapping("/cron")
	public void cron(TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		this.articleService.cronjob();
	}
	
	/**
	 * 保存文章配置
	 */
	@ApiOperation(value="保存文章配置", notes="保存文章配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "te", value = "文章配置entity", required = true, dataType = "TemplateConfig"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@PostMapping("/saveTemplateConfig")
	public BaseEntity saveTemplateConfig(@Valid TemplateConfig te, TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		te.setId(UUID.randomUUID().toString().replace("-", ""));
		articleService.saveTemplateConfig(te);
		BaseEntity be = new BaseEntity();
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		be.setContent("保存文章配置成功");
		return be;
	}

	/**
	 * 修改文章配置
	 */
	@ApiOperation(value="修改文章配置", notes="修改文章配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "te", value = "文章配置entity", required = true, dataType = "TemplateConfig"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@PutMapping("/editTemplateConfig")
	public BaseEntity editTemplateConfig(@Valid TemplateConfig te, TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		articleService.editTemplateConfig(te);
		BaseEntity be = new BaseEntity();
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		be.setContent("修改文章配置成功");
		return be;
	}

	/**
	 * 根据id删除文章配置
	 */
	@ApiOperation(value="根据id删除文章配置", notes="根据id删除文章配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "文章配置id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@DeleteMapping(path = "/removeTemplateConfig/{id}")
	public BaseEntity removeTemplateConfig(@PathVariable("id") String id, TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		articleService.removeTemplateConfig(id);
		BaseEntity be = new BaseEntity();
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		be.setContent("删除文章配置成功");
		return be;
	}

	/**
	 * 列出所有文章配置
	 */
	@ApiOperation(value="列出所有文章配置", notes="列出所有文章配置")
	@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	@GetMapping("/listAllTemplateConfig")
	public BaseEntity listAllTemplateConfig(TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		BaseEntity be = new BaseEntity();
		List<TemplateConfig> templateConfigs = articleService.listAllTemplateConfig();
		be.setContent(templateConfigs);
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}

	/**
	 * 根据id获得文章配置
	 */
	@ApiOperation(value="根据id获得文章配置", notes="根据id获得文章配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "文章配置id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping(path = "/getTemplateConfig/{id}")
	public BaseEntity getTemplateConfig(@PathVariable("id") String id, TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		BaseEntity be = new BaseEntity();
		TemplateConfig templateConfig = articleService.getTemplateConfig(id);
		be.setContent(templateConfig);
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}

	/**
	 * 列出所有查询出的文章
	 */
	@ApiOperation(value="列出所有查询出的文章", notes="列出所有查询出的文章")
	@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	@GetMapping("/listAllCrawlerContents")
	public BaseEntity listAllCrawlerContents(TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		List<CrawlerContent> contents = null;
		// 从redis中获取文章
		String crawlerContent = (String)redisConfiguration.valueOperations(redisTemplate).get("crawlerContent");
		// 如果redis中的文章列表为空
		if(StringUtils.isEmpty(crawlerContent)) {
			contents = articleService.listAllCrawlerContents();
			// 把文章列表序列化到redis
			String crawlerJson = JsonUtils.objectToJson(contents);
			redisConfiguration.valueOperations(redisTemplate).set("crawlerContent", crawlerJson);
			// 设置过期时间为4h
			redisTemplate.expire("crawlerContent", 4, TimeUnit.HOURS);
		}else { // 从redis中取出文章列表
			contents = JsonUtils.jsonToList(crawlerContent, CrawlerContent.class);
		}
		BaseEntity be = new BaseEntity();
		be.setContent(contents);
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}

	/**
	 * 根据id获得指定文章
	 */
	@ApiOperation(value="根据id获得指定文章", notes="根据id获得指定文章")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping(path = "/getCrawlerContent/{id}")
	public BaseEntity getCrawlerContent(@PathVariable("id") String id, TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		BaseEntity be = new BaseEntity();
		CrawlerContent crawlerContent = articleService.getCrawlerContent(id);
		be.setContent(crawlerContent);
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}

	/**
	 * 执行cron
	 */
	@ApiOperation(value="执行cron", notes="执行cron")
	@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	@GetMapping("/executeCron")
	public BaseEntity executeCron(TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		this.articleService.cronjob();
		// 将redis中的crawlerContent删除
		redisTemplate.delete("crawlerContent");
		BaseEntity be = new BaseEntity();
		be.setContent("执行爬取...");
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}
}