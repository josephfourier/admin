package com.thinkjoy.ucenter.common.constant;

/**
 * Created by xufei on 2017/7/26.
 */
public enum UcenterResultConstant {
    FAILED(0, "failed"),
    SUCCESS(1, "success"),

    FILE_TYPE_ERROR(20001, "File type not supported!"),
    INVALID_LENGTH(20002, "Invalid length"),
    INVALID_PARAMETER(20003, "Invalid parameter"),

    SCHOOLNAME_EXIT(20005, "学校名称已经存在!"),
    CODE_EXIT(20006, "编码已存在!"),
    SUBORDINATE_NODE(20004,"Subordinate node"),
    IMPORT_SUCCESS(90001,"数据导入成功"),
    IMPORT_ERROR(90002,"数据导入错误，下载错误信息查看！"),
    IMPORT_EXCEPTION(90003,"数据导入异常！");



    public int code;

    public String message;

    UcenterResultConstant(int code, String message) {
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
