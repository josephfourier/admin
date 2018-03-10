package com.thinkjoy.enrollment.dao.model;

import java.io.Serializable;
import java.util.Date;

public class EnrollSpecialtychangeLog implements Serializable {
    /**
     * 日志id
     *
     * @mbg.generated
     */
    private Integer changeId;

    /**
     * 学生ID
     *
     * @mbg.generated
     */
    private Integer studentId;

    /**
     * 招生年度
     *
     * @mbg.generated
     */
    private Integer batchYear;

    /**
     * 招生批次ID
     *
     * @mbg.generated
     */
    private Integer batchId;

    /**
     * 招生批次名称
     *
     * @mbg.generated
     */
    private String batchName;

    /**
     * 学生姓名
     *
     * @mbg.generated
     */
    private String studentName;

    /**
     * 性别
     *
     * @mbg.generated
     */
    private String sex;

    /**
     * 身份证号
     *
     * @mbg.generated
     */
    private String idcard;

    /**
     * 变更前专业
     *
     * @mbg.generated
     */
    private String beforeSpecialty;

    /**
     * 变更后专业
     *
     * @mbg.generated
     */
    private String afterSpecialty;

    /**
     * 修改人
     *
     * @mbg.generated
     */
    private String modifier;

    /**
     * 修改时间
     *
     * @mbg.generated
     */
    private Date mtime;

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
     *  状态：    0.锁定1.正常(默认)
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

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(Integer batchYear) {
        this.batchYear = batchYear;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBeforeSpecialty() {
        return beforeSpecialty;
    }

    public void setBeforeSpecialty(String beforeSpecialty) {
        this.beforeSpecialty = beforeSpecialty;
    }

    public String getAfterSpecialty() {
        return afterSpecialty;
    }

    public void setAfterSpecialty(String afterSpecialty) {
        this.afterSpecialty = afterSpecialty;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
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
        sb.append(", changeId=").append(changeId);
        sb.append(", studentId=").append(studentId);
        sb.append(", batchYear=").append(batchYear);
        sb.append(", batchId=").append(batchId);
        sb.append(", batchName=").append(batchName);
        sb.append(", studentName=").append(studentName);
        sb.append(", sex=").append(sex);
        sb.append(", idcard=").append(idcard);
        sb.append(", beforeSpecialty=").append(beforeSpecialty);
        sb.append(", afterSpecialty=").append(afterSpecialty);
        sb.append(", modifier=").append(modifier);
        sb.append(", mtime=").append(mtime);
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
        EnrollSpecialtychangeLog other = (EnrollSpecialtychangeLog) that;
        return (this.getChangeId() == null ? other.getChangeId() == null : this.getChangeId().equals(other.getChangeId()))
            && (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getBatchYear() == null ? other.getBatchYear() == null : this.getBatchYear().equals(other.getBatchYear()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
            && (this.getBatchName() == null ? other.getBatchName() == null : this.getBatchName().equals(other.getBatchName()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getBeforeSpecialty() == null ? other.getBeforeSpecialty() == null : this.getBeforeSpecialty().equals(other.getBeforeSpecialty()))
            && (this.getAfterSpecialty() == null ? other.getAfterSpecialty() == null : this.getAfterSpecialty().equals(other.getAfterSpecialty()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getMtime() == null ? other.getMtime() == null : this.getMtime().equals(other.getMtime()))
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
        result = prime * result + ((getChangeId() == null) ? 0 : getChangeId().hashCode());
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getBatchYear() == null) ? 0 : getBatchYear().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
        result = prime * result + ((getBatchName() == null) ? 0 : getBatchName().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getBeforeSpecialty() == null) ? 0 : getBeforeSpecialty().hashCode());
        result = prime * result + ((getAfterSpecialty() == null) ? 0 : getAfterSpecialty().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getMtime() == null) ? 0 : getMtime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        return result;
    }
}