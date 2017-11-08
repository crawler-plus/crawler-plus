package com.crawler.components;

import com.crawler.constant.PermissionsConst;
import com.crawler.domain.SysMenu;
import com.crawler.exception.TokenInvalidException;
import com.crawler.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 验证用户权限公共类
 */
@Component
public class CheckPermissions {

    @Autowired
    private MenuService menuService;

    /**
     * 验证某个用户是否有某个操作的权限
     * @param uid
     * @param permissionValue
     */
    public void checkPermissions(String uid, String[] permissionValue) {
        // 存储权限列表
        List<Integer> permissionsList = new ArrayList<Integer>();
        String pStr = Arrays.toString(permissionValue);
        // 用户管理权限
        if(pStr.contains(PermissionsConst.USER_MGMT)) {
            permissionsList.add(4);
        }
        // 角色管理权限
        if(pStr.contains(PermissionsConst.ROLE_MGMT)) {
            permissionsList.add(5);
        }
        // 系统日志管理权限
        if(pStr.contains(PermissionsConst.LOG_MGMT)) {
            permissionsList.add(13);
        }
        // 网页爬虫查询权限
        if(pStr.contains(PermissionsConst.NET_CRAWLER_SEARCH)) {
            permissionsList.add(7);
        }
        // 二维码信息查询权限
        if(pStr.contains(PermissionsConst.QRCODE_SEARCH)) {
            permissionsList.add(8);
        }
        // 微信公众号查询权限
        if(pStr.contains(PermissionsConst.WECHAT_PUBLIC_SEARCH)) {
            permissionsList.add(9);
        }
        // 网站模版管理权限
        if(pStr.contains(PermissionsConst.TEMPLATE_MGMT)) {
            permissionsList.add(11);
        }
        // 文章查询权限
        if(pStr.contains(PermissionsConst.CRAWLER_CONTENT_SEARCH)) {
            permissionsList.add(12);
        }
        // 自服务管理-用户信息修改权限
        if(pStr.contains(PermissionsConst.SELF_INFO_UPDATE)) {
            permissionsList.add(15);
        }
        // 根据用户id得到某个用户的权限
        List<SysMenu> menuList = menuService.getMenuList(Integer.parseInt(uid));
        // 装这个用户可以访问的菜单id列表
        List<Integer> availableMenuList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(menuList)) {
            for (SysMenu sysMenu : menuList) {
                availableMenuList.add(sysMenu.getMenuId());
            }
        }
        // 求交集
        availableMenuList.retainAll(permissionsList);
        // 表明这个用户没有注解中标明的权限
        if(availableMenuList.isEmpty()) {
            throw new TokenInvalidException("invalid token");
        }
    }
}
