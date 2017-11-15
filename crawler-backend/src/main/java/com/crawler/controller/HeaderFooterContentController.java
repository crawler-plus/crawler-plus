package com.crawler.controller;

import com.crawler.annotation.RequirePermissions;
import com.crawler.domain.BaseEntity;
import com.crawler.domain.SysHeaderFooterContent;
import com.crawler.domain.TokenEntity;
import com.crawler.service.api.HeaderFooterContentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.crawler.constant.PermissionsConst.HEADER_FOOTER_CONTENT;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_ERROR;
import static com.crawler.constant.ResponseCodeConst.MESSAGE_CODE_OK;

/**
 * 页眉页脚管理Controller
 */

@RestController
@RequestMapping("/headerFooterContent")
public class HeaderFooterContentController {

	@Autowired
	private HeaderFooterContentService headerFooterContentService;

	/**
	 * 获得页眉页脚内容
	 */
	@ApiOperation(value="获得页眉页脚内容", notes="获得页眉页脚内容")
	@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	@GetMapping("/get")
	@RequirePermissions(value = HEADER_FOOTER_CONTENT)
	public BaseEntity getTemplateConfig(TokenEntity t) {
		BaseEntity be = new BaseEntity();
		SysHeaderFooterContent headerFooterContent = headerFooterContentService.getHeaderFooterContent();
		be.setContent(headerFooterContent);
		be.setMsgCode(MESSAGE_CODE_OK.getCode());
		return be;
	}

	/**
	 * 修改页眉页脚内容
	 */
	@ApiOperation(value="修改页眉页脚内容", notes="修改页眉页脚内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysHeaderFooterContent", value = "页眉页脚entity", required = true, dataType = "SysHeaderFooterContent"),
			@ApiImplicitParam(name = "t", value = "Token Entity", required = true, dataType = "TokenEntity")
	})
	@PutMapping("/edit")
	@RequirePermissions(value = HEADER_FOOTER_CONTENT)
	public BaseEntity editTemplateConfig(SysHeaderFooterContent sysHeaderFooterContent, TokenEntity t) {
		BaseEntity be = new BaseEntity();
		// 得到页眉页脚信息
		SysHeaderFooterContent sysFhc = headerFooterContentService.getHeaderFooterContent();
		// 得到最新版本信息
		int versionId = sysFhc.getVersion();
		// 如果两次的version不相等
		if(versionId != sysHeaderFooterContent.getVersion()) {
			be.setMsgCode(MESSAGE_CODE_ERROR.getCode());
			be.setContent("该页眉页脚内容已被其他人修改，请返回重新修改！");
		}else {
			headerFooterContentService.editHeaderFooterContent(sysHeaderFooterContent);
			be.setMsgCode(MESSAGE_CODE_OK.getCode());
			be.setContent("修改页眉页脚内容成功");
		}
		return be;
	}
}
