package com.thinkjoy.web.server.controller.third.entity;

/**
 * Created by wangcheng on 18/1/30.
 */
public class LoginAccessTokenReq {
    String schoolKey;
    String phone;

    public String getSchoolKey() {
        return schoolKey;
    }

    public void setSchoolKey(String schoolKey) {
        this.schoolKey = schoolKey;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "LoginAccessTokenReq{" +
                "schoolKey='" + schoolKey + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
