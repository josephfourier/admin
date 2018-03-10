package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterDepartment implements Serializable {
    /**
     * 部门ID
     *
     * @mbg.generated
     */
    private Integer departmentId;

    /**
     * 部门ID
     *
     * @mbg.generated
     */
    private Integer parentId;

    /**
     * 部门编码
     *
     * @mbg.generated
     */
    private String departmentCode;

    /**
     * 部门名称
     *
     * @mbg.generated
     */
    private String departmentName;

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
     *  状态：    0.锁定
                 1.正常(默认)
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 学校编码,学校数据隔离
     *
     * @mbg.generated
     */
    private String schoolCode;
    /**
     * 部门类型:1-院系，2-行政，等等
     *
     * @mbg.generated
     */
    private String departmentType;

    /**
     * 部门级别：扩展字段
     *
     * @mbg.generated
     */
    private String departmentLevel;


    private static final long serialVersionUID = 1L;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getDepartmentType() {return departmentType;}

    public void setDepartmentType(String departmentType) {this.departmentType = departmentType;}

    public String getDepartmentLevel() {return departmentLevel;}

    public void setDepartmentLevel(String departmentLevel) {this.departmentLevel = departmentLevel;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", departmentId=").append(departmentId);
        sb.append(", parentId=").append(parentId);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", isParent=").append(isParent);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", schoolCode=").append(schoolCode);
        sb.append(", departmentType=").append(departmentType);
        sb.append(", departmentLevel=").append(departmentLevel);
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
        UcenterDepartment other = (UcenterDepartment) that;
        return (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getDepartmentCode() == null ? other.getDepartmentCode() == null : this.getDepartmentCode().equals(other.getDepartmentCode()))
            && (this.getDepartmentName() == null ? other.getDepartmentName() == null : this.getDepartmentName().equals(other.getDepartmentName()))
            && (this.getIsParent() == null ? other.getIsParent() == null : this.getIsParent().equals(other.getIsParent()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSchoolCode() == null ? other.getSchoolCode() == null : this.getSchoolCode().equals(other.getSchoolCode()))
            && (this.getDepartmentType() == null ? other.getDepartmentType() == null : this.getDepartmentType().equals(other.getDepartmentType()))
            && (this.getDepartmentLevel() == null ? other.getDepartmentLevel() == null : this.getDepartmentLevel().equals(other.getDepartmentLevel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getDepartmentCode() == null) ? 0 : getDepartmentCode().hashCode());
        result = prime * result + ((getDepartmentName() == null) ? 0 : getDepartmentName().hashCode());
        result = prime * result + ((getIsParent() == null) ? 0 : getIsParent().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        result = prime * result + ((getDepartmentType() == null) ? 0 : getDepartmentType().hashCode());
        result = prime * result + ((getDepartmentLevel() == null) ? 0 : getDepartmentLevel().hashCode());
        return result;
    }
}