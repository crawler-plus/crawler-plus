package com.crawler.domain;

/**
 * 树形节点entity
 */
public class TreeNode {

    // 节点id
    private int id;

    // 节点父id
    private int pId;

    // 节点名称
    private String name;

    // 节点是否显示checkbox
    private boolean check = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
