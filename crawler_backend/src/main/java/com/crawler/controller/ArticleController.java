package com.crawler.controller;

import com.crawler.components.CheckToken;
import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import com.crawler.domain.TokenEntity;
import com.crawler.service.api.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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

	/**
	 * 执行cron
	 */
	@ApiOperation(value="执行cron", notes="执行cron")
	@GetMapping("/cron")
	public void cron(TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		this.articleService.cronjob();
	}
	
	/**
	 * 保存文章配置
	 */
	@PostMapping("/saveTemplateConfig")
	@ApiOperation(value="保存文章配置", notes="保存文章配置")
	@ApiImplicitParam(name = "te", value = "文章配置entity", required = true, dataType = "TemplateConfig")
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
	@ApiImplicitParam(name = "te", value = "文章配置entity", required = true, dataType = "TemplateConfig")
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
	@ApiImplicitParam(name = "id", value = "文章配置id", required = true, dataType = "String")
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
	@ApiImplicitParam(name = "id", value = "文章配置id", required = true, dataType = "String")
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
	@GetMapping("/listAllCrawlerContents")
	public BaseEntity listAllCrawlerContents(TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		BaseEntity be = new BaseEntity();
		List<CrawlerContent> crawlerContents = articleService.listAllCrawlerContents();
		be.setContent(crawlerContents);
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}

	/**
	 * 根据id获得指定文章
	 */
	@ApiOperation(value="根据id获得指定文章", notes="根据id获得指定文章")
	@ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String")
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
	@GetMapping("/executeCron")
	public BaseEntity executeCron(TokenEntity t) {
		// 验证token
		checkToken.checkToken(t);
		this.articleService.cronjob();
		BaseEntity be = new BaseEntity();
		be.setContent("执行爬取...");
		be.setMsgCode(Const.MESSAGE_CODE_OK);
		return be;
	}
}
