package com.thinkjoy.web.server.controller.third.entity;

/**
 * Created by wangcheng on 18/1/30.
 */
public class LoginAccessTokenResp {
    String resultCode;
    String resultMsg;
    String accessToken;
    String schoolCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    @Override
    public String toString() {
        return "LoginAccessTokenResp{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                '}';
    }
}
