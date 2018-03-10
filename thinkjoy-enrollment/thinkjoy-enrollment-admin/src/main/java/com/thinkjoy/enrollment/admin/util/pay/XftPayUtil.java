package com.thinkjoy.enrollment.admin.util.pay;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xjli on 15-4-8.
 */
public class XftPayUtil {
    private static PropertiesLoaderUtil propertiesLoaderUtil = null;

    static {
        // 读取配置文件对象
        propertiesLoaderUtil = new PropertiesLoaderUtil("classpath:/config/xfttpay.properties");

    }

    public static String getOrderUrl() {
        return propertiesLoaderUtil.getProperty("order_url");
    }
    public static String getPayrUrl() {
        return propertiesLoaderUtil.getProperty("pay_url");
    }

    public static String getNotifyUrl() {
        return propertiesLoaderUtil.getProperty("notify_url");
    }

    public static String getPageUrl() {
        return propertiesLoaderUtil.getProperty("page_url");
    }


}
