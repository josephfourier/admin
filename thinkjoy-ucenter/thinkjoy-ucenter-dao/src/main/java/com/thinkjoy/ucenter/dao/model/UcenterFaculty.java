package com.thinkjoy.ucenter.dao.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class UcenterFaculty implements Serializable {
    /**
     * 院系ID
     *
     * @mbg.generated
     */
    private Integer facultyId;

    /**
     * 院系code
     *
     * @mbg.generated
     */
    private String facultyCode;

    /**
     * 院系名称
     *
     * @mbg.generated
     */
    private String facultyName;

    /**
     * 负责人名称
     *
     * @mbg.generated
     */
    private String fzrName;

    /**
     * 联系方式
     *
     * @mbg.generated
     */
    private String tel;

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

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFzrName() {
        return fzrName;
    }

    public void setFzrName(String fzrName) {
        this.fzrName = fzrName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
        sb.append(", facultyId=").append(facultyId);
        sb.append(", facultyCode=").append(facultyCode);
        sb.append(", facultyName=").append(facultyName);
        sb.append(", fzrName=").append(fzrName);
        sb.append(", tel=").append(tel);
        sb.append(", birthday=").append(birthday);
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
        UcenterFaculty other = (UcenterFaculty) that;
        return (this.getFacultyId() == null ? other.getFacultyId() == null : this.getFacultyId().equals(other.getFacultyId()))
            && (this.getFacultyCode() == null ? other.getFacultyCode() == null : this.getFacultyCode().equals(other.getFacultyCode()))
            && (this.getFacultyName() == null ? other.getFacultyName() == null : this.getFacultyName().equals(other.getFacultyName()))
            && (this.getFzrName() == null ? other.getFzrName() == null : this.getFzrName().equals(other.getFzrName()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
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
        result = prime * result + ((getFacultyId() == null) ? 0 : getFacultyId().hashCode());
        result = prime * result + ((getFacultyCode() == null) ? 0 : getFacultyCode().hashCode());
        result = prime * result + ((getFacultyName() == null) ? 0 : getFacultyName().hashCode());
        result = prime * result + ((getFzrName() == null) ? 0 : getFzrName().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        return result;
    }
}