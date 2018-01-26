package com.crawler.controller;

import com.crawler.domain.ArticleTransferEntity;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.CrawlerContent;
import com.crawler.service.api.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 文章Controller
 */
@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping(value = "/")
	public String hello() {
		return "index";
	}

	/**
	 * 列出所有查询出的文章（只包含标题和id）
	 */
	@PostMapping(value = "/fetchArticles")
	@ResponseBody
	public BaseEntity listAllSimpleCrawlerContents(@RequestBody ArticleTransferEntity at, BaseEntity be) {
		// 默认一页显示10条数据
		int limit = 10;
		// 将输入关键字变成小写
		at.setKeyword(at.getKeyword().toLowerCase());
		// 分页查询
		PageHelper.startPage(at.getCurrentPage(), limit);
		List<CrawlerContent> contents = articleService.listAllSimpleCrawlerContents(at);
		// 得到分页后信息
		PageInfo<CrawlerContent> p = new PageInfo<>(contents);
		// 设置总记录数
		be.setTotal(p.getTotal());
		// 设置结果集
		be.setRows(contents);
		// 设置总页数
		be.setTotalPage(p.getPages());
		be.setPage(at.getCurrentPage());
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 根据id获得指定文章
	 */
	@GetMapping(path = "/article/{id}")
	public ModelAndView getCrawlerContent(@PathVariable("id") int id, BaseEntity be) {
		CrawlerContent crawlerContent = articleService.getCrawlerContent(id);
		be.setContent(crawlerContent);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		ModelAndView mav = new ModelAndView("articleDetail");
		mav.addObject("messages", be);
		return mav;
	}

	@GetMapping(path = "/about")
	public String about() {
		return "about";
	}
}
