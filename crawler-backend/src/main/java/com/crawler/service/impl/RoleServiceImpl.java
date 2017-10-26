package com.crawler.service.impl;

import com.crawler.dao.RoleMapper;
import com.crawler.domain.SysRole;
import com.crawler.domain.SysRoleMenu;
import com.crawler.service.api.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SysRole> listAll(SysRole sysRole) {
        return roleMapper.listAll(sysRole);
    }

    @Override
    @Transactional(readOnly = true)
    public int getRolesListCount(SysRole sysRole) {
        return roleMapper.getRolesListCount(sysRole);
    }

    @Override
    @Transactional(readOnly = true)
    public int getUserReferencesCountByRoleId(int roleId) {
        return roleMapper.getUserReferencesCountByRoleId(roleId);
    }

    @Override
    public void delete(int roleId) {
        roleMapper.deleteRole(roleId);
        roleMapper.deleteRoleMenuMapping(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysRole> getRoleByUserId(int userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public int checkRoleNameExists(SysRole sysRole) {
        return roleMapper.checkRoleNameExists(sysRole);
    }

    @Override
    public void addRole(SysRole sysRole) {
        // 首先向角色表中新增一条数据
        roleMapper.roleAdd(sysRole);
        // 得到用户选择的角色对应的菜单
        String roleMenuStr = sysRole.getRoleMenuStr();
        // 如果角色对应的菜单不为空
        if(!StringUtils.isEmpty(roleMenuStr)) {
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            String[] split = roleMenuStr.split(",");
            for (String s : split) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getId());
                sysRoleMenu.setMenuId(Integer.parseInt(s));
                sysRoleMenus.add(sysRoleMenu);
            }
            roleMapper.addSysRoleMenu(sysRoleMenus);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SysRole getRoleByRoleId(int roleId) {
        return roleMapper.getRoleByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> fetchAllMenuId(int roleId) {
        return roleMapper.fetchAllMenuId(roleId);
    }

    @Override
    public void updateRole(SysRole sysRole) {
        // 首先更新角色表
        roleMapper.roleUpdate(sysRole);
        // 然后将角色菜单表中数据根据角色id清空
        int roleId = sysRole.getId();
        roleMapper.deleteRoleMenuMapping(roleId);
        // 最后如果用户选择了角色对应的菜单，向角色菜单表中增加数据
        String roleMenuStr = sysRole.getRoleMenuStr();
        // 如果角色对应的菜单不为空
        if(!StringUtils.isEmpty(roleMenuStr)) {
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            String[] split = roleMenuStr.split(",");
            for (String s : split) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(Integer.parseInt(s));
                sysRoleMenus.add(sysRoleMenu);
            }
            roleMapper.addSysRoleMenu(sysRoleMenus);
        }
    }
}
