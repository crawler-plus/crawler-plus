package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.components.CrawlerProperties;
import com.crawler.domain.*;
import com.crawler.service.api.LogService;
import com.crawler.service.api.RoleService;
import com.crawler.service.api.SysCaptchaService;
import com.crawler.service.api.UserService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.crawler.constant.PermissionsConst.SELF_INFO_UPDATE;
import static com.crawler.constant.PermissionsConst.USER_MGMT;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_ERROR;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LogService logService;

    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private CrawlerProperties crawlerProperties;

    /**
     * 用户登录
     */
    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
    @PostMapping("/login")
    public BaseEntity login(SysUser sysUser) {
        BaseEntity be = new BaseEntity();
        boolean useCaptcha = crawlerProperties.isUseCaptcha();
        // 验证码是否通过
        boolean captchaAccess;
        // 如果配置了需要验证码登录
        if(useCaptcha) {
            List<String> sysCaptchas = sysCaptchaService.listAllSysCaptcha();
            // 判断验证码是否正确
            String captcha = sysUser.getCaptcha();
            // 如果验证码正确
            captchaAccess = sysCaptchas.contains(captcha.toLowerCase());
        }
        // 如果没有配置验证码登录
        else {
            captchaAccess = true;
        }
        // 如果验证码没有通过
        if(!captchaAccess) {
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
            be.setContent("验证码错误，请重新输入");
        }
        // 如果验证码通过
        else {
            Map<String, Object> infoMap = userService.canLogin(sysUser);
            if(infoMap.isEmpty()) {
                be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
                be.setContent("用户名或密码错误，请重试");
            }else {
                be.setContent(infoMap);
                be.setMsgCode(MESSAGE_CODE_OK.getCode());
            }
        }
        return be;
    }

    /**
     * 用户查询
     */
    @ApiOperation(value="查询所有用户", notes="查询所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @GetMapping("/queryAll")
    @RequirePermissions(value = USER_MGMT)
    public BaseEntity queryAll(SysUser sysUser, TokenEntity te) {
        int userCount = userService.getUsersListCount(sysUser);
        // 分页查询
		PageHelper.startPage(sysUser.getPage(),sysUser.getLimit());
        List<SysUser> sysUsers = userService.listAll(sysUser);
        BaseEntity be = new BaseEntity();
        be.setTotal(userCount);
        be.setRows(sysUsers);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 用户删除
     */
    @ApiOperation(value="删除用户", notes="删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @DeleteMapping(path = "/delete/{id}")
    @RequirePermissions(value = USER_MGMT)
    public BaseEntity delete(@PathVariable("id") int userId, TokenEntity te) {
        BaseEntity be = new BaseEntity();
        SysUser sysUser = userService.getSysUserByUserId(userId);
        // 如果是管理员，不允许删除，否则系统中一个用户都没有了
        if("admin".equals(sysUser.getLoginAccount())) {
            be.setContent("系统管理员不允许删除");
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
        }else {
            userService.delete(userId);
            be.setContent("删除用户成功");
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
        }
        return be;
    }

    /**
     * 查询单个用户信息
     */
    @ApiOperation(value="查询单个用户信息", notes="查询单个用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @GetMapping(path = "/queryUser/{id}")
    @RequirePermissions(value = {USER_MGMT, SELF_INFO_UPDATE})
    public BaseEntity queryUser(@PathVariable("id") int userId, TokenEntity te) {
        SysUser sysUserByUserId = userService.getSysUserByUserId(userId);
        BaseEntity be = new BaseEntity();
        be.setContent(sysUserByUserId);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 查询单个用户的角色
     */
    @ApiOperation(value="查询单个用户的角色", notes="查询单个用户的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @GetMapping(path = "/getRoleByUserId/{id}")
    @RequirePermissions(value = USER_MGMT)
    public BaseEntity getRoleByUserId(@PathVariable("id") int userId, TokenEntity te) {
        List<SysRole> roleByUserId = roleService.getRoleByUserId(userId);
        BaseEntity be = new BaseEntity();
        be.setContent(roleByUserId);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 添加系统用户
     */
    @ApiOperation(value="添加系统用户", notes="添加系统用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", dataType = "SysUser"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @PostMapping(path = "/addUser")
    @RequirePermissions(value = USER_MGMT)
    public BaseEntity addUser(SysUser sysUser, TokenEntity te) {
        BaseEntity be = new BaseEntity();
        // 判断用户是否存在
        int userExists = userService.checkUserExists(sysUser);
        // 如果用户存在
        if(userExists > 0) {
            be.setContent("该用户登录帐号已经存在，请换个登录帐号试试");
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
        }else {
            userService.addUser(sysUser);
            be.setContent("新增用户成功");
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
        }
        return be;
    }

    /**
     * 修改系统用户
     */
    @ApiOperation(value="修改系统用户", notes="修改系统用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", dataType = "SysUser"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @PutMapping(path = "/updateUser")
    @RequirePermissions(value = {USER_MGMT, SELF_INFO_UPDATE})
    public BaseEntity updateUser(SysUser sysUser, TokenEntity te) {
        BaseEntity be = new BaseEntity();
        // 得到用户信息
        SysUser sysUserByUserId = userService.getSysUserByUserId(sysUser.getId());
        // 得到最新版本信息
        int versionId = sysUserByUserId.getVersion();
        // 如果两次的version不相等
        if(versionId != sysUser.getVersion()) {
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
            be.setContent("该用户信息已被其他人修改，请返回重新修改！");
        }else {
            userService.updateUser(sysUser);
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
            be.setContent("修改用户成功");
        }
        return be;
    }

    /**
     * 用户退出
     */
    @ApiOperation(value="用户退出", notes="用户退出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @GetMapping(path = "/logout/{id}")
    public BaseEntity logout(@PathVariable("id") int userId, TokenEntity te) {
        BaseEntity be = new BaseEntity();
        SysUser sysUserByUserId = userService.getSysUserByUserId(userId);
        // 往系统log表中添加一条记录
        SysLog sysLog = new SysLog();
        sysLog.setLoginAccount(sysUserByUserId.getLoginAccount());
        sysLog.setTypeId(2);
        logService.logAdd(sysLog);
        // 将用户表中的token字段更新
        SysUser updateTokenParam = new SysUser();
        updateTokenParam.setId(userId);
        updateTokenParam.setLoginToken("");
        userService.updateUserToken(updateTokenParam);
        be.setContent("用户退出");
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 判断用户是否存在
     */
    @ApiOperation(value="判断用户是否存在", notes="判断用户是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", dataType = "TokenEntity")
    })
    @GetMapping(path = "/checkUserExists/{id}")
    public BaseEntity checkUserExists(@PathVariable("id") int userId, TokenEntity te) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        int userCount = userService.checkUserExists(sysUser);
        BaseEntity be = new BaseEntity();
        // 如果用户不存在
        if(userCount < 1) {
            be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
            be.setContent("该用户已被删除！");
        }else {
            be.setMsgCode(MESSAGE_CODE_OK.getCode());
        }
        return be;
    }

}
