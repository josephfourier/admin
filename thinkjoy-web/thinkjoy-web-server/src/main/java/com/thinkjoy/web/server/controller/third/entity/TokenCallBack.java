package com.thinkjoy.web.server.controller.third.entity;

/**
 * Created by wangcheng on 18/1/30.
 */
public class TokenCallBack {
    private String resultCode;
    private String resultMsg;
    private Token token;

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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenCallBack{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", token=" + token +
                '}';
    }
}
