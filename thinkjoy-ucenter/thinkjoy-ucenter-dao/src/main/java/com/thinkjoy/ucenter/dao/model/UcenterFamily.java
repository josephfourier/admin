package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterFamily implements Serializable {
    /**
     * 家庭成员id
     *
     * @mbg.generated
     */
    private Integer family;

    /**
     * 学生ID
     *
     * @mbg.generated
     */
    private Integer studentId;

    /**
     * 关系
     *
     * @mbg.generated
     */
    private String relation;

    /**
     * 姓名
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 工作单位
     *
     * @mbg.generated
     */
    private String workplace;

    /**
     * 职务
     *
     * @mbg.generated
     */
    private String position;

    /**
     * 联系电话
     *
     * @mbg.generated
     */
    private String relationtel;

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

    private static final long serialVersionUID = 1L;

    public Integer getFamily() {
        return family;
    }

    public void setFamily(Integer family) {
        this.family = family;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRelationtel() {
        return relationtel;
    }

    public void setRelationtel(String relationtel) {
        this.relationtel = relationtel;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", family=").append(family);
        sb.append(", studentId=").append(studentId);
        sb.append(", relation=").append(relation);
        sb.append(", name=").append(name);
        sb.append(", workplace=").append(workplace);
        sb.append(", position=").append(position);
        sb.append(", relationtel=").append(relationtel);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
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
        UcenterFamily other = (UcenterFamily) that;
        return (this.getFamily() == null ? other.getFamily() == null : this.getFamily().equals(other.getFamily()))
            && (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getRelation() == null ? other.getRelation() == null : this.getRelation().equals(other.getRelation()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getWorkplace() == null ? other.getWorkplace() == null : this.getWorkplace().equals(other.getWorkplace()))
            && (this.getPosition() == null ? other.getPosition() == null : this.getPosition().equals(other.getPosition()))
            && (this.getRelationtel() == null ? other.getRelationtel() == null : this.getRelationtel().equals(other.getRelationtel()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFamily() == null) ? 0 : getFamily().hashCode());
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getRelation() == null) ? 0 : getRelation().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getWorkplace() == null) ? 0 : getWorkplace().hashCode());
        result = prime * result + ((getPosition() == null) ? 0 : getPosition().hashCode());
        result = prime * result + ((getRelationtel() == null) ? 0 : getRelationtel().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}