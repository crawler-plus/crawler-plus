package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.annotation.RequireToken;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysRole;
import com.crawler.domain.TreeNode;
import com.crawler.service.api.MenuService;
import com.crawler.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.crawler.constant.PermissionsConst.ROLE_MGMT;
import static com.crawler.constant.PermissionsConst.USER_MGMT;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_ERROR;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

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

    /**
     * 查询系统角色
     */
    @ApiOperation(value="查询所有系统角色", notes="查询所有系统角色")
    @ApiImplicitParam(name = "sysRole", value = "系统角色entity", dataType = "SysRole")
    @GetMapping("/queryAll")
    @RequirePermissions(value = ROLE_MGMT)
    @RequireToken
    public BaseEntity queryAll(SysRole sysRole, BaseEntity be) {
        // 分页查询
        PageHelper.startPage(sysRole.getPage(),sysRole.getLimit());
        List<SysRole> sysRoles = roleService.listAll(sysRole);
        // 得到分页后信息
        PageInfo<SysRole> p = new PageInfo<>(sysRoles);
        // 设置总记录数
        be.setTotal(p.getTotal());
        be.setRows(sysRoles);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 查询系统角色(不带条件)
     */
    @ApiOperation(value="查询系统角色(不带条件)", notes="查询系统角色(不带条件)")
    @GetMapping("/queryAllRolesWithoutCondition")
    @RequirePermissions(value = USER_MGMT)
    @RequireToken
    public BaseEntity queryAllRolesWithoutCondition(BaseEntity be) {
        SysRole sysRole = new SysRole();
        List<SysRole> sysRoles = roleService.listAll(sysRole);
        be.setContent(sysRoles);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     *  角色删除
     */
    @ApiOperation(value="删除角色", notes="删除角色")
    @ApiImplicitParam(name = "roleId", value = "系统角色id", required = true, dataType = "int")
    @DeleteMapping(path = "/delete/{id}")
    @RequirePermissions(value = ROLE_MGMT)
    @RequireToken
    public BaseEntity delete(@PathVariable("id") int roleId, BaseEntity be) {
        int countByRoleId = roleService.getUserReferencesCountByRoleId(roleId);
        // 当前角色已经被引用，不能删除
        if(countByRoleId > 0) {
            be.setContent("当前角色已经被引用，不能删除");
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
        }else {
            roleService.delete(roleId);
            be.setContent("删除角色成功");
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
        }
        return be;
    }

    /**
     * 初始化菜单树形节点
     */
    @ApiOperation(value="初始化菜单树形节点", notes="初始化菜单树形节点")
    @GetMapping("/initMenuTree")
    @RequirePermissions(value = ROLE_MGMT)
    @RequireToken
    public BaseEntity initMenuTree(BaseEntity be) {
        List<TreeNode> menuList = menuService.getMenuTreeList();
        be.setContent(menuList);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 添加系统角色
     */
    @ApiOperation(value="添加系统角色", notes="添加系统角色")
    @ApiImplicitParam(name = "addRole", value = "系统角色Entity", dataType = "SysRole")
    @PostMapping(path = "/addRole")
    @RequirePermissions(value = ROLE_MGMT)
    @RequireToken
    public BaseEntity addRole(@Valid SysRole sysRole, BaseEntity be) {
        // 判断角色名称是否存在
        int roleNameExists = roleService.checkRoleNameExists(sysRole);
        // 如果角色名称存在
        if(roleNameExists > 0) {
            be.setContent("该角色名称存在，请换一个角色名称试试");
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
        }else {
            roleService.addRole(sysRole);
            be.setContent("新增角色成功");
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
        }
        return be;
    }

    /**
     * 查询单个角色信息
     */
    @ApiOperation(value="查询单个系统角色信息", notes="查询单个系统角色信息")
    @ApiImplicitParam(name = "roleId", value = "系统角色Id", required = true, dataType = "int")
    @GetMapping(path = "/getRoleInfoById/{id}")
    @RequirePermissions(value = ROLE_MGMT)
    @RequireToken
    public BaseEntity getRoleInfoById(@PathVariable("id") int roleId, BaseEntity be) {
        // 获得角色信息
        SysRole roleByRoleId = roleService.getRoleByRoleId(roleId);
        // 获得树形列表
        List<TreeNode> menuList = menuService.getMenuTreeList();
        // 获得角色对应的菜单ID列表
        List<Integer> availableMenus = roleService.fetchAllMenuId(roleId);
        Map<String, Object> infoMap = Maps.newHashMap();
        infoMap.put("roleInfo", roleByRoleId);
        infoMap.put("menuTreeInfo", menuList);
        infoMap.put("roleRelatedMenuIds", availableMenus);
        be.setContent(infoMap);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 修改系统角色
     */
    @ApiOperation(value="修改系统角色", notes="修改系统角色")
    @ApiImplicitParam(name = "sysRole", value = "系统角色Entity", dataType = "SysRole")
    @PutMapping(path = "/updateRole")
    @RequirePermissions(value = ROLE_MGMT)
    @RequireToken
    public BaseEntity updateUser(@Valid SysRole sysRole, BaseEntity be) {
        // 得到角色信息
        SysRole sysRoleByRoleId = roleService.getRoleByRoleId(sysRole.getId());
        // 得到最新版本信息
        int versionId = sysRoleByRoleId.getVersion();
        // 如果两次的version不相等
        if(versionId != sysRole.getVersion()) {
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
            be.setContent("该角色信息已被其他人修改，请返回重新修改！");
        }else {
            roleService.updateRole(sysRole);
            be.setContent("修改系统角色成功");
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
        }
        return be;
    }

    /**
     * 判断角色是否存在
     */
    @ApiOperation(value="判断角色是否存在", notes="判断角色是否存在")
    @ApiImplicitParam(name = "roleId", value = "系统角色id", required = true, dataType = "int")
    @GetMapping(path = "/checkRoleExists/{id}")
    @RequirePermissions(value = ROLE_MGMT)
    @RequireToken
    public BaseEntity checkRoleExists(@PathVariable("id") int roleId, BaseEntity be) {
        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        int roleCount = roleService.checkRoleExists(sysRole);
        // 如果角色不存在
        if(roleCount < 1) {
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
            be.setContent("该角色已被删除！");
        }else {
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
        }
        return be;
    }

}
