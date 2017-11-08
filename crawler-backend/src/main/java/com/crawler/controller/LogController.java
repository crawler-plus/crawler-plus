package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.constant.Const;
import com.crawler.constant.PermissionsConst;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysLog;
import com.crawler.domain.TokenEntity;
import com.crawler.service.api.LogService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Log Controller
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 系统log查询
     */
    @ApiOperation(value="系统log查询", notes="系统log查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysLog", value = "系统Log Entity", required = true, dataType = "SysLog"),
            @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    })
    @GetMapping("/queryAll")
    @RequirePermissions(value = PermissionsConst.LOG_MGMT)
    public BaseEntity queryAll(SysLog sysLog, TokenEntity t) {
        int logCount = logService.getLogCount(sysLog);
        // 分页查询
		PageHelper.startPage(sysLog.getPage(),sysLog.getLimit());
        List<SysLog> sysLogs = logService.listAll(sysLog);
        BaseEntity be = new BaseEntity();
        be.setTotal(logCount);
        be.setRows(sysLogs);
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        return be;
    }

    /**
     * 系统log删除
     */
    @ApiOperation(value="系统log删除", notes="系统log删除")
    @ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
    @DeleteMapping(path = "/delete")
    @RequirePermissions(value = PermissionsConst.LOG_MGMT)
    public BaseEntity delete(TokenEntity t) {
        logService.delete();
        BaseEntity be = new BaseEntity();
        be.setMsgCode(Const.MESSAGE_CODE_OK);
        be.setContent("删除系统log成功");
        return be;
    }

}
