package com.crawler.domain;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 角色entity
 */
public class SysRole extends BaseEntity {

    // 自增ID
    private int id;

    // 角色名称
    @NotBlank(message = "{sysRole.roleName.not.null}")
    private String roleName;

    // 备注
    private String remark;

    // 是否被用户引用
    private int isRefByUser;

    // 角色菜单字符串，用逗号分割
    private String roleMenuStr;

    // sys_role表中的版本号（乐观锁）
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsRefByUser() {
        return isRefByUser;
    }

    public void setIsRefByUser(int isRefByUser) {
        this.isRefByUser = isRefByUser;
    }

    public String getRoleMenuStr() {
        return roleMenuStr;
    }

    public void setRoleMenuStr(String roleMenuStr) {
        this.roleMenuStr = roleMenuStr;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
