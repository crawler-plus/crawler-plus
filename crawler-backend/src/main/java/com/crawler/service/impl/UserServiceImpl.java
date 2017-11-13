package com.crawler.service.impl;

import com.crawler.components.CrawlerProperties;
import com.crawler.dao.ArticleMapper;
import com.crawler.dao.LogMapper;
import com.crawler.dao.MenuMapper;
import com.crawler.dao.UserMapper;
import com.crawler.domain.*;
import com.crawler.service.api.UserService;
import com.crawler.util.JsonUtils;
import com.crawler.util.MD5Utils;
import com.crawler.util.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private ArticleMapper articleMapper;

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
        articleMapper.removeCrawlerContentByUserId(userId);
        articleMapper.removeTemplateConfigByUserId(userId);
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
        // 如果不是简单需改用户信息
        if(sysUser.getSimpleUpdate() == 0) {
            // 然后将用户角色表中数据根据用户id清空
            int userId = sysUser.getId();
            userMapper.deleteUserRoleMapping(userId);
            // 最后如果用户选择了角色，像用户角色表中增加数据
            // 得到用户选择的角色
            String userRoleStr = sysUser.getUserRoleStr();
            addSysUserRole(userRoleStr, sysUser);
        }
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

    @Override
    public Map<String, Object> canLogin(SysUser sysUser) {
        // 最终返回的数据
        Map<String, Object> infoMap = new HashMap<>();
        String password = MD5Utils.toMD5String(sysUser.getPassword(), crawlerProperties.getMd5Salt());
        sysUser.setPassword(password);
        int exists = this.checkUserExists(sysUser);
        // 如果用户存在
        if(exists > 0) {
            // 得到用户信息
            SysUser sysUserByloginAccount = this.getSysUserByloginAccount(sysUser.getLoginAccount());
            // 得到用户对应的菜单信息
            List<SysMenu> menuList = menuMapper.getMenuList(sysUserByloginAccount.getId());
            List<List<SysMenu>> sList = new ArrayList<>();
            menuList.stream().forEach(t -> {
                // 如果是一个根节点
                if(t.getMenuParentId() == 0) {
                    List<SysMenu> smList = new ArrayList<>();
                    smList.add(t);
                    sList.add(smList);
                }
                // 如果是上一个根节点的子节点
                else {
                    sList.get(sList.size() - 1).add(t);
                }
            });
            // 生成用户token
            TokenEntity userToken = TokenUtils.createUserToken(String.valueOf(sysUserByloginAccount.getId()), 0, crawlerProperties.getUserTokenKey());
            // 往系统log表中添加一条记录
            SysLog sysLog = new SysLog();
            sysLog.setLoginAccount(sysUser.getLoginAccount());
            sysLog.setTypeId(1);
            logMapper.logAdd(sysLog);
            // 将用户表中的token字段更新
            SysUser updateTokenParam = new SysUser();
            updateTokenParam.setId(sysUserByloginAccount.getId());
            updateTokenParam.setLoginToken(userToken.getToken());
            updateUserToken(updateTokenParam);
            infoMap.put("userInfo", sysUserByloginAccount);
            infoMap.put("menuInfo", JsonUtils.objectToJson(sList));
            infoMap.put("token", userToken.getToken());
            infoMap.put("timestamp", userToken.getTimestamp());
        }
        return infoMap;
    }

    @Override
    public void updateUserToken(SysUser sysUser) {
        userMapper.updateUserToken(sysUser);
    }
}
