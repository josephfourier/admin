package com.thinkjoy.ucenter.common.constant;

import com.thinkjoy.common.base.BaseConstants;

/**
 * Created by xufei on 2017/7/26.
 */
public class UcenterConstant  extends BaseConstants {

    public static final String UCENTER_TYPE = "thinkjoy.ucneter.type";

    /**
     * 区域类型
     */
    public static class AreaType{
        /**
         * 1.省级
         * 2.市级
         * 3.区/县
         */
        public static final String SHENG ="1";
        public static final String SHI ="2";
        public static final String QU_XIAN ="3";
    }
	/**
	 * 机构级别
	 */
	public static class Level{
		/**
		 * 0.一级
		 * 1.二级
		 * 2.三级
		 */
		public static final String YIJI ="0";
		public static final String ERJI ="1";
		public static final String SANJI ="2";
	}

    /**
     * 机构级别
     */
    public static class PostLevel{
        /**
         * 0.一级（校级）
         * 1.二级（院系级/部级）
         * 2.三级（班级）
         */
        public static final String YIJI ="0";
        public static final String ERJI ="1";
        public static final String SANJI ="2";
    }

    public static class UserType{
        /**
         *  1.学生
         *  2.家长
         *  3.老师
         *  4.机构
         */
        public static final String STUDENT = "1";
        public static final String PARENT = "2";
        public static final String TEACHER = "3";
        public static final String AGENCY = "4";
    }

    public static class DeptType{
        /**
         *  1.院系
         *  2.行政
         */
        public static final String YX = "1";
        public static final String XZ = "2";
    }

}
