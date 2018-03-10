package com.thinkjoy.web.dao;


import com.thinkjoy.common.util.MybatisGeneratorUtil;
import com.thinkjoy.common.util.PropertiesFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangcheng on 17/7/20.
 *
 * MBATIS 代码生成配置调用类
 */
public class Generator {

    // 基础属性,根据不同的项目修改
    private static final String MODULE = "thinkjoy-web";
    private static final String DATABASE = "zjy";
    private static final String TABLE_PREFIX = "web_user_appcollections";
    private static final String PACKAGE_NAME = "com.thinkjoy.web";
    private static final String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static final String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static final String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static final String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");


    // 需要insert后返回主键的表配置，key:表名,value:主键名
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();
    static {

    }

    /**
     * 代码生成
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        MybatisGeneratorUtil.generator(
                JDBC_DRIVER,
                JDBC_URL,
                JDBC_USERNAME,
                JDBC_PASSWORD,
                MODULE,
                DATABASE,
                TABLE_PREFIX,
                PACKAGE_NAME,
                LAST_INSERT_ID_TABLES);

    }

}
