package com.crawler.domain;

/**
 * 文章爬虫entity
 */
public class CrawlerContent extends BaseEntity {

    private int cid; // 文章cid

    private String id; // 文章id

    private String url; // 文章url

    private String title; // 文章标题

    private String time; // 文章发布时间

    private String contentBody; // 文章主体内容

    private String insertTime; // 文章插入时间

    private int userId; // 关联的用户id

    private String siteName; //关联的网站名称

    private String refId; // 关联的模板id

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
