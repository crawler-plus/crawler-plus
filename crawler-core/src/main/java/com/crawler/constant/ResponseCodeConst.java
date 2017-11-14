package com.crawler.constant;

/**
 * 网页响应代码枚举
 */
public enum ResponseCodeConst {
    // 正常响应
    MESSAGE_CODE_OK("200"),
    // 异常响应
    MESSAGE_CODE_ERROR("400"),
    // security invalid响应
    MESSAGE_CODE_SECURITY_INVALID("401");

    // 错误代码
    private String code;

    ResponseCodeConst(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

