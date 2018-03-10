package com.thinkjoy.ucenter.rpc.exception;

import com.thinkjoy.exception.BusindessException;

/**
 * Created by wangcheng on 17/12/8.
 */
public class UcenterBusinessException extends BusindessException {
    public UcenterBusinessException(String message) {
        super(message);
    }
}
