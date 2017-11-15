package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.domain.*;
import com.crawler.service.api.ArticleService;
import com.crawler.service.api.SysLockService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.crawler.constant.PermissionsConst.CRAWLER_CONTENT_SEARCH;
import static com.crawler.constant.PermissionsConst.TEMPLATE_MGMT;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_ERROR;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 文章Controller
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private SysLockService sysLockService;

	/**
	 * 保存文章配置
	 */
	@ApiOperation(value="保存文章配置", notes="保存文章配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "te", value = "文章配置entity", required = true, dataType = "TemplateConfig"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@PostMapping("/saveTemplateConfig")
	@RequirePermissions(value = TEMPLATE_MGMT)
	public BaseEntity saveTemplateConfig(@Valid TemplateConfig te, TokenEntity t) {
		te.setId(UUID.randomUUID().toString().replace("-", ""));
		articleService.saveTemplateConfig(te);
		BaseEntity be = new BaseEntity();
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
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
	@RequirePermissions(value = TEMPLATE_MGMT)
	public BaseEntity editTemplateConfig(@Valid TemplateConfig te, TokenEntity t) {
		BaseEntity be = new BaseEntity();
		// 得到文章配置信息
		TemplateConfig templateConfig = articleService.getTemplateConfig(te.getId());
		// 得到最新版本信息
		int versionId = templateConfig.getVersion();
		// 如果两次的version不相等
		if(versionId != te.getVersion()) {
			be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
			be.setContent("该文章配置信息已被其他人修改，请返回重新修改！");
		}else {
			articleService.editTemplateConfig(te);
			be.setMsgCode(MESSAGE_CODE_OK.getCode());
			be.setContent("修改文章配置成功");
		}
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
	@RequirePermissions(value = TEMPLATE_MGMT)
	public BaseEntity removeTemplateConfig(@PathVariable("id") String id, TokenEntity t) {
		articleService.removeTemplateConfig(id);
		BaseEntity be = new BaseEntity();
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		be.setContent("删除文章配置成功");
		return be;
	}

	/**
	 * 列出所有文章配置
	 */
	@ApiOperation(value="列出所有文章配置", notes="列出所有文章配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping(path = "/listAllTemplateConfig/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	public BaseEntity listAllTemplateConfig(@PathVariable("id") int userId, TokenEntity t) {
		BaseEntity be = new BaseEntity();
		List<TemplateConfig> templateConfigs = articleService.listAllTemplateConfig(userId);
		be.setContent(templateConfigs);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
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
	@RequirePermissions(value = TEMPLATE_MGMT)
	public BaseEntity getTemplateConfig(@PathVariable("id") String id, TokenEntity t) {
		BaseEntity be = new BaseEntity();
		TemplateConfig templateConfig = articleService.getTemplateConfig(id);
		be.setContent(templateConfig);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 列出所有查询出的文章
	 */
	@ApiOperation(value="列出所有查询出的文章", notes="列出所有查询出的文章")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "crawlerContent", value = "文章 Entity", required = true, dataType = "CrawlerContent"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping("/listAllCrawlerContents")
	@RequirePermissions(value = CRAWLER_CONTENT_SEARCH)
	public BaseEntity listAllCrawlerContents(CrawlerContent crawlerContent, TokenEntity t) {
		int crawlerContentSize = articleService.getCrawlerContentSize(crawlerContent.getUserId());
		// 分页查询
		PageHelper.startPage(crawlerContent.getPage(), crawlerContent.getLimit());
		List<CrawlerContent> contents = articleService.listAllCrawlerContents(crawlerContent.getUserId());
		BaseEntity be = new BaseEntity();
		be.setTotal(crawlerContentSize);
		be.setRows(contents);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
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
	@RequirePermissions(value = CRAWLER_CONTENT_SEARCH)
	public BaseEntity getCrawlerContent(@PathVariable("id") String id, TokenEntity t) {
		BaseEntity be = new BaseEntity();
		CrawlerContent crawlerContent = articleService.getCrawlerContent(id);
		be.setContent(crawlerContent);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 执行cron
	 */
	@ApiOperation(value="执行cron", notes="执行cron")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping(path = "/executeCron/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	public BaseEntity executeCron(@PathVariable("id") int userId, TokenEntity t) {
		BaseEntity be = new BaseEntity();
		// 检查系统是否正在运行定时任务，爬取文章，如下条件成立，说明当前没有线程运行定时任务
		SysLock checkLock = sysLockService.getSysLock();
		if("0".equals(checkLock.getBusinessCron()) && "0".equals(checkLock.getSystemCron())) {
			// 向数据库中赋值
			SysLock sysLock = new SysLock();
			sysLock.setBusinessCron("1");
			sysLockService.updateSysLock(sysLock);
			// 执行爬取任务
			this.articleService.cronjob(userId);
			// 将businessCron设置为0
			sysLock.setBusinessCron("0");
			sysLockService.updateSysLock(sysLock);
			be.setContent("执行爬取...");
			be.setMsgCode(MESSAGE_CODE_OK.getCode());
		}else {
			be.setContent("系统中有其他线程正在爬取文章，请稍后重试！");
			be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
		}
		return be;
	}

	/**
	 * 判断文章配置是否存在
	 */
	@ApiOperation(value="判断文章配置是否存在", notes="判断文章配置是否存在")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "templateConfigId", value = "文章配置id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping(path = "/checkTemplateConfigExists/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	public BaseEntity checkTemplateConfigExists(@PathVariable("id") String templateConfigId, TokenEntity t) {
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setId(templateConfigId);
		int templateConfigCount = articleService.checkTemplateConfigExists(templateConfig);
		BaseEntity be = new BaseEntity();
		// 如果文章配置不存在
		if(templateConfigCount < 1) {
			be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
			be.setContent("该文章配置已被删除！");
		}else {
			be.setMsgCode(MESSAGE_CODE_OK.getCode());
		}
		return be;
	}
}
