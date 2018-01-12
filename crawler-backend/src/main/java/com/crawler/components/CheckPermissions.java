package com.crawler.components;

import com.crawler.exception.SecurityException;
import com.crawler.service.api.MenuService;
import com.google.common.primitives.Ints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
    void checkPermissions(String uid, int[] permissionValue) {
        // 存储权限列表
        List<Integer> permissionsList = Ints.asList(permissionValue);
        // 根据用户id得到某个用户的权限
        List<Integer> availableMenuList = menuService.getMenuIdList(Integer.parseInt(uid));
        // 求交集
        availableMenuList.retainAll(permissionsList);
        // 表明这个用户没有注解中标明的权限
        if(CollectionUtils.isEmpty(availableMenuList)) {
            throw new SecurityException("insecurity access");
        }
    }
}
