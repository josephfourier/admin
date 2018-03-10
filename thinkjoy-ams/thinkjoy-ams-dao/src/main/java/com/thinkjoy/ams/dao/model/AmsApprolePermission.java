package com.thinkjoy.ams.dao.model;

import java.io.Serializable;

public class AmsApprolePermission implements Serializable {
    /**
     * 权限编号
     *
     * @mbg.generated
     */
    private Integer permissionId;

    /**
     * 应用角色id
     *
     * @mbg.generated
     */
    private Integer approleId;

    private static final long serialVersionUID = 1L;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getApproleId() {
        return approleId;
    }

    public void setApproleId(Integer approleId) {
        this.approleId = approleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", permissionId=").append(permissionId);
        sb.append(", approleId=").append(approleId);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AmsApprolePermission other = (AmsApprolePermission) that;
        return (this.getPermissionId() == null ? other.getPermissionId() == null : this.getPermissionId().equals(other.getPermissionId()))
            && (this.getApproleId() == null ? other.getApproleId() == null : this.getApproleId().equals(other.getApproleId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPermissionId() == null) ? 0 : getPermissionId().hashCode());
        result = prime * result + ((getApproleId() == null) ? 0 : getApproleId().hashCode());
        return result;
    }
}