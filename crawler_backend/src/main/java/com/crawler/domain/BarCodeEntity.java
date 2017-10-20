package com.crawler.domain;

/**
 * 条形码entity
 */
public class BarCodeEntity {

    private String ean; // 商品条形码

    private String guobie; // 商品国别

    private String faccode; // 厂商代码

    private String place; // 商品产地

    private String supplier; // 厂商名称

    private String fac_name; // 厂商名称

    private float price; // 参考价格

    private String titleSrc; // 商品全称

    public String getEan() {
        return ean == null ? "" : ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getGuobie() {
        return guobie == null ? "" : guobie;
    }

    public void setGuobie(String guobie) {
        this.guobie = guobie;
    }

    public String getFaccode() {
        return faccode == null ? "" : faccode;
    }

    public void setFaccode(String faccode) {
        this.faccode = faccode;
    }

    public String getPlace() {
        return place == null ? "" : place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSupplier() {
        return supplier == null ? "" : supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getFac_name() {
        return fac_name == null ? "" : fac_name;
    }

    public void setFac_name(String fac_name) {
        this.fac_name = fac_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitleSrc() {
        return titleSrc == null ? "" : titleSrc.replace("\\", "");
    }

    public void setTitleSrc(String titleSrc) {
        this.titleSrc = titleSrc;
    }
}
