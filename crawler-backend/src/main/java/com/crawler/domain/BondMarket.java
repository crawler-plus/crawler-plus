package com.crawler.domain;

/**
 * 债券市场entity
 */
public class BondMarket extends BaseEntity {

    private String id; // 主键id

    private String code; // 证券代码

    private String abbre; // 证券简称

    private String title; // 公告标题

    private String category; // 类别

    private String publishDate; // 公告时间

    private String insertTime; // 插入时间

    private String adjunctUrl; // pdf下载url字符串

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAbbre() {
        return abbre;
    }

    public void setAbbre(String abbre) {
        this.abbre = abbre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getAdjunctUrl() {
        return adjunctUrl;
    }

    public void setAdjunctUrl(String adjunctUrl) {
        this.adjunctUrl = adjunctUrl;
    }
}
