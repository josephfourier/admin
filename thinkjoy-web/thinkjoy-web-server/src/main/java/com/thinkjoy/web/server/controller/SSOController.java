package com.thinkjoy.web.server.controller;

import com.thinkjoy.ams.dao.model.OauthAccessToken;
import com.thinkjoy.ams.dao.model.OauthClientDetailsExample;
import com.thinkjoy.ams.rpc.api.OauthClientDetailsService;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.oauth.client.shiro.session.OauthSession;
import com.thinkjoy.oauth.client.shiro.session.OauthSessionDao;
import com.thinkjoy.oauth.client.util.DateUtils;
import com.thinkjoy.oauth.client.util.OauthConstant;
import com.thinkjoy.oauth.client.util.SerializableUtil;
import com.thinkjoy.web.common.constant.WebResult;
import com.thinkjoy.web.common.constant.WebResultConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;


/**
 * 单点登录管理
 * Created by shuzheng on 2016/12/10.
 */
@Controller
@RequestMapping("/sso")
@Api(value = "单点登录管理", description = "单点登录管理")
public class SSOController extends BaseController {

    private final static Logger _log = LoggerFactory.getLogger(SSOController.class);
    // 全局会话key
    private final static String THINKJOY_OAUTH_SERVER_SESSION_ID = "thinkjoy-oauth-server-session-id";
    // 全局会话key列表
    private final static String THINKJOY_OAUTH_SERVER_SESSION_IDS = "thinkjoy-oauth-server-session-ids";
    // code key
    private final static String THINKJOY_OAUTH_SERVER_TOKEN = "thinkjoy-oauth-server-token";

    @Autowired
    OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    OauthSessionDao oauthSessionDao;

    @Autowired
    OAuthIssuer oAuthIssuer;


    @ApiOperation(value = "认证中心首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) throws Exception {
        String appid = request.getParameter("appid");
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("无效访问！");
        }
        // 判断请求认证系统是否注册
        OauthClientDetailsExample oauthClientDetailsExample = new OauthClientDetailsExample();
        oauthClientDetailsExample.createCriteria()
                .andClientIdEqualTo(appid);
        int count = oauthClientDetailsService.countByExample(oauthClientDetailsExample);

        if (0 == count) {
            throw new RuntimeException(String.format("未注册的clientId:%s", appid));
        }
        return "redirect:/sso/login?backurl=" + URLEncoder.encode(backurl, "utf-8");
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        String _tokenId = null;

        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();

            // 判断是否已登录，如果已登录，则回跳
            String token = jedis.get(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + serverSessionId);

            if (StringUtils.isBlank(token)) {
                /*
                 *  token为空:
                 *  情况①: shiro_session失效,serverSessionId为新的sid
                 *  情况②: server_session失效
                 *  对于这两种情况,定义为用户登录失效,即需要清除cookie,
                 *  以保证新用户可以正常登录
                 */
                clearCookies(request, response);
                return "/login.jsp";
            }
            OauthAccessToken oauthAccessToken = SerializableUtil.protostuff_deserialize(token, OauthAccessToken.class);

            if (oauthAccessToken != null) {
                _tokenId = oauthAccessToken.getTokenId();
            }
            String codeValue = jedis.get(THINKJOY_OAUTH_SERVER_TOKEN + "_" + _tokenId);

            //防止sessionid存在,而code不存在的状况,这里重新设置一下
            if (StringUtils.isNotBlank(token) && StringUtils.isBlank(codeValue)) {
                // code校验值
                jedis.setex(THINKJOY_OAUTH_SERVER_TOKEN + "_" + oauthAccessToken.getTokenId(), (int) subject.getSession().getTimeout() / 1000, serverSessionId);
                /*
                 * 对于server_token失效的情况,
                 * 目前在shiro_session 和 server_session 不为空的情况下,重设此值
                 */
                codeValue = jedis.get(THINKJOY_OAUTH_SERVER_TOKEN + "_" + _tokenId);
            }
            // token校验值
            if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(codeValue)) {
                // 回跳
                String backurl = request.getParameter("backurl");
                String _backurl = getBackurl(backurl, oauthAccessToken);

                _log.debug("认证中心帐号通过，带token回跳：{}", _backurl.toString());
                return "redirect:" + _backurl.toString();
            }
        } finally {
            RedisUtil.returnResource(jedis);
        }

        return "/login.jsp";
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        //TODO 获取学校编码(如果先选学校,那么就在这里讲学校编码放入token)

        String username = request.getParameter("username");//登录名
        String password = request.getParameter("password");//密码
        String rememberMe = request.getParameter("rememberMe");//记住功能
        if (StringUtils.isBlank(username)) {
            return new WebResult(WebResultConstant.EMPTY_USERNAME, "帐号不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return new WebResult(WebResultConstant.EMPTY_PASSWORD, "密码不能为空！");
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        int tout = (int) subject.getSession().getTimeout() / 1000;
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasToken = RedisUtil.get(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sessionId);
        // TOKEN校验值
        if (StringUtils.isBlank(hasToken)) {
            // 使用shiro认证
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                if (BooleanUtils.toBoolean(rememberMe)) {
                    usernamePasswordToken.setRememberMe(true);
                } else {
                    usernamePasswordToken.setRememberMe(false);
                }
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                return new WebResult(WebResultConstant.INVALID_USERNAME, "帐号不存在！");
            } catch (IncorrectCredentialsException e) {
                return new WebResult(WebResultConstant.INVALID_PASSWORD, "密码错误！");
            } catch (LockedAccountException e) {
                return new WebResult(WebResultConstant.INVALID_ACCOUNT, "帐号已锁定！");
            }
            // 更新session状态
            oauthSessionDao.updateStatus(sessionId, OauthSession.OnlineStatus.on_line);
            // 全局会话sessionId列表，供会话管理
            RedisUtil.lpush(THINKJOY_OAUTH_SERVER_SESSION_IDS, sessionId.toString());
            // 默认验证帐号密码正确，创建token
            //String token = UUID.randomUUID().toString();
            OauthAccessToken token = null;

            try {
                token = createToken();
            } catch (OAuthSystemException e) {
                return new WebResult(WebResultConstant.FAILED_GEN_TOKEN, "token生成失败!");
            }

            // 全局会话的TOKEN key为sessionid value为token对象
            RedisUtil.set(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sessionId, SerializableUtil.protostuff_serialize(token), token.getTokenExpiredSeconds());
            // TOKEN校验值, key为tokenId value为sessionid
            RedisUtil.set(THINKJOY_OAUTH_SERVER_TOKEN + "_" + token.getTokenId(), sessionId, token.getTokenExpiredSeconds());
        }
        // 回跳登录前地址
        //String backurl = request.getParameter("backurl");
        String backurl = null;
        if (StringUtils.isBlank(backurl)) {
            return new WebResult(WebResultConstant.SUCCESS, "/");
        } else {
            request.getSession().setAttribute("backurl", backurl);
            return new WebResult(WebResultConstant.SUCCESS, backurl);
        }
    }

    /**
     * 拼接免认证参数
     *
     * @param backurl          回调地址
     * @param oauthAccessToken token
     * @return 拼接后的地址
     */
    public String getBackurl(String backurl, OauthAccessToken oauthAccessToken) {
        StringBuilder _backurl = new StringBuilder();

        if (StringUtils.isBlank(backurl)) {
            _backurl.append("/");
        } else {
            _backurl.append(backurl);
            if (backurl.contains("?")) {
                //backurl += "&token=" + oauthAccessToken.getTokenId() + "&username=" + username;
                _backurl.append("&token=")
                        .append(oauthAccessToken.getTokenId())
                        .append("&username=")
                        .append(oauthAccessToken.getUsername())
                        .append("&userType=")
                        .append(oauthAccessToken.getUsertypeId())
                        .append("&userId=")
                        .append(oauthAccessToken.getUserId())
                        .append("&relationCode=")
                        .append(oauthAccessToken.getRelationCode())
                        .append("&perpersonalization=")
                        .append(oauthAccessToken.getPerPersonalization());
            } else {
                //backurl += "?token=" + oauthAccessToken.getTokenId() + "&username=" + username;
                _backurl.append("?token=")
                        .append(oauthAccessToken.getTokenId())
                        .append("&username=")
                        .append(oauthAccessToken.getUsername())
                        .append("&userType=")
                        .append(oauthAccessToken.getUsertypeId())
                        .append("&userId=")
                        .append(oauthAccessToken.getUserId())
                        .append("&relationCode=")
                        .append(oauthAccessToken.getRelationCode())
                        .append("&perpersonalization=")
                        .append(oauthAccessToken.getPerPersonalization());
            }
        }

        return _backurl.toString();
    }

    public OauthAccessToken createToken() throws OAuthSystemException {

        OauthAccessToken oauthAccessToken = new OauthAccessToken();
        oauthAccessToken.setTokenId(oAuthIssuer.accessToken());
        oauthAccessToken.setUsername((String) SecurityUtils.getSubject().getPrincipal());
        oauthAccessToken.setCtime(DateUtils.now().getTime());
        oauthAccessToken.setTokenExpiredSeconds(OauthConstant.TOKEN_EXPIRE_TIME);
        oauthAccessToken.setRefreshToken(oAuthIssuer.refreshToken());
        oauthAccessToken.setRefreshTokenExpiredSeconds(OauthConstant.REFRESH_TOKEN_EXPIRE_TIME);

        return oauthAccessToken;
    }

    @ApiOperation(value = "校验token")
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseBody
    public Object code(HttpServletRequest request) {

        String codeParam = null;
        String code = null;
        try {
            codeParam = request.getParameter("token");
            String sessionId = RedisUtil.get(THINKJOY_OAUTH_SERVER_TOKEN + "_" + codeParam);
            code = SerializableUtil
                    .protostuff_deserialize(RedisUtil
                            .get(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sessionId), OauthAccessToken.class).getTokenId();
        } catch (Exception e) {
            return new WebResult(WebResultConstant.FAILED, "无效TOKEN");
        }

        if (StringUtils.isBlank(codeParam) || !codeParam.equals(code)) {
            return new WebResult(WebResultConstant.FAILED, "无效TOKEN");
        }
        return new WebResult(WebResultConstant.SUCCESS, code);
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }

        clearCookies(request, response);
        return "redirect:" + redirectUrl;
    }

    /**
     * 清除以 "thinkjoy" 开头的cookie
     *
     * @param request
     * @param response
     */
    public void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("thinkjoy")) {
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

}