package com.crawler.controller;

import com.crawler.components.CrawlerProperties;
import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysMenu;
import com.crawler.domain.SysRole;
import com.crawler.domain.SysUser;
import com.crawler.service.api.MenuService;
import com.crawler.service.api.RoleService;
import com.crawler.service.api.UserService;
import com.crawler.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CrawlerProperties crawlerProperties;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", required = true, dataType = "SysUser")
    public BaseEntity login(SysUser sysUser) {
        String password = MD5Utils.toMD5String(sysUser.getPassword(), crawlerProperties.getMd5Salt());
        sysUser.setPassword(password);
        BaseEntity be = new BaseEntity();
        int exists = userService.checkUserExists(sysUser);
        // 如果用户存在
        if(exists > 0) {
            // 得到用户信息
            SysUser sysUserByloginAccount = userService.getSysUserByloginAccount(sysUser.getLoginAccount());
            // 得到用户对应的菜单信息
            List<SysMenu> menuList = menuService.getMenuList(sysUserByloginAccount.getId());
            Map<String, Object> infoMap = new HashMap<>();
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
            infoMap.put("userInfo", sysUserByloginAccount);
            infoMap.put("menuInfo", sList);
            be.setContent(infoMap);
            be.setMsgCode(Const.MESSAGE_CODE_OK);
        }else {
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
            be.setContent("用户名或密码错误，请重试");
        }
        return be;
    }

    /**
     * 用户查询
     */
    @GetMapping("/queryAll")
    @ApiOperation(value="查询所有用户", notes="查询所有用户")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", required = true, dataType = "SysUser")
    public BaseEntity queryAll(SysUser sysUser) {
        int userCount =  userService.getUsersListCount(sysUser);
        // 分页查询
		PageHelper.startPage(sysUser.getPage(),sysUser.getLimit());
        List<SysUser> sysUsers = userService.listAll(sysUser);
        BaseEntity be = new BaseEntity();
        be.setTotal(userCount);
        be.setRows(sysUsers);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 用户删除
     */
    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value="删除用户", notes="删除用户")
    @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int")
    public BaseEntity delete(@PathVariable("id") int userId) {
        BaseEntity be = new BaseEntity();
        SysUser sysUser = userService.getSysUserByUserId(userId);
        // 如果是管理员，不允许删除，否则系统中一个用户都没有了
        if("admin".equals(sysUser.getLoginAccount())) {
            be.setContent("系统管理员不允许删除");
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
        }else {
            userService.delete(userId);
            be.setContent("删除用户成功");
            be.setMsgCode(Const.MESSAGE_CODE_OK);
        }
        return be;
    }

    /**
     * 查询单个用户信息
     */
    @GetMapping(path = "/queryUser/{id}")
    @ApiOperation(value="查询单个用户信息", notes="查询单个用户信息")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int")
    public BaseEntity queryUser(@PathVariable("id") int userId) {
        SysUser sysUserByUserId = userService.getSysUserByUserId(userId);
        BaseEntity be = new BaseEntity();
        be.setContent(sysUserByUserId);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 查询单个用户的角色
     */
    @GetMapping(path = "/getRoleByUserId/{id}")
    @ApiOperation(value="查询单个用户的角色", notes="查询单个用户的角色")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int")
    public BaseEntity getRoleByUserId(@PathVariable("id") int userId) {
        List<SysRole> roleByUserId = roleService.getRoleByUserId(userId);
        BaseEntity be = new BaseEntity();
        be.setContent(roleByUserId);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 添加系统用户
     */
    @PostMapping(path = "/addUser")
    @ApiOperation(value="添加系统用户", notes="添加系统用户")
    @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", required = true, dataType = "SysUser")
    public BaseEntity addUser(SysUser sysUser) {
        BaseEntity be = new BaseEntity();
        // 判断用户是否存在
        int userExists = userService.checkUserExists(sysUser);
        // 如果用户存在
        if(userExists > 0) {
            be.setContent("该用户登录帐号已经存在，请换个登录帐号试试");
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
        }else {
            userService.addUser(sysUser);
            be.setContent("新增用户成功");
            be.setMsgCode(Const.MESSAGE_CODE_OK);
        }
        return be;
    }

    /**
     * 修改系统用户
     */
    @PutMapping(path = "/updateUser")
    @ApiOperation(value="修改系统用户", notes="修改系统用户")
    @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", required = true, dataType = "SysUser")
    public BaseEntity updateUser(SysUser sysUser) {
        BaseEntity be = new BaseEntity();
        userService.updateUser(sysUser);
        be.setContent("修改用户成功");
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }
}
