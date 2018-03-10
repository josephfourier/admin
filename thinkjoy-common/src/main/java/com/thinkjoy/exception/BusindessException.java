package com.thinkjoy.exception;

/**
 * Created by wangcheng on 17/8/25.
 * 参数异常
 */
public class BusindessException extends RuntimeException {

    private String name;

    public BusindessException(String message) {
        super(message);
        this.name = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
