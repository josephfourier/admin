package com.thinkjoy.web.server.controller.third.entity;

/**
 * Created by wangcheng on 18/1/30.
 */
public class TokenApply {
    String authType;
    String account;
    String secret;

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "TokenApply{" +
                "authType='" + authType + '\'' +
                ", account='" + account + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
