package com.thinkjoy.web.dao.model;

import java.io.Serializable;

public class WebUserAppcollections implements Serializable {
    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 用户类型
     *
     * @mbg.generated
     */
    private Integer usertypeId;

    /**
     * 学校/机构编码
     *
     * @mbg.generated
     */
    private String relationCode;

    /**
     * 应用id
     *
     * @mbg.generated
     */
    private Integer appId;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(Integer usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", usertypeId=").append(usertypeId);
        sb.append(", relationCode=").append(relationCode);
        sb.append(", appId=").append(appId);
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
        WebUserAppcollections other = (WebUserAppcollections) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUsertypeId() == null ? other.getUsertypeId() == null : this.getUsertypeId().equals(other.getUsertypeId()))
            && (this.getRelationCode() == null ? other.getRelationCode() == null : this.getRelationCode().equals(other.getRelationCode()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUsertypeId() == null) ? 0 : getUsertypeId().hashCode());
        result = prime * result + ((getRelationCode() == null) ? 0 : getRelationCode().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        return result;
    }
}