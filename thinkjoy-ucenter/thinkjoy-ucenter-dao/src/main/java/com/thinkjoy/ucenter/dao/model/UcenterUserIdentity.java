package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterUserIdentity implements Serializable {
    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 用户类型
     *
     * @mbg.generated
     */
    private Integer usertypeId;

    /**
     * 学校/机构编码
     *
     * @mbg.generated
     */
    private String relationCode;

    /**
     * 个性化设置:
            0-默认
            1-个性化
     *
     * @mbg.generated
     */
    private String personalization;

    /**
     * 个性化设置:
     *
     * @mbg.generated
     */
    private String perPersonalization;

    /**
     * 用户类型名称
     *
     * @mbg.generated
     */
    private String usertype;

    /**
     * 学校/机构名称
     *
     * @mbg.generated
     */
    private String relationName;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(Integer usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getPersonalization() {
        return personalization;
    }

    public void setPersonalization(String personalization) {
        this.personalization = personalization;
    }

    public String getPerPersonalization() {
        return perPersonalization;
    }

    public void setPerPersonalization(String perPersonalization) {
        this.perPersonalization = perPersonalization;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", usertypeId=").append(usertypeId);
        sb.append(", relationCode=").append(relationCode);
        sb.append(", personalization=").append(personalization);
        sb.append(", perPersonalization=").append(perPersonalization);
        sb.append(", usertype=").append(usertype);
        sb.append(", relationName=").append(relationName);
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
        UcenterUserIdentity other = (UcenterUserIdentity) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUsertypeId() == null ? other.getUsertypeId() == null : this.getUsertypeId().equals(other.getUsertypeId()))
            && (this.getRelationCode() == null ? other.getRelationCode() == null : this.getRelationCode().equals(other.getRelationCode()))
            && (this.getPersonalization() == null ? other.getPersonalization() == null : this.getPersonalization().equals(other.getPersonalization()))
            && (this.getPerPersonalization() == null ? other.getPerPersonalization() == null : this.getPerPersonalization().equals(other.getPerPersonalization()))
            && (this.getUsertype() == null ? other.getUsertype() == null : this.getUsertype().equals(other.getUsertype()))
            && (this.getRelationName() == null ? other.getRelationName() == null : this.getRelationName().equals(other.getRelationName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUsertypeId() == null) ? 0 : getUsertypeId().hashCode());
        result = prime * result + ((getRelationCode() == null) ? 0 : getRelationCode().hashCode());
        result = prime * result + ((getPersonalization() == null) ? 0 : getPersonalization().hashCode());
        result = prime * result + ((getPerPersonalization() == null) ? 0 : getPerPersonalization().hashCode());
        result = prime * result + ((getUsertype() == null) ? 0 : getUsertype().hashCode());
        result = prime * result + ((getRelationName() == null) ? 0 : getRelationName().hashCode());
        return result;
    }
}