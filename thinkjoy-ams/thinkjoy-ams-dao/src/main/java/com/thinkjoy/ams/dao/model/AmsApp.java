package com.thinkjoy.ams.dao.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class AmsApp implements Serializable {
    private Integer appId;

    /**
     * 自动生成
     *
     * @mbg.generated
     */
    @Length(min = 1, max = 32, message = "应用编码长度必须在{min}-{max}之间")
    @NotBlank(message = "应用编码不能为空!")
    private String appCode;

    @Length(min = 1, max = 64, message = "应用名称长度必须在{min}-{max}之间")
    @NotBlank(message = "应用名称不能为空!")
    private String appName;

    /**
     * 可以确认是自己开发的系统，还是对接的其他系统
     * 1.内部
     * 2.外部
     *
     * @mbg.generated
     */
    private String accessType;

    private String icon;

    /**
     * 1.PC (默认PC)
     * 2.APP
     * ...
     *
     * @mbg.generated
     */
    private String deviceType;

    private String appClass;

    @NotBlank(message = "重定向链接不能为空!")
    private String redirectUri;

    /**
     * 用户类型,多个通过“,”分隔
     * 1:教师
     * 2:家长
     * 3:学生
     * 4:教育机构
     *
     * @mbg.generated
     */
    private String applicableIdentity;

    private String creator;

    private Long ctime;

    @Length(max = 256, message = "描述不能超过{max}个字符")
    private String description;

    /**
     * 0.锁定
     * 1.正常(默认)
     *
     * @mbg.generated
     */
    private String status;

    private Long orders;

    /**
     * 是否个性化
     *
     * @mbg.generated
     */
    private String isPersonalization;

    private static final long serialVersionUID = 1L;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppClass() {
        return appClass;
    }

    public void setAppClass(String appClass) {
        this.appClass = appClass;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getApplicableIdentity() {
        return applicableIdentity;
    }

    public void setApplicableIdentity(String applicableIdentity) {
        this.applicableIdentity = applicableIdentity;
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

    public String getIsPersonalization() {
        return isPersonalization;
    }

    public void setIsPersonalization(String isPersonalization) {
        this.isPersonalization = isPersonalization;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", appId=").append(appId);
        sb.append(", appCode=").append(appCode);
        sb.append(", appName=").append(appName);
        sb.append(", accessType=").append(accessType);
        sb.append(", icon=").append(icon);
        sb.append(", deviceType=").append(deviceType);
        sb.append(", appClass=").append(appClass);
        sb.append(", redirectUri=").append(redirectUri);
        sb.append(", applicableIdentity=").append(applicableIdentity);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", orders=").append(orders);
        sb.append(", isPersonalization=").append(isPersonalization);
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
        AmsApp other = (AmsApp) that;
        return (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
                && (this.getAppCode() == null ? other.getAppCode() == null : this.getAppCode().equals(other.getAppCode()))
                && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
                && (this.getAccessType() == null ? other.getAccessType() == null : this.getAccessType().equals(other.getAccessType()))
                && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
                && (this.getDeviceType() == null ? other.getDeviceType() == null : this.getDeviceType().equals(other.getDeviceType()))
                && (this.getAppClass() == null ? other.getAppClass() == null : this.getAppClass().equals(other.getAppClass()))
                && (this.getRedirectUri() == null ? other.getRedirectUri() == null : this.getRedirectUri().equals(other.getRedirectUri()))
                && (this.getApplicableIdentity() == null ? other.getApplicableIdentity() == null : this.getApplicableIdentity().equals(other.getApplicableIdentity()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getOrders() == null ? other.getOrders() == null : this.getOrders().equals(other.getOrders()))
                && (this.getIsPersonalization() == null ? other.getIsPersonalization() == null : this.getIsPersonalization().equals(other.getIsPersonalization()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getAppCode() == null) ? 0 : getAppCode().hashCode());
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        result = prime * result + ((getAccessType() == null) ? 0 : getAccessType().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getDeviceType() == null) ? 0 : getDeviceType().hashCode());
        result = prime * result + ((getAppClass() == null) ? 0 : getAppClass().hashCode());
        result = prime * result + ((getRedirectUri() == null) ? 0 : getRedirectUri().hashCode());
        result = prime * result + ((getApplicableIdentity() == null) ? 0 : getApplicableIdentity().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOrders() == null) ? 0 : getOrders().hashCode());
        result = prime * result + ((getIsPersonalization() == null) ? 0 : getIsPersonalization().hashCode());
        return result;
    }

}