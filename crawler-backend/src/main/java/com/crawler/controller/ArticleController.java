package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.annotation.RequireToken;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.CrawlerContent;
import com.crawler.domain.TemplateConfig;
import com.crawler.service.RPCApi;
import com.crawler.service.api.ArticleService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
	private RPCApi rpcApi;

	/**
	 * 保存文章配置
	 */
	@ApiOperation(value="保存文章配置", notes="保存文章配置")
	@ApiImplicitParam(name = "te", value = "文章配置entity", dataType = "TemplateConfig")
	@PostMapping("/saveTemplateConfig")
	@RequirePermissions(value = TEMPLATE_MGMT)
	@RequireToken
	public BaseEntity saveTemplateConfig(@Valid TemplateConfig te, BaseEntity be) {
		te.setId(UUID.randomUUID().toString().replace("-", ""));
		articleService.saveTemplateConfig(te);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		be.setContent("保存文章配置成功");
		return be;
	}

	/**
	 * 修改文章配置
	 */
	@ApiOperation(value="修改文章配置", notes="修改文章配置")
	@ApiImplicitParam(name = "te", value = "文章配置entity", dataType = "TemplateConfig")
	@PutMapping("/editTemplateConfig")
	@RequirePermissions(value = TEMPLATE_MGMT)
	@RequireToken
	public BaseEntity editTemplateConfig(@Valid TemplateConfig te, BaseEntity be) {
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
	@ApiImplicitParam(name = "id", value = "文章配置id", required = true, dataType = "String")
	@DeleteMapping(path = "/removeTemplateConfig/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	@RequireToken
	public BaseEntity removeTemplateConfig(@PathVariable("id") String id, BaseEntity be) {
		articleService.removeTemplateConfig(id);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		be.setContent("删除文章配置成功");
		return be;
	}

	/**
	 * 列出所有文章配置
	 */
	@ApiOperation(value="列出所有文章配置", notes="列出所有文章配置")
	@ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int")
	@GetMapping(path = "/listAllTemplateConfig/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	@RequireToken
	public BaseEntity listAllTemplateConfig(@PathVariable("id") int userId, BaseEntity be) {
		List<TemplateConfig> templateConfigs = articleService.listAllTemplateConfig(userId);
		be.setContent(templateConfigs);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 根据id获得文章配置
	 */
	@ApiOperation(value="根据id获得文章配置", notes="根据id获得文章配置")
	@ApiImplicitParam(name = "id", value = "文章配置id", required = true, dataType = "String")
	@GetMapping(path = "/getTemplateConfig/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	@RequireToken
	public BaseEntity getTemplateConfig(@PathVariable("id") String id, BaseEntity be) {
		TemplateConfig templateConfig = articleService.getTemplateConfig(id);
		be.setContent(templateConfig);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 列出所有查询出的文章
	 */
	@ApiOperation(value="列出所有查询出的文章", notes="列出所有查询出的文章")
	@ApiImplicitParam(name = "crawlerContent", value = "文章 Entity", dataType = "CrawlerContent")
	@GetMapping("/listAllCrawlerContents")
	@RequirePermissions(value = CRAWLER_CONTENT_SEARCH)
	@RequireToken
	public BaseEntity listAllCrawlerContents(CrawlerContent crawlerContent, BaseEntity be) {
		int crawlerContentSize = articleService.getCrawlerContentSize(crawlerContent.getUserId());
		// 分页查询
		PageHelper.startPage(crawlerContent.getPage(), crawlerContent.getLimit());
		List<CrawlerContent> contents = articleService.listAllCrawlerContents(crawlerContent.getUserId());
		be.setTotal(crawlerContentSize);
		be.setRows(contents);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 根据id获得指定文章
	 */
	@ApiOperation(value="根据id获得指定文章", notes="根据id获得指定文章")
	@ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String")
	@GetMapping(path = "/getCrawlerContent/{id}")
	@RequirePermissions(value = CRAWLER_CONTENT_SEARCH)
	@RequireToken
	public BaseEntity getCrawlerContent(@PathVariable("id") String id, BaseEntity be) {
		CrawlerContent crawlerContent = articleService.getCrawlerContent(id);
		be.setContent(crawlerContent);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 执行cron
	 */
	@ApiOperation(value="执行cron", notes="执行cron")
	@ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int")
	@GetMapping(path = "/executeCron/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	@RequireToken
	public BaseEntity executeCron(@PathVariable("id") int userId, BaseEntity be) {
		// 检查该用户是否正在进行爬取
		String lockByUserId = rpcApi.getLockByUserId(userId);
		if(StringUtils.isEmpty(lockByUserId)) {
			// 锁定
			rpcApi.lockByUserId(userId);
			// 执行爬取任务
			this.articleService.cronjob(userId);
			// 解除锁定
			rpcApi.deleteLockByUserId(userId);
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
	@ApiImplicitParam(name = "templateConfigId", value = "文章配置id", required = true, dataType = "String")
	@GetMapping(path = "/checkTemplateConfigExists/{id}")
	@RequirePermissions(value = TEMPLATE_MGMT)
	@RequireToken
	public BaseEntity checkTemplateConfigExists(@PathVariable("id") String templateConfigId, BaseEntity be) {
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setId(templateConfigId);
		int templateConfigCount = articleService.checkTemplateConfigExists(templateConfig);
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
