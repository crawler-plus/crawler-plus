package com.crawler.service.impl;

import com.crawler.components.CrawlerProperties;
import com.crawler.components.RequestHolderConfiguration;
import com.crawler.dao.ArticleMapper;
import com.crawler.dao.MenuMapper;
import com.crawler.dao.UserMapper;
import com.crawler.domain.SysMenu;
import com.crawler.domain.SysUser;
import com.crawler.domain.SysUserRole;
import com.crawler.domain.TokenEntity;
import com.crawler.service.RPCApi;
import com.crawler.service.api.UserService;
import com.crawler.util.TokenUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CrawlerProperties crawlerProperties;

    @Autowired
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired
    private RPCApi rpcApi;

    @Override
    public int checkUserExists(SysUser sysUser) {
        return userMapper.checkUserExists(sysUser);
    }

    @Override
    public SysUser getSysUserByloginAccount(String loginAccount) {
        return userMapper.getSysUserByloginAccount(loginAccount);
    }

    @Override
    public List<SysUser> listAll(SysUser sysUser) {
        return userMapper.listAll(sysUser);
    }

    @Override
    public int getUsersListCount(SysUser sysUser) {
        return userMapper.getUsersListCount(sysUser);
    }

    @Override
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
        int userFrom = sysUser.getAddUserFrom();
        String password;
        if(1 == userFrom) {
            password = SecureUtil.md5(crawlerProperties.getDefaultPassword() + crawlerProperties.getMd5Salt());
        }else {
            password = SecureUtil.md5(sysUser.getPassword() + crawlerProperties.getMd5Salt());
        }
        sysUser.setPassword(password);
        // 首先向用户表中新增一条数据
        userMapper.userAdd(sysUser);
        // 如果是系统注册来的用户
        if(0 == userFrom) {
            sysUser.setUserRoleStr("6");
        }
        // 得到用户选择的角色
        String userRoleStr = sysUser.getUserRoleStr();
        addSysUserRole(userRoleStr, sysUser);
    }

    @Override
    public void updateUser(SysUser sysUser) {
        // 将用户菜单字符串从redis中删除
        rpcApi.deleteUserMenuInfo(sysUser.getId());
        String password = SecureUtil.md5(sysUser.getPassword() + crawlerProperties.getMd5Salt());
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
            List<SysUserRole> sysUserRoles = Lists.newArrayList();
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
        Map<String, Object> infoMap = Maps.newHashMap();
        String password = SecureUtil.md5(sysUser.getPassword() + crawlerProperties.getMd5Salt());
        sysUser.setPassword(password);
        int exists = this.checkUserExists(sysUser);
        // 如果用户存在
        if(exists > 0) {
            // 得到用户信息
            SysUser sysUserByloginAccount = this.getSysUserByloginAccount(sysUser.getLoginAccount());
            // 判断是否可以从redis中获取用户的菜单列表字符串
            String userMenuStr = rpcApi.getUserMenuInfo(sysUserByloginAccount.getId());
            if(StringUtils.isEmpty(userMenuStr)) {
                // 得到用户对应的菜单信息
                List<SysMenu> menuList = menuMapper.getMenuList(sysUserByloginAccount.getId());
                List<List<SysMenu>> sList = Lists.newArrayList();
                menuList.forEach(t -> {
                    // 如果是一个根节点
                    if(t.getMenuParentId() == 0) {
                        List<SysMenu> smList = Lists.newArrayList();
                        smList.add(t);
                        sList.add(smList);
                    }
                    // 如果是上一个根节点的子节点
                    else {
                        sList.get(sList.size() - 1).add(t);
                    }
                });
                userMenuStr = JSONUtil.toJsonStr(sList);
                // 写用户菜单字符串到redis
                Map<String, String> menuMap = new HashMap<>();
                menuMap.put("userId", String.valueOf(sysUserByloginAccount.getId()));
                menuMap.put("userInfo", userMenuStr);
                rpcApi.writeUserMenuInfoToRedis(menuMap);
            }
            // 生成用户token
            TokenEntity uToken = TokenUtils.createUserToken(String.valueOf(sysUserByloginAccount.getId()), crawlerProperties.getUserTokenKey());
            // 向redis中插入用户token,token的key为userToken_用户id
            rpcApi.writeUserToken(sysUserByloginAccount.getId() + ":" + uToken.getToken(), uToken.getToken());
            infoMap.put("userInfo", sysUserByloginAccount);
            infoMap.put("menuInfo", userMenuStr);
            infoMap.put("token", uToken.getToken());
        }
        return infoMap;
    }
}
