package com.thinkjoy.common.base;

/**
 * 全局常量
 * Created by  on 2017/2/18.
 */
public class BaseConstants {


    /**
     * 全局返回编码
     */
    public static class ResultCode {

        //异常
        public static final int ERROR = 0;
        //成功
        public static final int SUCCESS = 1;
    }

    /**
     * 系统权限级别
     */
    public static class UpmsType {
        /**
         * 0.系统
         * 1.目录
         * 2.菜单
         * 3.按钮
         */
        public static final String SYSTEM = "0";
        public static final String CATALOG = "1";
        public static final String MENU = "2";
        public static final String BUTTON = "3";
    }

    /**
     * 管理员类型
     */
    public static class ManagerType {

        /**
         * 1.超级管理员
         * 2.区域管理员
         * 3.学校管理员
         */
        public static final String SUPMANAGER = "1";
        public static final String AREAMANAGER = "2";
        public static final String SCHMANAGER = "3";
    }

    /**
     * 状态常量
     */
    public static class Status {
        /**
         * 正常
         */
        public static final String NORMAL = "1";
        /**
         * 锁定
         */
        public static final String LOCKING = "0";
    }

    /**
     * 缴费状态常量
     */
    public static class FeeStatus {
        /**
         * 未交费
         */
        public static final String WJF = "1";
        /**
         * 预交费
         */
        public static final String YJF = "2";
        /**
         * 已交费
         */
        public static final String YJJF = "3";
        /**
         * 待退费
         */
        public static final String DTF = "4";
        /**
         * 已退费
         */
        public static final String YTF = "5";
    }

    /**
     * 录取状态常量
     */
    public static class EnrollStatus {
		/**
		 * 院校在审阅
		 */
		public static final String YXWSY = "0";
        /**
         * 未录取
         */
        public static final String WLQ = "1";
        /**
         * 预录取
         */
        public static final String YLQ = "2";
        /**
         * 已录取
         */
        public static final String YJLQ = "3";
        /**
         * 已录取被删除
         */
        public static final String SCLQ = "4";
    }
	/**
	 * 性别状态
	 */
	public static class sexStatus {
		/**
		 * 男
		 */
		public static final String man = "0";
		/**
		 * 女
		 */
		public static final String woman = "1";
	}

	/**
	 * 政治面貌
	 */
	public static class politicsStatus {
		/**
		 * 党员
		 */
		public static final String dy = "1";
		/**
		 * 预备党员
		 */
		public static final String ybdy= "2";
		/**
		 * 团员
		 */
		public static final String ty = "3";
		/**
		 * 群众
		 */
		public static final String qz= "4";
	}

	/**
	 * 招生对象
	 */
	public static class targetStatus {
		/**
		 * 小学毕业
		 */
		public static final String xxby = "110";
		/**
		 * 初中毕业
		 */
		public static final String czby= "111";
		/**
		 * 中职毕业
		 */
		public static final String zzby = "112";
		/**
		 * 高中毕业
		 */
		public static final String qzby= "113";
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
        public static final String YIJI ="1";
        public static final String ERJI ="2";
        public static final String SANJI ="3";
    }


    /**
     * 字典常量类
     */
    public static class Dict {
        //常量前缀
        public static final String PREFIX = "DICT_";

        //接入类型
        public static final String ACCESSTYPE = PREFIX + "ACCESSTYPE";
        //设备类型
        public static final String DEVICETYPE = PREFIX + "DEVICETYPE";
        //应用分类
        public static final String APPCLASS = PREFIX + "APPCLASS";
        //用户类型
        public static final String APPLICABLEIDENTITY = PREFIX + "APPLICABLEIDENTITY";
        //启动状态
        public static final String STATUS = PREFIX + "STATUS";
        //区域类型
        public static final String AREATYPE = PREFIX + "AREATYPE";
        //资源类型
        public static final String RESOURCETYPE = PREFIX + "RESOURCETYPE";
        //资源分类
        public static final String RESOURCECLASS = PREFIX + "RESOURCECLASS";

        //协同办公 通知类型
        public static final String NOTIFY_TYPE = PREFIX + "NOTIFYTYPE";
        //专业类型
        public static final String MAJORTYPE = PREFIX + "MAJTYPE";
        //学制
        public static final String XZ = PREFIX + "XZ";
        //民族
        public static final String NATION = PREFIX + "NATION";
        //招生对象
        public static final String TARGET = PREFIX + "TARGET";
        //专业性质
        public static final String FACUlTY_NATURE = PREFIX + "FACUlTY_NATURE";
        //专业类别
        public static final String FACUlTY_TYPE = PREFIX + "FACUlTY_TYPE";
        //学生类别
        public static final String STUDENTTYPE = PREFIX + "STUDENTTYPE";
        //政治面貌
        public static final String POLITICS = PREFIX + "POLITICS";
        //是否贫困生
        public static final String ISPOOR = PREFIX + "ISPOOR";
        //是否住校
        public static final String ISLIVESCHOOL = PREFIX + "ISLIVESCHOOL";
        //性别
        public static final String SEX = PREFIX + "SEX";
        //缴费状态feeStatus
        public static final String FEESTATUS = PREFIX + "FEESTATUS";
        //录取状态
        public static final String ENROLLSTATUS = PREFIX + "ENROLLSTATUS";
        //录取志愿
        public static final String ENROLLWILL = PREFIX + "ENROLLWILL";
        //学历
        public static final String EDUCATION = PREFIX + "EDUCATION";
        //户籍性质
        public static final String DOMICILETYPE = PREFIX + "DOMICILETYPE";
        //权限个性化
        public static final String PERPERSONALIZATION = PREFIX + "PERPERSONALIZATION";
        //缴费项目是否必须
        public static final String ISMUST = PREFIX + "ISMUST";
        //职务级别
        public static final String TEACHERLEVEl= PREFIX + "POST_LEVEL";
        //部门类型
        public static final String DEPTTYPE= PREFIX + "DEPT_TYPE";
        //是否有审批流程
        public static final String ISAPPROVAL= PREFIX + "ISAPPROVAL";
    }

    public static class UserType {
        public static final int STUDENT = 1;
        public static final int PARENTS = 2;
        public static final int TEACHER = 3;
        public static final int ORGAN = 4;
    }

    public static class PerPersonal {
        public static final String YES = "1";
        public static final String NO = "0";
    }



    public static class ImportModel {
        public static final String BASEMODEL_STUDENT = "ucenterStudent";
        public static final String BASEMODEL_TEACHER = "ucenterTeacher";
        public static final String BASEMODEL_ENROLL_STUDENT = "enrollStudent";
    }

    /**
     * excel导入错误
     */
    public static class ImportError {
        public static final String PROPERTY_REQUIRED = "不能为空";
        public static final String DATA_FORMAT = "数据格式错误";
        public static final String DICT_ERROR = "字典中没有对应值";
        public static final String PROPERTY_FACULTYNAME = "学校没有对应院系";
        public static final String PROPERTY_SPECIALTYNAME = "学校没有对应专业";
        public static final String PROPERTY_SPECIALTYCODE = "该专业没有配置对应专业code";
        public static final String PROPERTY_SPECIALTY_FACULTYCODE = "该专业没有配置对应院系code";
        public static final String PROPERTY_SPECIALTY_FACULTYNAME = "该专业没有配置对应院系名称";
        public static final String PROPERTY_SPECIALTY_TRDRNAME = "该专业没有配置对应培养方向名称";
        public static final String PROPERTY_SPECIALTY_SCHSYS = "该专业没有配置对应学制名称";

        public static final String PROPERTY_CLASSNAME = "学校没有对应班级";
        public static final String PROPERTY_DEPARTMENT = "学校没有对应部门";
        public static final String PROPERTY_TEACHERNAME= "学校没有对应招生老师";
        public static final String PROPERTY_BATCHNAME = "学校该年度没有对应招生批次";
        public static final String PROPERTY_LENGTH = "数据长度错误";
        public static final String DATA_IMPORTEXCEPTION = "数据异常";
    }

    public static class ValidationType {
        public static final String NOTEMPTY = "notEmpty";
        public static final String EQUALLENGTH = "equalLength";
        public static final String MAXLENGTH = "maxLength";
        public static final String MINLENGTH = "minLength";
        public static final String REGEX = "regEx";
    }

    public static class colClass {
        public static final String COL_COMMON = "commonType";//普通类型，直接使用
        public static final String COL_DICTTYPE = "dictType";//字典类型，需要转换
        public static final String COL_ASSEMBLETYPE = "assembleType";//组合类型(字段用某个字符隔开,存储多这个信息)，需要转换
        public static final String COL_NAME2IDTYPE = "name2idType";//外键字段,根据名称获取id
        public static final String COL_OTHERTYPE = "otherType";//其他类型，独立处理
    }

    public static class AreaType {
        /**
         * 1--省
         * 2--市
         * 3--区县
         */
        public static final String PROVINCE = "1";
        public static final String CITT = "2";
        public static final String COUNTY = "3";
    }

}
