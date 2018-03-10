package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterDepartmentBus implements Serializable {
    /**
     * 业务关系id
     *
     * @mbg.generated
     */
    private Integer departmentbusId;

    /**
     * 部门ID
     *
     * @mbg.generated
     */
    private Integer departmentId;

    /**
     * 业务id:院系，班级表id
     *
     * @mbg.generated
     */
    private Integer busId;

    private static final long serialVersionUID = 1L;

    public Integer getDepartmentbusId() {
        return departmentbusId;
    }

    public void setDepartmentbusId(Integer departmentbusId) {
        this.departmentbusId = departmentbusId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", departmentbusId=").append(departmentbusId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", busId=").append(busId);
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
        UcenterDepartmentBus other = (UcenterDepartmentBus) that;
        return (this.getDepartmentbusId() == null ? other.getDepartmentbusId() == null : this.getDepartmentbusId().equals(other.getDepartmentbusId()))
            && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
            && (this.getBusId() == null ? other.getBusId() == null : this.getBusId().equals(other.getBusId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDepartmentbusId() == null) ? 0 : getDepartmentbusId().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getBusId() == null) ? 0 : getBusId().hashCode());
        return result;
    }
}