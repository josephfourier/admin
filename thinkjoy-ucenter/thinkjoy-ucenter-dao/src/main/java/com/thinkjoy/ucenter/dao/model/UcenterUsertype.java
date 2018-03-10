package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterUsertype implements Serializable {
    private Integer usertypeId;

    private String usertypeName;

    private Long orders;

    private static final long serialVersionUID = 1L;

    public Integer getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(Integer usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getUsertypeName() {
        return usertypeName;
    }

    public void setUsertypeName(String usertypeName) {
        this.usertypeName = usertypeName;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", usertypeId=").append(usertypeId);
        sb.append(", usertypeName=").append(usertypeName);
        sb.append(", orders=").append(orders);
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
        UcenterUsertype other = (UcenterUsertype) that;
        return (this.getUsertypeId() == null ? other.getUsertypeId() == null : this.getUsertypeId().equals(other.getUsertypeId()))
            && (this.getUsertypeName() == null ? other.getUsertypeName() == null : this.getUsertypeName().equals(other.getUsertypeName()))
            && (this.getOrders() == null ? other.getOrders() == null : this.getOrders().equals(other.getOrders()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUsertypeId() == null) ? 0 : getUsertypeId().hashCode());
        result = prime * result + ((getUsertypeName() == null) ? 0 : getUsertypeName().hashCode());
        result = prime * result + ((getOrders() == null) ? 0 : getOrders().hashCode());
        return result;
    }
}