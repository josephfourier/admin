package com.thinkjoy.oauth.client.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.oauth.client.shiro.session.OauthSessionDao;
import com.thinkjoy.oauth.client.util.OauthConstant;
import com.thinkjoy.oauth.client.util.RequestParameterUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 重写authc过滤器
 * Created by shuzheng on 2017/3/11.
 */
public class OauthAuthenticationFilter extends AuthenticationFilter {

    private final static Logger _log = LoggerFactory.getLogger(OauthAuthenticationFilter.class);

    // 局部会话key
    private final static String THINKJOY_OAUTH_CLIENT_SESSION_ID = "thinkjoy-oauth-client-session-id";
    // 单点同一个code所有局部会话key
    private final static String THINKJOY_OAUTH_CLIENT_SESSION_IDS = "thinkjoy-oauth-client-session-ids";
    private final static String THINKJOY_OAUTH_SERVER_TOKEN = "thinkjoy-oauth-server-token";

    private final static String THINKJOY_OAUTH_SHIRO_SESSION_ID = "thinkjoy-oauth-shiro-session-id";
    private final static String THINKJOY_OAUTH_SERVER_SESSION_ID = "thinkjoy-oauth-server-session-id";

    @Autowired
    OauthSessionDao oauthSessionDao;


    /**
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        /*
         *  客户端的逻辑:
         *       如,职教云登录页(client)过来的请求,首先在client端拦截, 在这里判断服务类型,
         *       如果是,server端,则首先判断,当前subject时候认证?,
         *          是的话,不进行onAccessDenied逻辑,
         *          不是的话,进行onAccessDenied逻辑,
         *       如果是client端,首先去调用validateClient方法验证client端的登录状态
         */

        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        // 判断请求类型
        String webType = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.type");
        session.setAttribute(OauthConstant.WEB_TYPE, webType);
        //不同类型进行不同操作
        if ("client".equals(webType)) {
            return validateClient(request, response);
        }
        if ("server".equals(webType)) {
            return subject.isAuthenticated();
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        /*
         *  首先拿到,登录类型,server端的话就直接调到登录页面登录
         *      client端的话,拼接回调地址,然后浏览器重定向到认证中心
         *      server的话直接重定向登录逻辑
         */

        // 获取认证url
        StringBuffer sso_server_url = new StringBuffer(PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.sso.server.url"));
        // server需要登录
        String webType = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.type");
        if ("server".equals(webType)) {
            WebUtils.toHttp(response).sendRedirect(sso_server_url.append("/sso/login").toString());
            return false;
        }

        // client端的话需要跳转认证中心认证
        redireactToLogin(request, response, sso_server_url);
        return false;
    }


    /**
     * 认证中心登录成功带回code
     *
     * @param request
     */
    private boolean validateClient(ServletRequest request, ServletResponse response) {
        /*
         *  首先判断client是否登录,这里用sessionID对应在redis中session来判断,
         *      如果有,则证明登录过,且去更新登录信息状态
         *      没有则判断是否是已经去认证中心认证,且返回了,此时判断请求中是否包含code参数
         *          是的话,证明已经在认证中心认证过,这里只进行一个免登陆的过程
         *          不是的话,则证明没有在client登录,且还没有去认证中心登录,直接返回false,然后调用onAccessDenied逻辑
         *
         */

        // 获取当前用户对应的subject
        Subject subject = getSubject(request, response);

        // 验证第client端是否登录,登录的话,更新session的有效期
        if (checkClientIsLogin(subject, request, response)) {
            return true;
        }

        // client没登录的话,判断request参数中是否有认证中心token
        String token = checkHasTokenFromRequest(request);

        // client 获取已经token的话就进行免登陆,没有获取则调用onAccessDenied逻辑,到认证中心获取token
        return doNoLanding(request, response, token, subject);

    }


    public void redireactToLogin(ServletRequest request, ServletResponse response, StringBuffer sso_server_url) throws IOException {
        // 拼接认证中心重定向链接及参数
        sso_server_url
                .append("/sso/index")
                .append("?")
                .append("appid")
                .append("=")
                .append(PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.appID"));
        // 回跳地址
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        StringBuffer backurl = httpServletRequest.getRequestURL();
        String queryString = httpServletRequest.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            backurl.append("?").append(queryString);
        }
        sso_server_url.append("&").append("backurl").append("=").append(URLEncoder.encode(backurl.toString(), "utf-8"));
        WebUtils.toHttp(response).sendRedirect(sso_server_url.toString());

    }

    // 第一次验证第client端是否登录,登录的话,更新session的有效期
    public boolean checkClientIsLogin(Subject subject, ServletRequest request, ServletResponse response) {
        Jedis jedis = null;
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        try {

            jedis = RedisUtil.getJedis();
            // 判断局部会话是否登录
            String c = jedis.get(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + sessionId);
            //验证服务端server_token是否失效
            String serverSessionId = jedis.get(THINKJOY_OAUTH_SERVER_TOKEN + "_" + c);
            //验证服务端server_session是否失效
            long ttl = jedis.ttl(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + serverSessionId);

            /*
             * 子系统登录校验:
             * client_session不为空
             * server_token 和 server_session为空的话,证明父系统登录失效或异常,这里子系统也进行登出
             *
             * 这里没有校验server的shiro_session,
             * 对于server_token 和 server_session不为空,而server的shiro_session为空的情况
             * 这里只有redis没有存储成功或者获取成功导致,概率较低,目前暂时不要影响到子系统
             *
             * 当父系统操作时,会直接跳到登录首页,并且清除cookie
             */
            //验证code,codeValue为空表明server登录失效,则重新登录
            if (StringUtils.isNotBlank(c) && (StringUtils.isBlank(serverSessionId) || -2 == ttl)) {
                // 清除当前应用的局部会话(各自清除自己的对话)
                jedis.del(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + sessionId);
                jedis.srem(THINKJOY_OAUTH_CLIENT_SESSION_IDS + "_" + c, sessionId);
                //当登出之后,将client端产生的THINKJOY-OAUTH-SHIRO-SESSION-ID也清除
                jedis.del(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sessionId);
                return false;
            }

            if (StringUtils.isNotBlank(c)) {
                // 移除url中的code参数
                if (null != request.getParameter("token")) {
                    String backUrl = RequestParameterUtil.getParameterWithOutCode(WebUtils.toHttp(request));
                    HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                    try {
                        httpServletResponse.sendRedirect(backUrl.toString());
                    } catch (IOException e) {
                        _log.error("局部会话已登录，移除token参数跳转出错：", e);
                    }
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            _log.error("清除子系统sessionId : {} 异常:", sessionId, e);
        } finally {
            RedisUtil.returnResource(jedis);
        }

        return false;
    }

    public String checkHasTokenFromRequest(ServletRequest request) {
        String token = request.getParameter("token");
        return StringUtils.isNotBlank(token) ? token : "";
    }

    // client端免登陆
    public boolean doNoLanding(ServletRequest request, ServletResponse response, String token, Subject subject) {

        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        int timeOut = (int) session.getTimeout() / 1000;


        if (StringUtils.isNotBlank(token)) {
            // HttpPost去校验token(接口可以在统一api总线中提供)
            try {
                StringBuffer sso_server_url = new StringBuffer(PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.sso.server.url"));
                //HttpClient httpclient = new DefaultHttpClient();
                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(sso_server_url.toString() + "/sso/code");

                //封装参数对
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("token", token));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                    HttpEntity httpEntity = httpResponse.getEntity();
                    JSONObject result = JSONObject.parseObject(EntityUtils.toString(httpEntity));
                    if (1 == result.getIntValue("code") && result.getString("data").equals(token)) {
                        // token校验正确，创建局部会话
                        RedisUtil.set(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + sessionId, token, timeOut);
                        // 保存token对应的局部会话sessionId，方便退出操作,一个token对应多个系统的多个sessionid
                        RedisUtil.sadd(THINKJOY_OAUTH_CLIENT_SESSION_IDS + "_" + token, sessionId, timeOut);
                        _log.debug("token={}，对应的注册系统个数：{}个", token, RedisUtil.getJedis().scard(THINKJOY_OAUTH_CLIENT_SESSION_IDS + "_" + token));
                        // 返回请求资源
                        try {
                            // client无密认证
                            String username = request.getParameter("username");
                            subject.login(new UsernamePasswordToken(username, ""));
                            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

                            //将传过来的参数存入request中的session
                            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
                            HttpSession reqSession = httpServletRequest.getSession();
                            reqSession.setAttribute("username", username);
                            reqSession.setAttribute("userType", request.getParameter("userType"));
                            reqSession.setAttribute("userId", request.getParameter("userId"));
                            reqSession.setAttribute("relationCode", request.getParameter("relationCode"));
                            reqSession.setAttribute("perpersonalization", request.getParameter("perpersonalization"));

                            //登录完成后显式触发realm的权限方法
                            SecurityUtils.getSubject().isPermitted();
                            // 移除url中的token参数
                            //String backUrl = RequestParameterUtil.getParameterWithOutCode(WebUtils.toHttp(request));
                            String backUrl = httpServletRequest.getRequestURL().toString();
                            httpServletResponse.sendRedirect(backUrl.toString());
                            return true;
                        } catch (IOException e) {
                            _log.error("已拿到code，移除code参数跳转出错：", e);
                        }
                    } else {
                        _log.warn(result.getString("data"));
                    }
                }
            } catch (IOException e) {
                _log.error("验证token失败：", e);
            }
        }

        return false;
    }


}
