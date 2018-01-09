package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.annotation.RequireToken;
import com.crawler.components.CrawlerProperties;
import com.crawler.components.RequestHolderConfiguration;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysLog;
import com.crawler.domain.SysRole;
import com.crawler.domain.SysUser;
import com.crawler.service.RPCApi;
import com.crawler.service.api.LogService;
import com.crawler.service.api.RoleService;
import com.crawler.service.api.UserService;
import com.crawler.service.api.UserTokenService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
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
    private UserTokenService userTokenService;

    @Autowired
    private CrawlerProperties crawlerProperties;

    @Autowired
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired
    private RPCApi rpcApi;

    /**
     * 用户登录
     */
    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
    @PostMapping("/login")
    public BaseEntity login(@Validated({SysUser.Second.class}) SysUser sysUser, BaseEntity be) {
        boolean useCaptcha = crawlerProperties.isUseCaptcha();
        // 验证码是否通过
        boolean captchaAccess;
        // 如果配置了需要验证码登录
        if(useCaptcha) {
            String captcha = sysUser.getCaptcha();
            // 处理验证码前台传过来是空的情况
            if(StringUtils.isEmpty(captcha)) {
                captcha = "";
            }
            // 如果验证码正确
            captchaAccess = rpcApi.checkCaptchaExists("captchaSet", captcha);
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
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
    @GetMapping("/queryAll")
    @RequirePermissions(value = USER_MGMT)
    @RequireToken
    public BaseEntity queryAll(SysUser sysUser, BaseEntity be) {
        int userCount = userService.getUsersListCount(sysUser);
        // 分页查询
		PageHelper.startPage(sysUser.getPage(),sysUser.getLimit());
        List<SysUser> sysUsers = userService.listAll(sysUser);
        be.setTotal(userCount);
        be.setRows(sysUsers);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 用户删除
     */
    @ApiOperation(value="删除用户", notes="删除用户")
    @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int")
    @DeleteMapping(path = "/delete/{id}")
    @RequirePermissions(value = USER_MGMT)
    @RequireToken
    public BaseEntity delete(@PathVariable("id") int userId, BaseEntity be) {
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
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int")
    @GetMapping(path = "/queryUser/{id}")
    @RequirePermissions(value = {USER_MGMT, SELF_INFO_UPDATE})
    @RequireToken
    public BaseEntity queryUser(@PathVariable("id") int userId, BaseEntity be) {
        SysUser sysUserByUserId = userService.getSysUserByUserId(userId);
        be.setContent(sysUserByUserId);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 查询单个用户的角色
     */
    @ApiOperation(value="查询单个用户的角色", notes="查询单个用户的角色")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int")
    @GetMapping(path = "/getRoleByUserId/{id}")
    @RequirePermissions(value = USER_MGMT)
    @RequireToken
    public BaseEntity getRoleByUserId(@PathVariable("id") int userId, BaseEntity be) {
        List<SysRole> roleByUserId = roleService.getRoleByUserId(userId);
        be.setContent(roleByUserId);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 添加系统用户
     */
    @ApiOperation(value="添加系统用户", notes="添加系统用户")
    @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", dataType = "SysUser")
    @PostMapping(path = "/addUser")
    @RequirePermissions(value = USER_MGMT)
    @RequireToken
    public BaseEntity addUser(@Validated({SysUser.First.class}) SysUser sysUser, BaseEntity be) {
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
    @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", dataType = "SysUser")
    @PutMapping(path = "/updateUser")
    @RequirePermissions(value = {USER_MGMT, SELF_INFO_UPDATE})
    @RequireToken
    public BaseEntity updateUser(@Validated({SysUser.Third.class})SysUser sysUser, BaseEntity be) {
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
    @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int")
    @GetMapping(path = "/logout/{id}")
    @RequireToken
    public BaseEntity logout(@PathVariable("id") int userId, BaseEntity be) {
        SysUser sysUserByUserId = userService.getSysUserByUserId(userId);
        // 往系统log表中添加一条记录
        SysLog sysLog = new SysLog();
        sysLog.setLoginAccount(sysUserByUserId.getLoginAccount());
        sysLog.setTypeId(2);
        // 获取ip地址
        String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
        sysLog.setIp(ip);
        logService.logAdd(sysLog);
        // 将用户token表中的数据删除
        userTokenService.deleteByToken(be.getToken());
        be.setContent("用户退出");
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 判断用户是否存在
     */
    @ApiOperation(value="判断用户是否存在", notes="判断用户是否存在")
    @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int")
    @GetMapping(path = "/checkUserExists/{id}")
    @RequireToken
    public BaseEntity checkUserExists(@PathVariable("id") int userId, BaseEntity be) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        int userCount = userService.checkUserExists(sysUser);
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
