package com.crawler.controller;

import com.crawler.components.CrawlerProperties;
import com.crawler.constant.Const;
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

//	@Autowired
//	private RedisConfiguration redisConfiguration;
//
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private CrawlerProperties crawlerProperties;

	/**
	 * 执行cron
	 */
	@ApiOperation(value="执行cron", notes="执行cron")
	@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	@GetMapping("/cron")
	public void cron(TokenEntity t) {
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
		BaseEntity be = new BaseEntity();
		// 得到用户信息
		TemplateConfig templateConfig = articleService.getTemplateConfig(te.getId());
		// 得到最新版本信息
		int versionId = templateConfig.getVersion();
		// 如果两次的version不相等
		if(versionId != te.getVersion()) {
			be.setMsgCode(Const.MESSAGE_CODE_ERROR);
			be.setContent("该文章配置信息已被其他人修改，请返回重新修改！");
		}else {
			articleService.editTemplateConfig(te);
			be.setMsgCode(Const.MESSAGE_CODE_OK);
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
	public BaseEntity removeTemplateConfig(@PathVariable("id") String id, TokenEntity t) {
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
	@ApiImplicitParams({
			@ApiImplicitParam(name = "crawlerContent", value = "文章 Entity", required = true, dataType = "CrawlerContent"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping("/listAllCrawlerContents")
	public BaseEntity listAllCrawlerContents(CrawlerContent crawlerContent, TokenEntity t) {
		int crawlerContentSize = articleService.getCrawlerContentSize();
		// 分页查询
		PageHelper.startPage(crawlerContent.getPage(), crawlerContent.getLimit());
		List<CrawlerContent> contents = null;
		// 如果开启redis服务支持
		if(crawlerProperties.isUseRedisCache()) {
			// 从redis中获取文章
			/*String crawlerContent = (String)redisConfiguration.valueOperations(redisTemplate).get("crawlerContent");
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
			}*/
		}else {
			contents = articleService.listAllCrawlerContents();
		}
		BaseEntity be = new BaseEntity();
		be.setTotal(crawlerContentSize);
		be.setRows(contents);
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
		BaseEntity be = new BaseEntity();
		// 检查系统是否正在运行定时任务，爬取文章，如下条件成立，说明当前没有线程运行定时任务
		SysLock checkLock = sysLockService.getSysLock();
		if("0".equals(checkLock.getBusinessCron()) && "0".equals(checkLock.getSystemCron())) {
			// 向数据库中赋值
			SysLock sysLock = new SysLock();
			sysLock.setBusinessCron("1");
			sysLockService.updateSysLock(sysLock);
			// 执行爬取任务
			this.articleService.cronjob();
			// 讲businesscron设置为0
			sysLock.setBusinessCron("0");
			sysLockService.updateSysLock(sysLock);
			// 将redis中的crawlerContent删除
//			redisTemplate.delete("crawlerContent");
			be.setContent("执行爬取...");
			be.setMsgCode(Const.MESSAGE_CODE_OK);
		}else {
			be.setContent("系统中有其他线程正在爬取文章，请稍后重试！");
			be.setMsgCode(Const.MESSAGE_CODE_ERROR);
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
	public BaseEntity checkTemplateConfigExists(@PathVariable("id") String templateConfigId, TokenEntity t) {
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setId(templateConfigId);
		int templateConfigCount = articleService.checkTemplateConfigExists(templateConfig);
		BaseEntity be = new BaseEntity();
		// 如果文章配置不存在
		if(templateConfigCount < 1) {
			be.setMsgCode(Const.MESSAGE_CODE_ERROR);
			be.setContent("该文章配置已被删除！");
		}else {
			be.setMsgCode(Const.MESSAGE_CODE_OK);
		}
		return be;
	}
}
