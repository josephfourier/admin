package com.thinkjoy.common.util;

import org.apache.shiro.SecurityUtils;

/**
 * Created by wangcheng on 17/7/26.
 * 用户信息获取工具类
 */
public class UserUtil {

    //获取当前用户名
    public static String getCurrentUser(){
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

}
