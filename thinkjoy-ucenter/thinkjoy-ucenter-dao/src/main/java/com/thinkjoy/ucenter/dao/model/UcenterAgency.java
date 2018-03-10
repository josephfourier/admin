package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterAgency implements Serializable {
    /**
     * 组织机构id
     *
     * @mbg.generated
     */
    private Integer agencyId;

    /**
     * 组织机构编码
     *
     * @mbg.generated
     */
    private String agencyCode;

    /**
     * 组织机构名称
     *
     * @mbg.generated
     */
    private String agencyName;

    /**
     * 上级机构编码
     *
     * @mbg.generated
     */
    private String pagencyCode;

    /**
     * 简称
     *
     * @mbg.generated
     */
    private String shortName;

    /**
     * 主页
     *
     * @mbg.generated
     */
    private String url;

    /**
     * 区域类型：1.省
            2.市
            3.区/县
     *
     * @mbg.generated
     */
    private String areaType;

    /**
     * 行政区划编码：与行政区划表code关联
            省，市，区/县，与组织机构类型对应
     *
     * @mbg.generated
     */
    private String areaCode;

    /**
     * 机构电话
     *
     * @mbg.generated
     */
    private String agencyPhone;

    /**
     * 是否父节点：是true
            否false
     *
     * @mbg.generated
     */
    private String isParent;

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
     * 状态： 0.锁定
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
     * 上级机构编码集合：多个用“,”隔开
     *
     * @mbg.generated
     */
    private String pagencyCodes;

    /**
     * 上级行政区划编码集合：多个用“,”隔开
     *
     * @mbg.generated
     */
    private String areaCodes;

    private static final long serialVersionUID = 1L;

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getPagencyCode() {
        return pagencyCode;
    }

    public void setPagencyCode(String pagencyCode) {
        this.pagencyCode = pagencyCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
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

    public String getPagencyCodes() {
        return pagencyCodes;
    }

    public void setPagencyCodes(String pagencyCodes) {
        this.pagencyCodes = pagencyCodes;
    }

    public String getAreaCodes() {
        return areaCodes;
    }

    public void setAreaCodes(String areaCodes) {
        this.areaCodes = areaCodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", agencyId=").append(agencyId);
        sb.append(", agencyCode=").append(agencyCode);
        sb.append(", agencyName=").append(agencyName);
        sb.append(", pagencyCode=").append(pagencyCode);
        sb.append(", shortName=").append(shortName);
        sb.append(", url=").append(url);
        sb.append(", areaType=").append(areaType);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", agencyPhone=").append(agencyPhone);
        sb.append(", isParent=").append(isParent);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", orders=").append(orders);
        sb.append(", pagencyCodes=").append(pagencyCodes);
        sb.append(", areaCodes=").append(areaCodes);
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
        UcenterAgency other = (UcenterAgency) that;
        return (this.getAgencyId() == null ? other.getAgencyId() == null : this.getAgencyId().equals(other.getAgencyId()))
            && (this.getAgencyCode() == null ? other.getAgencyCode() == null : this.getAgencyCode().equals(other.getAgencyCode()))
            && (this.getAgencyName() == null ? other.getAgencyName() == null : this.getAgencyName().equals(other.getAgencyName()))
            && (this.getPagencyCode() == null ? other.getPagencyCode() == null : this.getPagencyCode().equals(other.getPagencyCode()))
            && (this.getShortName() == null ? other.getShortName() == null : this.getShortName().equals(other.getShortName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getAreaType() == null ? other.getAreaType() == null : this.getAreaType().equals(other.getAreaType()))
            && (this.getAreaCode() == null ? other.getAreaCode() == null : this.getAreaCode().equals(other.getAreaCode()))
            && (this.getAgencyPhone() == null ? other.getAgencyPhone() == null : this.getAgencyPhone().equals(other.getAgencyPhone()))
            && (this.getIsParent() == null ? other.getIsParent() == null : this.getIsParent().equals(other.getIsParent()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOrders() == null ? other.getOrders() == null : this.getOrders().equals(other.getOrders()))
            && (this.getPagencyCodes() == null ? other.getPagencyCodes() == null : this.getPagencyCodes().equals(other.getPagencyCodes()))
            && (this.getAreaCodes() == null ? other.getAreaCodes() == null : this.getAreaCodes().equals(other.getAreaCodes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAgencyId() == null) ? 0 : getAgencyId().hashCode());
        result = prime * result + ((getAgencyCode() == null) ? 0 : getAgencyCode().hashCode());
        result = prime * result + ((getAgencyName() == null) ? 0 : getAgencyName().hashCode());
        result = prime * result + ((getPagencyCode() == null) ? 0 : getPagencyCode().hashCode());
        result = prime * result + ((getShortName() == null) ? 0 : getShortName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getAreaType() == null) ? 0 : getAreaType().hashCode());
        result = prime * result + ((getAreaCode() == null) ? 0 : getAreaCode().hashCode());
        result = prime * result + ((getAgencyPhone() == null) ? 0 : getAgencyPhone().hashCode());
        result = prime * result + ((getIsParent() == null) ? 0 : getIsParent().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOrders() == null) ? 0 : getOrders().hashCode());
        result = prime * result + ((getPagencyCodes() == null) ? 0 : getPagencyCodes().hashCode());
        result = prime * result + ((getAreaCodes() == null) ? 0 : getAreaCodes().hashCode());
        return result;
    }
}