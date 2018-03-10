package com.thinkjoy.web.server.controller.third.entity;

/**
 * Created by wangcheng on 18/1/30.
 */
public class Token {
    String expireAt;
    String value;

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "expireAt='" + expireAt + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
