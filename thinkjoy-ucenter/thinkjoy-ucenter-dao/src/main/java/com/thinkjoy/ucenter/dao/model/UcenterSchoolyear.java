package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;
import java.util.Date;

public class UcenterSchoolyear implements Serializable {
    /**
     * 学年ID
     *
     * @mbg.generated
     */
    private Integer yearId;

    /**
     * 学年名称
     *
     * @mbg.generated
     */
    private String yearName;

    /**
     * 学年开始时间
     *
     * @mbg.generated
     */
    private Date yearStarttime;

    /**
     * 学年结束时间
     *
     * @mbg.generated
     */
    private Date yearEndtime;

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
     *  状态：    0.锁定
                 1.正常(默认)
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 年份
     *
     * @mbg.generated
     */
    private Integer year;

    /**
     * 学校编码,学校数据隔离
     *
     * @mbg.generated
     */
    private String schoolCode;

    private static final long serialVersionUID = 1L;

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public Date getYearStarttime() {
        return yearStarttime;
    }

    public void setYearStarttime(Date yearStarttime) {
        this.yearStarttime = yearStarttime;
    }

    public Date getYearEndtime() {
        return yearEndtime;
    }

    public void setYearEndtime(Date yearEndtime) {
        this.yearEndtime = yearEndtime;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", yearId=").append(yearId);
        sb.append(", yearName=").append(yearName);
        sb.append(", yearStarttime=").append(yearStarttime);
        sb.append(", yearEndtime=").append(yearEndtime);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", year=").append(year);
        sb.append(", schoolCode=").append(schoolCode);
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
        UcenterSchoolyear other = (UcenterSchoolyear) that;
        return (this.getYearId() == null ? other.getYearId() == null : this.getYearId().equals(other.getYearId()))
            && (this.getYearName() == null ? other.getYearName() == null : this.getYearName().equals(other.getYearName()))
            && (this.getYearStarttime() == null ? other.getYearStarttime() == null : this.getYearStarttime().equals(other.getYearStarttime()))
            && (this.getYearEndtime() == null ? other.getYearEndtime() == null : this.getYearEndtime().equals(other.getYearEndtime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
            && (this.getSchoolCode() == null ? other.getSchoolCode() == null : this.getSchoolCode().equals(other.getSchoolCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getYearId() == null) ? 0 : getYearId().hashCode());
        result = prime * result + ((getYearName() == null) ? 0 : getYearName().hashCode());
        result = prime * result + ((getYearStarttime() == null) ? 0 : getYearStarttime().hashCode());
        result = prime * result + ((getYearEndtime() == null) ? 0 : getYearEndtime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        return result;
    }
}