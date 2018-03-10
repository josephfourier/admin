package com.thinkjoy.common.util;

import java.util.Properties;

/**
 * 懒人模式的单例
 * Created by wangcheng on 2016/12/5.
 */
public class PropertiesUtil {

    private Properties properties;

    private static PropertiesUtil propertiesUtil = null;

    public synchronized static PropertiesUtil getInstance(){
        if (propertiesUtil == null){
            propertiesUtil = new PropertiesUtil();
        }
        return propertiesUtil;
    }

    private PropertiesUtil(){}

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
