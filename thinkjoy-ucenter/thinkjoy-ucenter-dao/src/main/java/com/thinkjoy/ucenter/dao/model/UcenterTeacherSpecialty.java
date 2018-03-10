package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterTeacherSpecialty implements Serializable {
    /**
     * 教师ID
     *
     * @mbg.generated
     */
    private Integer teacherId;

    /**
     * 专业code
     *
     * @mbg.generated
     */
    private String specialtyCode;

    private static final long serialVersionUID = 1L;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teacherId=").append(teacherId);
        sb.append(", specialtyCode=").append(specialtyCode);
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
        UcenterTeacherSpecialty other = (UcenterTeacherSpecialty) that;
        return (this.getTeacherId() == null ? other.getTeacherId() == null : this.getTeacherId().equals(other.getTeacherId()))
            && (this.getSpecialtyCode() == null ? other.getSpecialtyCode() == null : this.getSpecialtyCode().equals(other.getSpecialtyCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeacherId() == null) ? 0 : getTeacherId().hashCode());
        result = prime * result + ((getSpecialtyCode() == null) ? 0 : getSpecialtyCode().hashCode());
        return result;
    }
}