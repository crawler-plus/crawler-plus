package com.crawler.controller;

import com.crawler.components.CheckToken;
import com.crawler.components.CrawlerProperties;
import com.crawler.components.RedisConfiguration;
import com.crawler.constant.Const;
import com.crawler.domain.*;
import com.crawler.service.api.LogService;
import com.crawler.service.api.MenuService;
import com.crawler.service.api.RoleService;
import com.crawler.service.api.UserService;
import com.crawler.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private LogService logService;

    @Autowired
    private CrawlerProperties crawlerProperties;

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CheckToken checkToken;

    /**
     * 用户登录
     */
    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", required = true, dataType = "SysUser")
    @PostMapping("/login")
    public BaseEntity login(SysUser sysUser) {
        BaseEntity be = new BaseEntity();
        // 判断验证码是否正确
        String captcha = sysUser.getCaptcha();
        // 如果验证码正确
        if(redisConfiguration.setOperations(redisTemplate).isMember("captchaSet", captcha.toLowerCase())) {
            String password = MD5Utils.toMD5String(sysUser.getPassword(), crawlerProperties.getMd5Salt());
            sysUser.setPassword(password);
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
                // 将用户信息最为token记录到redis中
                String userToken = "user" + UUID.randomUUID().toString().replace("-", "");
                redisConfiguration.valueOperations(redisTemplate).set("loginToken_" + String.valueOf(sysUserByloginAccount.getId()) + "_" + userToken, userToken);
                // 设置默认过期时间为30分钟
                redisTemplate.expire("loginToken_" + String.valueOf(sysUserByloginAccount.getId()) + "_" + userToken, 30, TimeUnit.MINUTES);
                // 往系统log表中添加一条记录
                SysLog sysLog = new SysLog();
                sysLog.setLoginAccount(sysUser.getLoginAccount());
                sysLog.setTypeId(1);
                logService.logAdd(sysLog);
                infoMap.put("userInfo", sysUserByloginAccount);
                infoMap.put("menuInfo", sList);
                infoMap.put("token", userToken);
                be.setContent(infoMap);
                be.setMsgCode(Const.MESSAGE_CODE_OK);
            }else {
                be.setMsgCode(Const.MESSAGE_CODE_ERROR);
                be.setContent("用户名或密码错误，请重试");
            }
        }
        else {
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
            be.setContent("验证码错误，请重新输入");
        }
        return be;
    }

    /**
     * 用户查询
     */
    @ApiOperation(value="查询所有用户", notes="查询所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "系统用户entity", required = true, dataType = "SysUser"),
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @GetMapping("/queryAll")
    public BaseEntity queryAll(SysUser sysUser, TokenEntity te) {
        // 验证token
        checkToken.checkToken(te);
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
    @ApiOperation(value="删除用户", notes="删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @DeleteMapping(path = "/delete/{id}")
    public BaseEntity delete(@PathVariable("id") int userId, TokenEntity te) {
        // 验证token
        checkToken.checkToken(te);
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
    @ApiOperation(value="查询单个用户信息", notes="查询单个用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @GetMapping(path = "/queryUser/{id}")
    public BaseEntity queryUser(@PathVariable("id") int userId, TokenEntity te) {
        // 验证token
        checkToken.checkToken(te);
        SysUser sysUserByUserId = userService.getSysUserByUserId(userId);
        BaseEntity be = new BaseEntity();
        be.setContent(sysUserByUserId);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 查询单个用户的角色
     */
    @ApiOperation(value="查询单个用户的角色", notes="查询单个用户的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @GetMapping(path = "/getRoleByUserId/{id}")
    public BaseEntity getRoleByUserId(@PathVariable("id") int userId, TokenEntity te) {
        // 验证token
        checkToken.checkToken(te);
        List<SysRole> roleByUserId = roleService.getRoleByUserId(userId);
        BaseEntity be = new BaseEntity();
        be.setContent(roleByUserId);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 添加系统用户
     */
    @ApiOperation(value="添加系统用户", notes="添加系统用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", required = true, dataType = "SysUser"),
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @PostMapping(path = "/addUser")
    public BaseEntity addUser(SysUser sysUser, TokenEntity te) {
        // 验证token
        checkToken.checkToken(te);
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
    @ApiOperation(value="修改系统用户", notes="修改系统用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "系统用户Entity", required = true, dataType = "SysUser"),
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @PutMapping(path = "/updateUser")
    public BaseEntity updateUser(SysUser sysUser, TokenEntity te) {
        // 验证token
        checkToken.checkToken(te);
        BaseEntity be = new BaseEntity();
        // 得到用户信息
        SysUser sysUserByUserId = userService.getSysUserByUserId(sysUser.getId());
        // 得到最新版本信息
        int versionId = sysUserByUserId.getVersion();
        // 如果两次的version不相等
        if(versionId != sysUser.getVersion()) {
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
            be.setContent("该用户信息已被其他人修改，请返回重新修改！");
        }else {
            userService.updateUser(sysUser);
            be.setMsgCode(Const.MESSAGE_CODE_OK);
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
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
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
        redisTemplate.delete("loginToken_" + String.valueOf(userId) + "_" + te.getToken());
        be.setContent("用户退出");
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 判断用户是否存在
     */
    @ApiOperation(value="判断用户是否存在", notes="判断用户是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "系统用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "te", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @GetMapping(path = "/checkUserExists/{id}")
    public BaseEntity checkUserExists(@PathVariable("id") int userId, TokenEntity te) {
        // 验证token
        checkToken.checkToken(te);
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        int userCount = userService.checkUserExists(sysUser);
        BaseEntity be = new BaseEntity();
        // 如果用户不存在
        if(userCount < 1) {
            be.setMsgCode(Const.MESSAGE_CODE_ERROR);
            be.setContent("该用户已被删除！");
        }else {
            be.setMsgCode(Const.MESSAGE_CODE_OK);
        }
        return be;
    }

}
