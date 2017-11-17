package com.crawler.domain;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 页眉页脚Entity
 */
public class SysHeaderFooterContent {

    private int id; // 主键id

    @NotBlank(message = "{sysHeaderFooterContent.headerContent.not.null}")
    private String headerContent; // 页眉内容

    @NotBlank(message = "{sysHeaderFooterContent.footerContent.not.null}")
    private String footerContent; // 页脚内容

    private int version; // 乐观锁版本号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeaderContent() {
        return headerContent;
    }

    public void setHeaderContent(String headerContent) {
        this.headerContent = headerContent;
    }

    public String getFooterContent() {
        return footerContent;
    }

    public void setFooterContent(String footerContent) {
        this.footerContent = footerContent;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
