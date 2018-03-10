package com.thinkjoy.ucenter.dao.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class UcenterSchool implements Serializable {
    /**
     * 学校id
     *
     * @mbg.generated
     */
    private Integer schoolId;

    /**
     * 学校编码
     *
     * @mbg.generated
     */
    private String schoolCode;

    /**
     * 学校名称
     *
     * @mbg.generated
     */
    private String schoolName;

    /**
     * 英文名称
     *
     * @mbg.generated
     */
    private String schoolEnname;

    /**
     * 组织机构编码：关联组织机构表code
     *
     * @mbg.generated
     */
    private String agencyCode;

    /**
     * 行政区划编码：与行政区划表code关联
            省，市，区/县，与组织机构类型对应
     *
     * @mbg.generated
     */
    private String areaCode;

    /**
     * 地址
     *
     * @mbg.generated
     */
    private String address;

    /**
     * 建校日期
     *
     * @mbg.generated
     */
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

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
     * 区域类型：1.省
            2.市
            3.区/县
     *
     * @mbg.generated
     */
    private String areaType;

    /**
     * 所属机构编码集合：多个用“,”隔开
     *
     * @mbg.generated
     */
    private String pagencyCodes;

    /**
     * 所属行政区划编码集合：多个用“,”隔开
     *
     * @mbg.generated
     */
    private String areaCodes;

    /**
     * 区划级别：0，省；1.市级；2，区县级；
     *
     * @mbg.generated
     */
    private String areaLevel;

    /**
     * 学校类型中职、高职
     *
     * @mbg.generated
     */
    private String xxType;

    /**
     * 办学类型:公办 民办
     *
     * @mbg.generated
     */
    private String bxType;

    /**
     * 学校负责人（法人）
     *
     * @mbg.generated
     */
    private String legalPerson;

    /**
     * 学校联系电话
     *
     * @mbg.generated
     */
    private String tel;

    /**
     * 学校官网
     *
     * @mbg.generated
     */
    private String website;

    private static final long serialVersionUID = 1L;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolEnname() {
        return schoolEnname;
    }

    public void setSchoolEnname(String schoolEnname) {
        this.schoolEnname = schoolEnname;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
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

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getXxType() {
        return xxType;
    }

    public void setXxType(String xxType) {
        this.xxType = xxType;
    }

    public String getBxType() {
        return bxType;
    }

    public void setBxType(String bxType) {
        this.bxType = bxType;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", schoolId=").append(schoolId);
        sb.append(", schoolCode=").append(schoolCode);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", schoolEnname=").append(schoolEnname);
        sb.append(", agencyCode=").append(agencyCode);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", address=").append(address);
        sb.append(", birthday=").append(birthday);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", orders=").append(orders);
        sb.append(", areaType=").append(areaType);
        sb.append(", pagencyCodes=").append(pagencyCodes);
        sb.append(", areaCodes=").append(areaCodes);
        sb.append(", areaLevel=").append(areaLevel);
        sb.append(", xxType=").append(xxType);
        sb.append(", bxType=").append(bxType);
        sb.append(", legalPerson=").append(legalPerson);
        sb.append(", tel=").append(tel);
        sb.append(", website=").append(website);
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
        UcenterSchool other = (UcenterSchool) that;
        return (this.getSchoolId() == null ? other.getSchoolId() == null : this.getSchoolId().equals(other.getSchoolId()))
            && (this.getSchoolCode() == null ? other.getSchoolCode() == null : this.getSchoolCode().equals(other.getSchoolCode()))
            && (this.getSchoolName() == null ? other.getSchoolName() == null : this.getSchoolName().equals(other.getSchoolName()))
            && (this.getSchoolEnname() == null ? other.getSchoolEnname() == null : this.getSchoolEnname().equals(other.getSchoolEnname()))
            && (this.getAgencyCode() == null ? other.getAgencyCode() == null : this.getAgencyCode().equals(other.getAgencyCode()))
            && (this.getAreaCode() == null ? other.getAreaCode() == null : this.getAreaCode().equals(other.getAreaCode()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOrders() == null ? other.getOrders() == null : this.getOrders().equals(other.getOrders()))
            && (this.getAreaType() == null ? other.getAreaType() == null : this.getAreaType().equals(other.getAreaType()))
            && (this.getPagencyCodes() == null ? other.getPagencyCodes() == null : this.getPagencyCodes().equals(other.getPagencyCodes()))
            && (this.getAreaCodes() == null ? other.getAreaCodes() == null : this.getAreaCodes().equals(other.getAreaCodes()))
            && (this.getAreaLevel() == null ? other.getAreaLevel() == null : this.getAreaLevel().equals(other.getAreaLevel()))
            && (this.getXxType() == null ? other.getXxType() == null : this.getXxType().equals(other.getXxType()))
            && (this.getBxType() == null ? other.getBxType() == null : this.getBxType().equals(other.getBxType()))
            && (this.getLegalPerson() == null ? other.getLegalPerson() == null : this.getLegalPerson().equals(other.getLegalPerson()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getWebsite() == null ? other.getWebsite() == null : this.getWebsite().equals(other.getWebsite()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSchoolId() == null) ? 0 : getSchoolId().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        result = prime * result + ((getSchoolName() == null) ? 0 : getSchoolName().hashCode());
        result = prime * result + ((getSchoolEnname() == null) ? 0 : getSchoolEnname().hashCode());
        result = prime * result + ((getAgencyCode() == null) ? 0 : getAgencyCode().hashCode());
        result = prime * result + ((getAreaCode() == null) ? 0 : getAreaCode().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOrders() == null) ? 0 : getOrders().hashCode());
        result = prime * result + ((getAreaType() == null) ? 0 : getAreaType().hashCode());
        result = prime * result + ((getPagencyCodes() == null) ? 0 : getPagencyCodes().hashCode());
        result = prime * result + ((getAreaCodes() == null) ? 0 : getAreaCodes().hashCode());
        result = prime * result + ((getAreaLevel() == null) ? 0 : getAreaLevel().hashCode());
        result = prime * result + ((getXxType() == null) ? 0 : getXxType().hashCode());
        result = prime * result + ((getBxType() == null) ? 0 : getBxType().hashCode());
        result = prime * result + ((getLegalPerson() == null) ? 0 : getLegalPerson().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getWebsite() == null) ? 0 : getWebsite().hashCode());
        return result;
    }
}