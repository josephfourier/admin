package com.thinkjoy.web.common.constant;

/**
 * web系统接口结果常量枚举类
 * Created by shuzheng on 2017/2/18.
 */
public enum WebResultConstant {

    FAILED(0, "failed"),
    SUCCESS(1, "success"),

	OLDPASSWORD(1, "Incorrect original password"),
	DIFFPASSWORD(2, "Two cipher inconsistencies"),
	USUCCESS(3, "Amend the success"),
	Failure(4, "Failure of user login information"),

    INVALID_LENGTH(10001, "Invalid length"),

    EMPTY_USERNAME(10101, "Username cannot be empty"),
    EMPTY_PASSWORD(10102, "Password cannot be empty"),
    INVALID_USERNAME(10103, "Account does not exist"),
    INVALID_PASSWORD(10104, "Password error"),
    INVALID_ACCOUNT(10105, "Invalid account"),
    FAILED_GEN_TOKEN(10106, "Failed generate token"),

    INVALID_USER_IDENTITY(10107, "Invalid user identity params"),

    EMPTY_COLLECTION_APPID(10108, "empty collection appid"),


    EMPTY_TOKEN(10109, "empty token"),
    EMPTY_CLIENTID(10110, "empty clientid"),
    EMPTY_SIG(10111, "empty sig"),

    INVALID_SIG(10112, "invalid sig"),
    INVALID_TOKEN(10113, "invalid token");



    public int code;

    public String message;

    WebResultConstant(int code, String message) {
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
