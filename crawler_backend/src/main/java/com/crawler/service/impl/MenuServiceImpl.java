package com.crawler.service.impl;

import com.crawler.dao.MenuMapper;
import com.crawler.domain.SysMenu;
import com.crawler.domain.TreeNode;
import com.crawler.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SysMenu> getMenuList(int userId) {
        return menuMapper.getMenuList(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreeNode> getMenuTreeList() {
        return menuMapper.getMenuTreeList();
    }

    @Override
    public void updateUrl(String prefix) {
        menuMapper.updateUrl(prefix);
    }
}
