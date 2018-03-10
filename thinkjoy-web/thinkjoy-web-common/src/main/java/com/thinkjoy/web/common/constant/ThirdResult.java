package com.thinkjoy.web.common.constant;


/**
 * Created by wangcheng on 18/1/30.
 */
public class ThirdResult{

    // 状态码 0成功，其他为失败
    public String code;

    // 成功为success，其他为失败原因
    public String message;

    // 数据结果集
    public Object data;

    public ThirdResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}