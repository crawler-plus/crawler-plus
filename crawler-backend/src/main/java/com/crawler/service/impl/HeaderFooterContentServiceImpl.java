package com.crawler.service.impl;

import com.crawler.dao.HeaderFooterContentMapper;
import com.crawler.domain.SysHeaderFooterContent;
import com.crawler.service.api.HeaderFooterContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HeaderFooterContentServiceImpl implements HeaderFooterContentService {

	@Autowired
	private HeaderFooterContentMapper headerFooterContentMapper;

    @Override
    public SysHeaderFooterContent getHeaderFooterContent() {
        return headerFooterContentMapper.getHeaderFooterContent();
    }

    @Override
    public void editHeaderFooterContent(SysHeaderFooterContent sysHeaderFooterContent) {
        headerFooterContentMapper.editHeaderFooterContent(sysHeaderFooterContent);
    }
}
