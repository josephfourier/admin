package com.thinkjoy.web.common.exception;

/**
 * Created by wangcheng on 17/8/23.
 * 用户身份类型不存在异常
 */
public class NoUserTypeException extends RuntimeException {
    public NoUserTypeException(String message) {
        super(message);
    }
}
