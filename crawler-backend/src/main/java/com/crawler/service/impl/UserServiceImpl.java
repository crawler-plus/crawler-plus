package com.crawler.service.impl;

import com.crawler.components.CrawlerProperties;
import com.crawler.dao.UserMapper;
import com.crawler.domain.SysUser;
import com.crawler.domain.SysUserRole;
import com.crawler.service.api.UserService;
import com.crawler.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CrawlerProperties crawlerProperties;

    @Override
    @Transactional(readOnly = true)
    public int checkUserExists(SysUser sysUser) {
        return userMapper.checkUserExists(sysUser);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser getSysUserByloginAccount(String loginAccount) {
        return userMapper.getSysUserByloginAccount(loginAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysUser> listAll(SysUser sysUser) {
        return userMapper.listAll(sysUser);
    }

    @Override
    @Transactional(readOnly = true)
    public int getUsersListCount(SysUser sysUser) {
        return userMapper.getUsersListCount(sysUser);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser getSysUserByUserId(int id) {
        return userMapper.getSysUserByUserId(id);
    }

    @Override
    public void delete(int userId) {
        userMapper.deleteUser(userId);
        userMapper.deleteUserRoleMapping(userId);
    }

    @Override
    public void addUser(SysUser sysUser) {
        // 默认密码：123456
        String password = MD5Utils.toMD5String(crawlerProperties.getDefaultPassword(), crawlerProperties.getMd5Salt());
        sysUser.setPassword(password);
        // 首先向用户表中新增一条数据
        userMapper.userAdd(sysUser);
        // 得到用户选择的角色
        String userRoleStr = sysUser.getUserRoleStr();
        addSysUserRole(userRoleStr, sysUser);
    }

    @Override
    public void updateUser(SysUser sysUser) {
        String password = MD5Utils.toMD5String(sysUser.getPassword(), crawlerProperties.getMd5Salt());
        sysUser.setPassword(password);
        // 首先更新用户表
        userMapper.userUpdate(sysUser);
        // 然后将用户角色表中数据根据用户id清空
        int userId = sysUser.getId();
        userMapper.deleteUserRoleMapping(userId);
        // 最后如果用户选择了角色，像用户角色表中增加数据
        // 得到用户选择的角色
        String userRoleStr = sysUser.getUserRoleStr();
        addSysUserRole(userRoleStr, sysUser);
    }

    /**
     * 通过用户角色字符串增加用户角色列表
     * @param userRoleStr
     */
    private void addSysUserRole(String userRoleStr, SysUser sysUser) {
        // 如果用户角色列表不为空
        if(!StringUtils.isEmpty(userRoleStr)) {
            List<SysUserRole> sysUserRoles = new ArrayList<>();
            String[] split = userRoleStr.split(",");
            for (String s : split) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getId());
                sysUserRole.setRoleId(Integer.parseInt(s));
                sysUserRoles.add(sysUserRole);
            }
            userMapper.addSysUserRole(sysUserRoles);
        }
    }
}
