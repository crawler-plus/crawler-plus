package com.crawler.domain;

/**
 * 文章配置entity
 */
public class TemplateConfig {

    private String id; // 文章配置id

    private String url; // 文章配置url

    private String firstLevelPattern; // url指定区域pattern

    private String titlePattern; // 子页面标题pattern

    private String timePattern; // 子页面发布时间pattern

    private String contentPattern; // 子页面内容pattern

    private int version; // 版本号（乐观锁）

    private String crawlerPrefix; // 带爬网站的前缀

    private String siteName; // 网站名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstLevelPattern() {
        return firstLevelPattern;
    }

    public void setFirstLevelPattern(String firstLevelPattern) {
        this.firstLevelPattern = firstLevelPattern;
    }

    public String getTitlePattern() {
        return titlePattern;
    }

    public void setTitlePattern(String titlePattern) {
        this.titlePattern = titlePattern;
    }

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    public String getContentPattern() {
        return contentPattern;
    }

    public void setContentPattern(String contentPattern) {
        this.contentPattern = contentPattern;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCrawlerPrefix() {
        return crawlerPrefix;
    }

    public void setCrawlerPrefix(String crawlerPrefix) {
        this.crawlerPrefix = crawlerPrefix;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
