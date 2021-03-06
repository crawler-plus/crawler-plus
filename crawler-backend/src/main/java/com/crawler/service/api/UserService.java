package com.crawler.service.api;

import com.crawler.domain.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 用户Service
 */
public interface UserService {

    /**
     * 判断用户是否存在
     * @param sysUser
     * @return
     */
    int checkUserExists(SysUser sysUser);

    /**
     * 通过用户登录名称获得用户信息
     * @param loginAccount
     * @return
     */
    SysUser getSysUserByloginAccount(String loginAccount);

    /**
     * 根据条件查找系统用户
     * @param sysUser
     * @return
     */
    List<SysUser> listAll(SysUser sysUser);

    /**
     * 通过用户id获得用户信息
     * @param id
     * @return
     */
    SysUser getSysUserByUserId(int id);

    /**
     * 通过id删除用户信息
     * @param userId
     */
    void delete(int userId);

    /**
     * 新增用户
     * @param sysUser
     */
    void addUser(SysUser sysUser);

    /**
     * 修改用户
     * @param sysUser
     */
    void updateUser(SysUser sysUser);

    /**
     * 判断用户是否可以登录
     * @param sysUser
     * @return
     */
    Map<String, Object> canLogin(SysUser sysUser);
}
