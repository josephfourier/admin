package com.thinkjoy.ucenter.rpc.exception;

import com.thinkjoy.exception.BusindessException;

/**
 * Created by wangcheng on 17/12/8.
 *
 * 自定义异常必须和接口类在同一个jar包中,
 * service层中抛出的自定义异常才可以传递到controller层,
 * 否则dubbo会封装成RuntimeException
 */
public class DuplicatePropertyException extends BusindessException {
    public DuplicatePropertyException(String message) {
        super(message);
    }
}
