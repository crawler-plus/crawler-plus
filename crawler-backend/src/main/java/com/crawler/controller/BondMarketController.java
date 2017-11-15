package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.BondMarket;
import com.crawler.domain.TokenEntity;
import com.crawler.exception.CrawlerException;
import com.crawler.service.api.BondMarketService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.crawler.constant.PermissionsConst.BOND_MARKET;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 债券市场内容爬取Controller
 */
@RestController
@RequestMapping("/bondMarket")
public class BondMarketController {

	@Autowired
	private BondMarketService bondMarketService;

	/**
	 * 爬取债券市场内容
	 * @param t
	 * @return
	 * @throws CrawlerException
	 */
	@ApiOperation(value="爬取债券市场内容", notes="爬取债券市场内容")
	@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	@GetMapping("/craw")
	@RequirePermissions(value = BOND_MARKET)
	public BaseEntity crawBondMarket(TokenEntity t) {
		BaseEntity be = new BaseEntity();
		bondMarketService.crawBondMarket();
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		be.setContent("爬取债券市场内容成功！");
		return be;
	}

	/**
	 * 用户债券市场内容
	 */
	@ApiOperation(value="用户债券市场内容", notes="用户债券市场内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bondMarket", value = "债券市场entity", required = true, dataType = "BondMarket"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@GetMapping("/queryAll")
	@RequirePermissions(value = BOND_MARKET)
	public BaseEntity queryAll(BondMarket bondMarket, TokenEntity t) {
		int bondMarketCount = bondMarketService.getBondMarketListCount();
		// 分页查询
		PageHelper.startPage(bondMarket.getPage(),bondMarket.getLimit());
		List<BondMarket> bondMarkets = bondMarketService.listAll();
		BaseEntity be = new BaseEntity();
		be.setTotal(bondMarketCount);
		be.setRows(bondMarkets);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}
}