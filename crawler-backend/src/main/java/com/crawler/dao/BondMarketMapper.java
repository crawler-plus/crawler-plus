package com.crawler.dao;

import com.crawler.domain.BondMarket;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 债券市场mapper
 */
@Repository
public interface BondMarketMapper {

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
     * 得到债券市场数据数量
     * @return
     */
    int getBondMarketListCount();

    /**
     * 债券市场分页查询
     * @param bondMarket
     * @return
     */
    List<BondMarket> listAll();
}