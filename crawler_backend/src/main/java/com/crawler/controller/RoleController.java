package com.crawler.controller;

import com.crawler.components.CheckToken;
import com.crawler.constant.Const;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysRole;
import com.crawler.domain.TokenEntity;
import com.crawler.domain.TreeNode;
import com.crawler.service.api.MenuService;
import com.crawler.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色Controller
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CheckToken checkToken;

    /**
     * 查询系统角色
     */
    @ApiOperation(value="查询所有系统角色", notes="查询所有系统角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "系统角色entity", required = true, dataType = "SysRole"),
            @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @GetMapping("/queryAll")
    public BaseEntity queryAll(SysRole sysRole, TokenEntity t) {
        // 验证token
        checkToken.checkToken(t);
        int roleCount =  roleService.getRolesListCount(sysRole);
        // 分页查询
        PageHelper.startPage(sysRole.getPage(),sysRole.getLimit());
        List<SysRole> sysRoles = roleService.listAll(sysRole);
        BaseEntity be = new BaseEntity();
        be.setTotal(roleCount);
        be.setRows(sysRoles);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 查询系统角色(不带条件)
     */
    @ApiOperation(value="查询系统角色(不带条件)", notes="查询系统角色(不带条件)")
    @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    @GetMapping("/queryAllRolesWithoutCondition")
    public BaseEntity queryAllRolesWithoutCondition(TokenEntity t) {
        // 验证token
        checkToken.checkToken(t);
        SysRole sysRole = new SysRole();
        List<SysRole> sysRoles = roleService.listAll(sysRole);
        BaseEntity be = new BaseEntity();
        be.setContent(sysRoles);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     *  角色删除
     */
    @ApiOperation(value="删除角色", notes="删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "系统角色id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @DeleteMapping(path = "/delete/{id}")
    public BaseEntity delete(@PathVariable("id") int roleId, TokenEntity t) {
        // 验证token
        checkToken.checkToken(t);
        BaseEntity be = new BaseEntity();
        int countByRoleId = roleService.getUserReferencesCountByRoleId(roleId);
        // 当前角色已经被引用，不能删除
        if(countByRoleId > 0) {
            be.setContent("当前角色已经被引用，不能删除");
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
        }else {
            roleService.delete(roleId);
            be.setContent("删除角色成功");
            be.setMsgCode(Const.MESSAGE_CODE_OK);
        }
        return be;
    }

    /**
     * 初始化菜单树形节点
     */
    @ApiOperation(value="初始化菜单树形节点", notes="初始化菜单树形节点")
    @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    @GetMapping("/initMenuTree")
    public BaseEntity initMenuTree(TokenEntity t) {
        // 验证token
        checkToken.checkToken(t);
        List<TreeNode> menuList = menuService.getMenuTreeList();
        BaseEntity be = new BaseEntity();
        be.setContent(menuList);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 添加系统角色
     */
    @ApiOperation(value="添加系统角色", notes="添加系统角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addRole", value = "系统角色Entity", required = true, dataType = "SysRole"),
            @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @PostMapping(path = "/addRole")
    public BaseEntity addRole(SysRole sysRole, TokenEntity t) {
        // 验证token
        checkToken.checkToken(t);
        BaseEntity be = new BaseEntity();
        // 判断角色名称是否存在
        int roleNameExists = roleService.checkRoleNameExists(sysRole);
        // 如果角色名称存在
        if(roleNameExists > 0) {
            be.setContent("该角色名称存在，请换一个角色名称试试");
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
        }else {
            roleService.addRole(sysRole);
            be.setContent("新增角色成功");
            be.setMsgCode(Const.MESSAGE_CODE_OK);
        }
        return be;
    }

    /**
     * 查询单个角色信息
     */
    @ApiOperation(value="查询单个系统角色信息", notes="查询单个系统角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "系统角色Id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @GetMapping(path = "/getRoleInfoById/{id}")
    public BaseEntity getRoleInfoById(@PathVariable("id") int roleId, TokenEntity t) {
        // 验证token
        checkToken.checkToken(t);
        BaseEntity be = new BaseEntity();
        // 获得角色信息
        SysRole roleByRoleId = roleService.getRoleByRoleId(roleId);
        // 获得树形列表
        List<TreeNode> menuList = menuService.getMenuTreeList();
        // 获得角色对应的菜单ID列表
        List<Integer> availableMenus = roleService.fetchAllMenuId(roleId);
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("roleInfo", roleByRoleId);
        infoMap.put("menuTreeInfo", menuList);
        infoMap.put("roleRelatedMenuIds", availableMenus);
        be.setContent(infoMap);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 修改系统角色
     */
    @ApiOperation(value="修改系统角色", notes="修改系统角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "系统角色Entity", required = true, dataType = "SysRole"),
            @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @PutMapping(path = "/updateRole")
    public BaseEntity updateUser(SysRole sysRole, TokenEntity t) {
        // 验证token
        checkToken.checkToken(t);
        BaseEntity be = new BaseEntity();
        roleService.updateRole(sysRole);
        be.setContent("修改系统角色成功");
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

}
