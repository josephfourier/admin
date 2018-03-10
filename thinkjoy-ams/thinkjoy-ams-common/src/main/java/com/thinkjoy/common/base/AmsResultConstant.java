package com.thinkjoy.common.base;

/**
 * Created by wangcheng on 17/7/24.
 */
public enum AmsResultConstant {
    FAILED(0, "failed"),
    SUCCESS(1, "success"),

    FILE_TYPE_ERROR(20001, "File type not supported!"),
    INVALID_LENGTH(20002, "Invalid length"),
    INVALID_PARAMETER(20003, "Invalid parameter"),

    NULL_ERROR(20004, "Not Null!");

    public int code;

    public String message;

    AmsResultConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
