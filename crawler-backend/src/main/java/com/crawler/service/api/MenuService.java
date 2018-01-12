package com.crawler.service.api;

import com.crawler.domain.SysMenu;
import com.crawler.domain.TreeNode;

import java.util.List;

/**
 * 菜单Service
 */
public interface MenuService {

    /**
     * 根据用户ID得到菜单列表
     * @return
     */
    List<SysMenu> getMenuList(int userId);

    /**
     * 根据用户ID得到菜单ID列表
     * @return
     */
    List<Integer> getMenuIdList(int userId);

    /**
     * 获得菜单列表树
     * @return
     */
    List<TreeNode> getMenuTreeList();

    /**
     * 更新网站监控和API文档的url
     * @param prefix
     */
    void updateUrl(String prefix);
}
