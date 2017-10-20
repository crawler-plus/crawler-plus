package com.crawler.domain;

/**
 * 传递的参数entity
 */
public class TransferEntity {

    private String url; // 目标url

    private String pattern; // 正则表达式

    private boolean excludeLink; // 是否需要根据a标签中的某一段文本过滤a标签

    private String excludeLinkStr; // 带过滤的a标签中href的字符串

    private boolean onlyText; // 是否只查文本

    private String proxyIp; // 代理ip

    private int proxyPort; // 代理端口

    private String httpMethod; // HTTP请求方法（GET/POST）

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean isExcludeLink() {
        return excludeLink;
    }

    public void setExcludeLink(boolean excludeLink) {
        this.excludeLink = excludeLink;
    }

    public String getExcludeLinkStr() {
        return excludeLinkStr;
    }

    public void setExcludeLinkStr(String excludeLinkStr) {
        this.excludeLinkStr = excludeLinkStr;
    }

    public boolean isOnlyText() {
        return onlyText;
    }

    public void setOnlyText(boolean onlyText) {
        this.onlyText = onlyText;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**\
     * 默认构造器
     */
    public TransferEntity() {
        this.excludeLink = false;
        this.onlyText = false;
        this.httpMethod = "GET";
    }
}
