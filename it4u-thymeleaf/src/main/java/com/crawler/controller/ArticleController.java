package com.crawler.controller;

import com.crawler.cache.RedisCacheHandler;
import com.crawler.domain.*;
import com.crawler.service.api.ArticleService;
import com.crawler.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 文章Controller
 */
@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private RedisCacheHandler redisCacheHandler;

	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;

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
	@GetMapping(path = "/article/{id:\\d+}", produces = "text/html")
	@ResponseBody
	public String getCrawlerContent(@PathVariable("id") int id
			, BaseEntity be, HttpServletRequest request, HttpServletResponse response,
			 Model model) {
		// 取缓存
		String article = (String)redisCacheHandler.getCache("article:" + id);
		if(!StringUtils.isEmpty(article)) {
			return article;
		}
		// 去数据库查文章
		CrawlerContent crawlerContent = articleService.getCrawlerContent(id);
		// 如果查询不到文章
		if(crawlerContent == null) {
			// 将不存在的文章也写入缓存一个值，防止被恶意刷
			redisCacheHandler.setCacheWithTimeout("article:" + id, "unknown", 30L, TimeUnit.MINUTES);
			return "unknown";
		}
		// 走到这里表明查到了文章，将文章内容设置到model中
		be.setContent(crawlerContent);
		model.addAttribute("messages", be);
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(),
				model.asMap());
		// 手动渲染
		article = thymeleafViewResolver.getTemplateEngine().process("articleDetail",
				ctx);
		// 写缓存
		if(!StringUtils.isEmpty(article)) {
			redisCacheHandler.setCacheWithTimeout("article:" + id, article, 30L, TimeUnit.DAYS);
		}
		return article;
	}

	@GetMapping(path = "/about")
	public String about() {
		return "about";
	}

	@GetMapping(path = "/stat")
	public String test() {
		return "stat";
	}

	@GetMapping(path = "/terminalStat")
	@ResponseBody
	public Map<String, Object> terminalStat() {
		// 从redis中获取数据
		Jedis jedis = RedisUtil.getJedis();
		Map<String, String> terminalType = jedis.hgetAll("terminalType");
		RedisUtil.returnResource(jedis);
		List<TerminalStat> list = new ArrayList<>();
		List<String> namesList = new ArrayList<>();
		Iterator itor= terminalType.entrySet().iterator();
		while(itor.hasNext()){
			Map.Entry<String,String> entry = (Map.Entry<String,String>)itor.next();
            String key = entry.getKey().equals("0") ? "PC端" : "移动端";
            String value = entry.getValue();
            TerminalStat ts = new TerminalStat();
            ts.setName(key);
            ts.setValue(value);
            list.add(ts);
			namesList.add(key);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("namesList", namesList);
		map.put("data", list);
		return map;
	}

	@GetMapping(path = "/operationSystemStat")
	@ResponseBody
	public Map<String, Object> operationSystemStat() {
		// 从redis中获取数据
		Jedis jedis = RedisUtil.getJedis();
		Map<String, String> terminalType = jedis.hgetAll("operationSystemType");
		RedisUtil.returnResource(jedis);
		List<String> namesList = new ArrayList<>();
		List<Long> valuesList = new ArrayList<>();
		Iterator itor= terminalType.entrySet().iterator();
		while(itor.hasNext()){
			Map.Entry<String,String> entry = (Map.Entry<String,String>)itor.next();
			String key = entry.getKey();
			String value = entry.getValue();
			valuesList.add(Long.parseLong(value));
			namesList.add(key);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("namesList", namesList);
		map.put("valuesList", valuesList);
		return map;
	}

	@GetMapping(path = "/totalClickCountStat")
	@ResponseBody
	public Map<String, Object> totalClickCountStat() {
		// 从redis中获取数据
		Jedis jedis = RedisUtil.getJedis();
		String totalClickCount = jedis.get("totalClickCount");
		RedisUtil.returnResource(jedis);
		Map<String, Object> map = new HashMap<>();
		map.put("totalClickCount", totalClickCount);
		return map;
	}
}
