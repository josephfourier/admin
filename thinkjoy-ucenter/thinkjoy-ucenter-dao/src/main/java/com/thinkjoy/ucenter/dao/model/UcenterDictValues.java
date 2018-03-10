package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterDictValues implements Serializable {
    /**
     * 字典值id
     *
     * @mbg.generated
     */
    private Integer valueId;

    /**
     * 字典Code
     *
     * @mbg.generated
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 值key
     *
     * @mbg.generated
     */
    private String valueKey;

    /**
     * 值名称
     *
     * @mbg.generated
     */
    private String valueName;

    /**
     * 值顺序
     *
     * @mbg.generated
     */
    private Integer valuesOrders;

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
     * 顺序
     *
     * @mbg.generated
     */
    private Long orders;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     *  状态：    0.锁定
     *
     * @mbg.generated
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getValuesOrders() {
        return valuesOrders;
    }

    public void setValuesOrders(Integer valuesOrders) {
        this.valuesOrders = valuesOrders;
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

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
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
        sb.append(", valueId=").append(valueId);
        sb.append(", dictCode=").append(dictCode);
        sb.append(", dictName=").append(dictName);
        sb.append(", valueKey=").append(valueKey);
        sb.append(", valueName=").append(valueName);
        sb.append(", valuesOrders=").append(valuesOrders);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", orders=").append(orders);
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
        UcenterDictValues other = (UcenterDictValues) that;
        return (this.getValueId() == null ? other.getValueId() == null : this.getValueId().equals(other.getValueId()))
            && (this.getDictCode() == null ? other.getDictCode() == null : this.getDictCode().equals(other.getDictCode()))
            && (this.getDictName() == null ? other.getDictName() == null : this.getDictName().equals(other.getDictName()))
            && (this.getValueKey() == null ? other.getValueKey() == null : this.getValueKey().equals(other.getValueKey()))
            && (this.getValueName() == null ? other.getValueName() == null : this.getValueName().equals(other.getValueName()))
            && (this.getValuesOrders() == null ? other.getValuesOrders() == null : this.getValuesOrders().equals(other.getValuesOrders()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getOrders() == null ? other.getOrders() == null : this.getOrders().equals(other.getOrders()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getValueId() == null) ? 0 : getValueId().hashCode());
        result = prime * result + ((getDictCode() == null) ? 0 : getDictCode().hashCode());
        result = prime * result + ((getDictName() == null) ? 0 : getDictName().hashCode());
        result = prime * result + ((getValueKey() == null) ? 0 : getValueKey().hashCode());
        result = prime * result + ((getValueName() == null) ? 0 : getValueName().hashCode());
        result = prime * result + ((getValuesOrders() == null) ? 0 : getValuesOrders().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getOrders() == null) ? 0 : getOrders().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}