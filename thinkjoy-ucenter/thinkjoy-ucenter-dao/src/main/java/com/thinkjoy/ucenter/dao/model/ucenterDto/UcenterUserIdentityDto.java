package com.thinkjoy.ucenter.dao.model.ucenterDto;

import java.io.Serializable;

/**
 * Created by wangcheng on 17/8/21.
 */
public class UcenterUserIdentityDto implements Serializable {

    private Integer userId;

    private Integer usertypeId;

    private String relationCode;

    /**
     * 0-默认
     * 1-个性化
     *
     * @mbg.generated
     */
    private String personalization;

    /**
     * 应用角色权限个性化
     */
    private String perpersonalization;

    private String username;

    private String usertypeName;

    private String schoolName;

    private String agencyName;

    private String fullname;

    private String phone;

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertypeName() {
        return usertypeName;
    }

    public void setUsertypeName(String usertypeName) {
        this.usertypeName = usertypeName;
    }

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

    public String getPerpersonalization() {
        return perpersonalization;
    }

    public void setPerpersonalization(String perpersonalization) {
        this.perpersonalization = perpersonalization;
    }

}
