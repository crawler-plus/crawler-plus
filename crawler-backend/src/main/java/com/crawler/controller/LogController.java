package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.annotation.RequireToken;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysLog;
import com.crawler.service.api.LogService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.crawler.constant.PermissionsConst.LOG_MGMT;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

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
    @ApiImplicitParam(name = "sysLog", value = "系统Log Entity", dataType = "SysLog")
    @GetMapping("/queryAll")
    @RequirePermissions(value = LOG_MGMT)
    @RequireToken()
    public BaseEntity queryAll(SysLog sysLog, BaseEntity be) {
        int logCount = logService.getLogCount(sysLog);
        // 分页查询
		PageHelper.startPage(sysLog.getPage(),sysLog.getLimit());
        List<SysLog> sysLogs = logService.listAll(sysLog);
        be.setTotal(logCount);
        be.setRows(sysLogs);
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        return be;
    }

    /**
     * 系统log删除
     */
    @ApiOperation(value="系统log删除", notes="系统log删除")
    @DeleteMapping(path = "/delete")
    @RequirePermissions(value = LOG_MGMT)
    @RequireToken()
    public BaseEntity delete(BaseEntity be) {
        logService.delete();
        be.setMsgCode(MESSAGE_CODE_OK.getCode());
        be.setContent("删除系统log成功");
        return be;
    }

}
