package com.crawler.service.api;

import com.crawler.domain.BondMarket;

import java.util.List;

/**
 * 债券市场Service
 */
public interface BondMarketService {

    /**
     * 保存债券市场数据
     * @param bm
     */
    void saveBondMarket(BondMarket bm);

    /**
     * 得到所有bond_market表中的id
     * @return
     */
    List<String> fetchAllIdsFromBondMarket();

    /**
     * 爬取债券市场数据
     */
    void crawBondMarket();

    /**
     * 得到债券市场数据数量
     * @return
     */
    int getBondMarketListCount();

    /**
     * 债券市场分页查询
     * @return
     */
    List<BondMarket> listAll();
}