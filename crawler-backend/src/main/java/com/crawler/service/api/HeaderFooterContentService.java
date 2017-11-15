package com.crawler.service.api;

import com.crawler.domain.SysHeaderFooterContent;

/**
 * 页眉页脚管理Service
 */
public interface HeaderFooterContentService {

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
