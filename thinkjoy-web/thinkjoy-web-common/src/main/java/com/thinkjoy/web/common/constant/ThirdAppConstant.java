package com.thinkjoy.web.common.constant;

/**
 * Created by wangcheng on 18/1/30.
 */
public class ThirdAppConstant {

    public class ALWX{
        /**
         *     安陆微校对接, 返回的code只适用微校,其他系统不可用
         */
        public static final String THIRD_PART = "THIRD_PART";

        public static final String SUCCESS = "0";
        public static final String FAILED = "1";
        public static final String SYSTEM_BUSY = "-1";
        public static final String INVALID_PARAMS = "-2";
        public static final String INVALID_MD5 = "-3";
        public static final String NULLPOINT = "-4";
        public static final String INVALID_USERNAME = "10001";
        public static final String INVALID_PASSWORD = "10002";
        public static final String INVALID_IP = "10003";
        public static final String INVALID_PERMISSION_TYPE = "10004";
        public static final String NO_PASS_PERMISSION = "10005";
        public static final String OVERDUE_TOKEN = "10006";
        public static final String MISSING_TOKEN = "10007";
        public static final String INVALID_TOKEN = "10008";
        public static final String MISSING_SCHOOL = "20001";
        public static final String MISSING_USERINFO = "20002";
    }

}
