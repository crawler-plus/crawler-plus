package com.crawler.dao;

import com.crawler.domain.SysUser;
import com.crawler.domain.SysUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 */
@Repository
public interface UserMapper {

    /**
     * 判断用户是否存在
     *
     * @param sysUser
     * @return
     */
    int checkUserExists(SysUser sysUser);

    /**
     * 通过用户登录名称获得用户信息
     *
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
    void deleteUser(int userId);

    /**
     * 通过id删除用户角色对应表中的信息
     * @param userId
     */
    void deleteUserRoleMapping(int userId);

    /**
     * 新增用户
     * @param sysUser
     */
    void userAdd(SysUser sysUser);

    /**
     * 新增用户角色
     * @param sysUserRoles
     */
    void addSysUserRole(List<SysUserRole> sysUserRoles);

    /**
     * 更新用户
     * @param sysUser
     */
    void userUpdate(SysUser sysUser);
}