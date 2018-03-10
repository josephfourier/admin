package com.thinkjoy.web.common.util;

import org.apache.shiro.SecurityUtils;

/**
 * Created by wangcheng on 17/8/21.
 */
public class UserUtil {

    //获取当前用户登录名
    public static String getCurrentUserLoginName(){
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

}
