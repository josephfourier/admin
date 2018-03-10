package com.thinkjoy.enrollment.admin.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.common.util.StringUtil;
import com.thinkjoy.exception.BusindessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangcheng on 17/11/27.
 */
@Controller
@RequestMapping
@Api(value = "登出", description = "登出")
public class LogoutController extends BaseController {


    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回个人中心
        String sid = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.session.id");
        String srvrl = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.sso.server.url");

        if (StringUtils.isBlank(sid) || StringUtils.isBlank(srvrl)) {
            throw new BusindessException("thinkjoy.web.session.id 或 thinkjoy.web.sso.server.url 没有配置!");
        }

        clearCookies(response, sid);
        return "redirect:" + srvrl;
    }

    /**
     * 清除以 子系统 的cookie
     *
     * @param response
     */
    public void clearCookies(HttpServletResponse response, String sid) {
        Cookie cookie = new Cookie(sid, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
