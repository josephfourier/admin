package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterSpecialty implements Serializable {
    /**
     * 专业ID
     *
     * @mbg.generated
     */
    private Integer specialtyId;

    /**
     * 校内专业编码,每个学校专业不同，学校会创建自己的专业编码
     *
     * @mbg.generated
     */
    private String specialtyCode;

    /**
     * 专业编码,教育局有标准
     *
     * @mbg.generated
     */
    private String specialtyNo;

    /**
     * 专业名称
     *
     * @mbg.generated
     */
    private String specialtyName;

    /**
     * 方向id
     *
     * @mbg.generated
     */
    private Integer trdrId;

    /**
     * 培养方向名称
     *
     * @mbg.generated
     */
    private String trdrName;

    /**
     * 专业类别（(1)哲学;(2)经济学;(3)法学;(4)教育学;(5)文学;(6)历史学;(7)理学;(8)工学;(9)农学;(10)医学;(11)军事学;(12)管理学）
     *
     * @mbg.generated
     */
    private String specialtyType;

    /**
     * 专业简称
     *
     * @mbg.generated
     */
    private String specialtyJc;

    /**
     * 专业英文名称
     *
     * @mbg.generated
     */
    private String specialtyEnname;

    /**
     * 所属院系编码
     *
     * @mbg.generated
     */
    private String facultyCode;

    /**
     * 所属院系名称
     *
     * @mbg.generated
     */
    private String facultyName;

    /**
     * 学制（五年，三年制专科）
     *
     * @mbg.generated
     */
    private String schoolSystem;

    /**
     * 最高学历（专科，本科）
     *
     * @mbg.generated
     */
    private String highestEducation;

    /**
     * 专业性质
     *
     * @mbg.generated
     */
    private String specialtyNature;

    /**
     * 招生对象
     *
     * @mbg.generated
     */
    private String enrollmentTarget;

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

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public String getSpecialtyNo() {
        return specialtyNo;
    }

    public void setSpecialtyNo(String specialtyNo) {
        this.specialtyNo = specialtyNo;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Integer getTrdrId() {
        return trdrId;
    }

    public void setTrdrId(Integer trdrId) {
        this.trdrId = trdrId;
    }

    public String getTrdrName() {
        return trdrName;
    }

    public void setTrdrName(String trdrName) {
        this.trdrName = trdrName;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getSpecialtyJc() {
        return specialtyJc;
    }

    public void setSpecialtyJc(String specialtyJc) {
        this.specialtyJc = specialtyJc;
    }

    public String getSpecialtyEnname() {
        return specialtyEnname;
    }

    public void setSpecialtyEnname(String specialtyEnname) {
        this.specialtyEnname = specialtyEnname;
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

    public String getSchoolSystem() {
        return schoolSystem;
    }

    public void setSchoolSystem(String schoolSystem) {
        this.schoolSystem = schoolSystem;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getSpecialtyNature() {
        return specialtyNature;
    }

    public void setSpecialtyNature(String specialtyNature) {
        this.specialtyNature = specialtyNature;
    }

    public String getEnrollmentTarget() {
        return enrollmentTarget;
    }

    public void setEnrollmentTarget(String enrollmentTarget) {
        this.enrollmentTarget = enrollmentTarget;
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
        sb.append(", specialtyId=").append(specialtyId);
        sb.append(", specialtyCode=").append(specialtyCode);
        sb.append(", specialtyNo=").append(specialtyNo);
        sb.append(", specialtyName=").append(specialtyName);
        sb.append(", trdrId=").append(trdrId);
        sb.append(", trdrName=").append(trdrName);
        sb.append(", specialtyType=").append(specialtyType);
        sb.append(", specialtyJc=").append(specialtyJc);
        sb.append(", specialtyEnname=").append(specialtyEnname);
        sb.append(", facultyCode=").append(facultyCode);
        sb.append(", facultyName=").append(facultyName);
        sb.append(", schoolSystem=").append(schoolSystem);
        sb.append(", highestEducation=").append(highestEducation);
        sb.append(", specialtyNature=").append(specialtyNature);
        sb.append(", enrollmentTarget=").append(enrollmentTarget);
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
        UcenterSpecialty other = (UcenterSpecialty) that;
        return (this.getSpecialtyId() == null ? other.getSpecialtyId() == null : this.getSpecialtyId().equals(other.getSpecialtyId()))
            && (this.getSpecialtyCode() == null ? other.getSpecialtyCode() == null : this.getSpecialtyCode().equals(other.getSpecialtyCode()))
            && (this.getSpecialtyNo() == null ? other.getSpecialtyNo() == null : this.getSpecialtyNo().equals(other.getSpecialtyNo()))
            && (this.getSpecialtyName() == null ? other.getSpecialtyName() == null : this.getSpecialtyName().equals(other.getSpecialtyName()))
            && (this.getTrdrId() == null ? other.getTrdrId() == null : this.getTrdrId().equals(other.getTrdrId()))
            && (this.getTrdrName() == null ? other.getTrdrName() == null : this.getTrdrName().equals(other.getTrdrName()))
            && (this.getSpecialtyType() == null ? other.getSpecialtyType() == null : this.getSpecialtyType().equals(other.getSpecialtyType()))
            && (this.getSpecialtyJc() == null ? other.getSpecialtyJc() == null : this.getSpecialtyJc().equals(other.getSpecialtyJc()))
            && (this.getSpecialtyEnname() == null ? other.getSpecialtyEnname() == null : this.getSpecialtyEnname().equals(other.getSpecialtyEnname()))
            && (this.getFacultyCode() == null ? other.getFacultyCode() == null : this.getFacultyCode().equals(other.getFacultyCode()))
            && (this.getFacultyName() == null ? other.getFacultyName() == null : this.getFacultyName().equals(other.getFacultyName()))
            && (this.getSchoolSystem() == null ? other.getSchoolSystem() == null : this.getSchoolSystem().equals(other.getSchoolSystem()))
            && (this.getHighestEducation() == null ? other.getHighestEducation() == null : this.getHighestEducation().equals(other.getHighestEducation()))
            && (this.getSpecialtyNature() == null ? other.getSpecialtyNature() == null : this.getSpecialtyNature().equals(other.getSpecialtyNature()))
            && (this.getEnrollmentTarget() == null ? other.getEnrollmentTarget() == null : this.getEnrollmentTarget().equals(other.getEnrollmentTarget()))
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
        result = prime * result + ((getSpecialtyId() == null) ? 0 : getSpecialtyId().hashCode());
        result = prime * result + ((getSpecialtyCode() == null) ? 0 : getSpecialtyCode().hashCode());
        result = prime * result + ((getSpecialtyNo() == null) ? 0 : getSpecialtyNo().hashCode());
        result = prime * result + ((getSpecialtyName() == null) ? 0 : getSpecialtyName().hashCode());
        result = prime * result + ((getTrdrId() == null) ? 0 : getTrdrId().hashCode());
        result = prime * result + ((getTrdrName() == null) ? 0 : getTrdrName().hashCode());
        result = prime * result + ((getSpecialtyType() == null) ? 0 : getSpecialtyType().hashCode());
        result = prime * result + ((getSpecialtyJc() == null) ? 0 : getSpecialtyJc().hashCode());
        result = prime * result + ((getSpecialtyEnname() == null) ? 0 : getSpecialtyEnname().hashCode());
        result = prime * result + ((getFacultyCode() == null) ? 0 : getFacultyCode().hashCode());
        result = prime * result + ((getFacultyName() == null) ? 0 : getFacultyName().hashCode());
        result = prime * result + ((getSchoolSystem() == null) ? 0 : getSchoolSystem().hashCode());
        result = prime * result + ((getHighestEducation() == null) ? 0 : getHighestEducation().hashCode());
        result = prime * result + ((getSpecialtyNature() == null) ? 0 : getSpecialtyNature().hashCode());
        result = prime * result + ((getEnrollmentTarget() == null) ? 0 : getEnrollmentTarget().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        return result;
    }
}