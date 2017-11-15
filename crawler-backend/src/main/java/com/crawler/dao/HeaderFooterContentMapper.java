package com.crawler.dao;

import com.crawler.domain.SysHeaderFooterContent;
import org.springframework.stereotype.Repository;

/**
 * 页眉页脚管理mapper
 */
@Repository
public interface HeaderFooterContentMapper {

    /**
     * 获得页眉页脚内容
     * @return
     */
    SysHeaderFooterContent getHeaderFooterContent();

    /**
     * 修改页眉页脚内容
     * @param sysHeaderFooterContent
     */
    void editHeaderFooterContent(SysHeaderFooterContent sysHeaderFooterContent);
}