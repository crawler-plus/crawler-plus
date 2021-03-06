package com.crawler.service.impl;

import com.crawler.dao.BondMarketMapper;
import com.crawler.domain.BondMarket;
import com.crawler.service.api.BondMarketService;
import com.crawler.util.LoggerUtils;
import com.google.common.collect.Maps;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class BondMarketServiceImpl implements BondMarketService {

	@Autowired
	private BondMarketMapper bondMarketMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void saveBondMarket(BondMarket bm) {
        bondMarketMapper.saveBondMarket(bm);
    }

    @Override
    public List<String> fetchAllIdsFromBondMarket() {
        return bondMarketMapper.fetchAllIdsFromBondMarket();
    }

    @Override
    public void crawBondMarket() {
        HashMap<String, Object> paramMap = Maps.newHashMap();
        String jsonContent = HttpUtil.post("http://www.cninfo.com.cn/cninfo-new/disclosure/szse_latest", paramMap);
        // 解析返回的json数据
        try {
            // 得到最外层的json数据
            JSONObject json = new JSONObject(jsonContent);
            // 得到核心数据array对象
            JSONArray outArray = json.getJSONArray("classifiedAnnouncements");
            if(!ObjectUtils.isEmpty(outArray)) {
                // 循环外层的array对象，得到里面的每个股份公司的数据，也是一个json数组
                for(int i = 0; i < outArray.size(); i ++) {
                    JSONArray innerArray = outArray.getJSONArray(i);
                    if(!ObjectUtils.isEmpty(innerArray)) {
                        // 循环每一个内层数据
                        for(int j = 0; j < innerArray.size(); j ++) {
                            List<String> allIds = this.fetchAllIdsFromBondMarket();
                            // 得到每个条目的数据
                            JSONObject eachObject = innerArray.getJSONObject(j);
                            // 得到主键id
                            String announcementId = eachObject.getStr("announcementId");
                            if(!CollectionUtils.isEmpty(allIds)) {
                                // 如果数据存在，不插入，继续下一次循环
                                if(allIds.contains(announcementId)) {
                                    continue;
                                }
                            }
                            // 得到证券代码
                            String secCode = eachObject.getStr("secCode");
                            // 得到证券简称
                            String secName = eachObject.getStr("secName");
                            // 得到公告标题
                            String announcementTitle = eachObject.getStr("announcementTitle");
                            // 得到公告类别
                            String announcementTypeName = eachObject.getStr("announcementTypeName");
                            // 得到发布时间
                            long announcementTime = eachObject.getLong("announcementTime");
                            // 把发布时间转成yyyy-mm-dd格式的字符串
                            Date d = new Date(announcementTime);
                            String announcementTimeStr = DateUtil.format(d, "yyyy-MM-dd");
                            // 得到pdf下载url字符串
                            String adjunctUrl = eachObject.getStr("adjunctUrl");
                            BondMarket bm = new BondMarket();
                            bm.setId(announcementId);
                            bm.setCode(secCode);
                            bm.setAbbre(secName);
                            bm.setTitle(announcementTitle);
                            bm.setCategory(announcementTypeName);
                            bm.setPublishDate(announcementTimeStr);
                            bm.setAdjunctUrl(adjunctUrl);
                            this.saveBondMarket(bm);
                            String[] split = adjunctUrl.split("/");
                            String numId = split[2].replace(".PDF", "");
                            String dateStamp = split[1];
                            String downloadUrl = "http://www.cninfo.com.cn/cninfo-new/disclosure/szse/download/" + numId + "?announceTime=" + dateStamp;
                            String downloadPdfName = announcementId + "-" + announcementTitle + ".pdf";
                            // 下载pdf
                            HttpUtil.downloadFile(downloadUrl, FileUtil.file(downloadPdfName));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LoggerUtils.printExceptionLogger(logger, e);
        }
    }

    @Override
    public List<BondMarket> listAll() {
        return bondMarketMapper.listAll();
    }

    @Override
    public BondMarket getBondMarketItemById(String id) {
        return bondMarketMapper.getBondMarketItemById(id);
    }
}
