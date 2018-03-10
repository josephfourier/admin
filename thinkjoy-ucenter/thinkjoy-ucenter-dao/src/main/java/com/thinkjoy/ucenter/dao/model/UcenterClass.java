package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;
import java.util.Date;

public class UcenterClass implements Serializable {
    /**
     * 班级ID
     *
     * @mbg.generated
     */
    private Integer classId;

    /**
     * 班级名称
     *
     * @mbg.generated
     */
    private String className;

    /**
     * 班主任id
     *
     * @mbg.generated
     */
    private Integer bzrId;

    /**
     * 班主任名称
     *
     * @mbg.generated
     */
    private String bzrName;

    /**
     * 辅导员id
     *
     * @mbg.generated
     */
    private Integer fdyId;

    /**
     * 辅导员名称
     *
     * @mbg.generated
     */
    private String fdyName;

    /**
     * 所属院系code
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
     * 所属专业code
     *
     * @mbg.generated
     */
    private String specialtyCode;

    /**
     * 专业名称
     *
     * @mbg.generated
     */
    private String specialtyName;

    /**
     * 毕业时间
     *
     * @mbg.generated
     */
    private Date graduationTime;

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
     * 学校编码,,学校数据隔离
     *
     * @mbg.generated
     */
    private String schoolCode;

    private static final long serialVersionUID = 1L;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getBzrId() {
        return bzrId;
    }

    public void setBzrId(Integer bzrId) {
        this.bzrId = bzrId;
    }

    public String getBzrName() {
        return bzrName;
    }

    public void setBzrName(String bzrName) {
        this.bzrName = bzrName;
    }

    public Integer getFdyId() {
        return fdyId;
    }

    public void setFdyId(Integer fdyId) {
        this.fdyId = fdyId;
    }

    public String getFdyName() {
        return fdyName;
    }

    public void setFdyName(String fdyName) {
        this.fdyName = fdyName;
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

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Date getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Date graduationTime) {
        this.graduationTime = graduationTime;
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
        sb.append(", classId=").append(classId);
        sb.append(", className=").append(className);
        sb.append(", bzrId=").append(bzrId);
        sb.append(", bzrName=").append(bzrName);
        sb.append(", fdyId=").append(fdyId);
        sb.append(", fdyName=").append(fdyName);
        sb.append(", facultyCode=").append(facultyCode);
        sb.append(", facultyName=").append(facultyName);
        sb.append(", specialtyCode=").append(specialtyCode);
        sb.append(", specialtyName=").append(specialtyName);
        sb.append(", graduationTime=").append(graduationTime);
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
        UcenterClass other = (UcenterClass) that;
        return (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getBzrId() == null ? other.getBzrId() == null : this.getBzrId().equals(other.getBzrId()))
            && (this.getBzrName() == null ? other.getBzrName() == null : this.getBzrName().equals(other.getBzrName()))
            && (this.getFdyId() == null ? other.getFdyId() == null : this.getFdyId().equals(other.getFdyId()))
            && (this.getFdyName() == null ? other.getFdyName() == null : this.getFdyName().equals(other.getFdyName()))
            && (this.getFacultyCode() == null ? other.getFacultyCode() == null : this.getFacultyCode().equals(other.getFacultyCode()))
            && (this.getFacultyName() == null ? other.getFacultyName() == null : this.getFacultyName().equals(other.getFacultyName()))
            && (this.getSpecialtyCode() == null ? other.getSpecialtyCode() == null : this.getSpecialtyCode().equals(other.getSpecialtyCode()))
            && (this.getSpecialtyName() == null ? other.getSpecialtyName() == null : this.getSpecialtyName().equals(other.getSpecialtyName()))
            && (this.getGraduationTime() == null ? other.getGraduationTime() == null : this.getGraduationTime().equals(other.getGraduationTime()))
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
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getBzrId() == null) ? 0 : getBzrId().hashCode());
        result = prime * result + ((getBzrName() == null) ? 0 : getBzrName().hashCode());
        result = prime * result + ((getFdyId() == null) ? 0 : getFdyId().hashCode());
        result = prime * result + ((getFdyName() == null) ? 0 : getFdyName().hashCode());
        result = prime * result + ((getFacultyCode() == null) ? 0 : getFacultyCode().hashCode());
        result = prime * result + ((getFacultyName() == null) ? 0 : getFacultyName().hashCode());
        result = prime * result + ((getSpecialtyCode() == null) ? 0 : getSpecialtyCode().hashCode());
        result = prime * result + ((getSpecialtyName() == null) ? 0 : getSpecialtyName().hashCode());
        result = prime * result + ((getGraduationTime() == null) ? 0 : getGraduationTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        return result;
    }
}