package com.thinkjoy.ams.dao.model;

import java.io.Serializable;

public class AmsApprole implements Serializable {
    /**
     * 应用角色id
     *
     * @mbg.generated
     */
    private Integer approleId;

    /**
     * 应用角色名称
     *
     * @mbg.generated
     */
    private String approleName;

    /**
     * 学校/机构名称
     *
     * @mbg.generated
     */
    private String relationName;

    /**
     * 学校/机构编码
     *
     * @mbg.generated
     */
    private String relationCode;

    /**
     * 用户类型
     *
     * @mbg.generated
     */
    private Integer usertypeId;

    /**
     * 创建人
     *
     * @mbg.generated
     */
    private String creator;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Long ctime;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     *   状态:   0.锁定
                 1.正常(默认)
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Long orders;

    /**
     * 权限个性化配置
     *
     * @mbg.generated
     */
    private String perPersonalization;

    /**
     * 应用id
     *
     * @mbg.generated
     */
    private Integer appId;

    /**
     * 应用名称
     *
     * @mbg.generated
     */
    private String appName;

    private static final long serialVersionUID = 1L;

    public Integer getApproleId() {
        return approleId;
    }

    public void setApproleId(Integer approleId) {
        this.approleId = approleId;
    }

    public String getApproleName() {
        return approleName;
    }

    public void setApproleName(String approleName) {
        this.approleName = approleName;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public Integer getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(Integer usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public String getPerPersonalization() {
        return perPersonalization;
    }

    public void setPerPersonalization(String perPersonalization) {
        this.perPersonalization = perPersonalization;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", approleId=").append(approleId);
        sb.append(", approleName=").append(approleName);
        sb.append(", relationName=").append(relationName);
        sb.append(", relationCode=").append(relationCode);
        sb.append(", usertypeId=").append(usertypeId);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", orders=").append(orders);
        sb.append(", perPersonalization=").append(perPersonalization);
        sb.append(", appId=").append(appId);
        sb.append(", appName=").append(appName);
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
        AmsApprole other = (AmsApprole) that;
        return (this.getApproleId() == null ? other.getApproleId() == null : this.getApproleId().equals(other.getApproleId()))
            && (this.getApproleName() == null ? other.getApproleName() == null : this.getApproleName().equals(other.getApproleName()))
            && (this.getRelationName() == null ? other.getRelationName() == null : this.getRelationName().equals(other.getRelationName()))
            && (this.getRelationCode() == null ? other.getRelationCode() == null : this.getRelationCode().equals(other.getRelationCode()))
            && (this.getUsertypeId() == null ? other.getUsertypeId() == null : this.getUsertypeId().equals(other.getUsertypeId()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOrders() == null ? other.getOrders() == null : this.getOrders().equals(other.getOrders()))
            && (this.getPerPersonalization() == null ? other.getPerPersonalization() == null : this.getPerPersonalization().equals(other.getPerPersonalization()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getApproleId() == null) ? 0 : getApproleId().hashCode());
        result = prime * result + ((getApproleName() == null) ? 0 : getApproleName().hashCode());
        result = prime * result + ((getRelationName() == null) ? 0 : getRelationName().hashCode());
        result = prime * result + ((getRelationCode() == null) ? 0 : getRelationCode().hashCode());
        result = prime * result + ((getUsertypeId() == null) ? 0 : getUsertypeId().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOrders() == null) ? 0 : getOrders().hashCode());
        result = prime * result + ((getPerPersonalization() == null) ? 0 : getPerPersonalization().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        return result;
    }
}