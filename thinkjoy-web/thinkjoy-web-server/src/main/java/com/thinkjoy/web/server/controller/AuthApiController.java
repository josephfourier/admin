package com.thinkjoy.web.server.controller;

import com.thinkjoy.ams.dao.model.OauthAccessToken;
import com.thinkjoy.ams.dao.model.OauthClientDetails;
import com.thinkjoy.ams.dao.model.OauthClientDetailsExample;
import com.thinkjoy.ams.rpc.api.OauthClientDetailsService;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.MD5Util;
import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.oauth.client.util.SerializableUtil;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.UcenterUserExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserAccountDto;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserInfoDto;
import com.thinkjoy.ucenter.rpc.api.UcenterUserService;
import com.thinkjoy.web.common.constant.WebResult;
import com.thinkjoy.web.common.constant.WebResultConstant;
import com.thinkjoy.web.common.exception.BusindessException;
import com.thinkjoy.web.server.util.SigUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by wangcheng on 17/8/31.
 * 提供第三方系统对接接口,如登出,校验token,根据token查询用户信息
 */
@Api(value = "开放接口")
@Controller
@RequestMapping("/open")
public class AuthApiController extends BaseController {

    private final static Logger _log = LoggerFactory.getLogger(AuthApiController.class);


    // 全局会话key
    private final static String THINKJOY_OAUTH_SERVER_SESSION_ID = "thinkjoy-oauth-server-session-id";
    // 全局会话key列表
    private final static String THINKJOY_OAUTH_SERVER_SESSION_IDS = "thinkjoy-oauth-server-session-ids";
    // code key
    private final static String THINKJOY_OAUTH_SERVER_TOKEN = "thinkjoy-oauth-server-token";
    // shiro 会话
    private final static String THINKJOY_OAUTH_SHIRO_SESSION_ID = "thinkjoy-oauth-shiro-session-id";

    @Autowired
    private UcenterUserService ucenterUserService;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;


    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object getUserInfoByToken(HttpServletRequest request, @RequestParam("token") String token, @RequestParam("clientId") String clientId, @RequestParam("sig") String sig) {

        _log.info("访问接口的请求uri为 : {}", request.getRequestURI());
        // token 和 sig的校验
        Object res = SigUtil.preCheck(request, token, clientId, sig);

        if (res != null) {
            return res;
        }

        //根据tokenid获取sessionid
        String sessionId = RedisUtil.get(THINKJOY_OAUTH_SERVER_TOKEN + "_" + token);
        //根据sessionid获取token对象
        String stoken = RedisUtil.get(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sessionId);
        if (StringUtils.isBlank(stoken)) {
            return new WebResult(WebResultConstant.EMPTY_TOKEN, "Server:token为空!");
        }

        OauthAccessToken oauthAccessToken = SerializableUtil.protostuff_deserialize(stoken, OauthAccessToken.class);

        String username = oauthAccessToken.getUsername();
        if (StringUtils.isBlank(username)) {
            return new WebResult(WebResultConstant.EMPTY_USERNAME, "Server:username为空!");
        }

        // 获取用户登录信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);

        UserInfoDto userInfo = null;


        try {
            userInfo = ucenterUserService.getUserInfo(map);
        } catch (Exception e) {
            _log.info("getUserInfo:获取用户信息异常: {}", e);
        }

        if (userInfo == null) {
            return new WebResult(WebResultConstant.FAILED, "Server:服务器异常!");
        } else {
            return new WebResult(WebResultConstant.SUCCESS, userInfo);
        }
    }


    @ApiOperation(value = "校验token")
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    @ResponseBody
    public Object checkToken(HttpServletRequest request, @RequestParam("token") String token, @RequestParam("clientId") String clientId, @RequestParam("sig") String sig) {

        _log.info("访问接口的请求uri为 : {}", request.getRequestURI());
        // token 和 sig的校验
        Object res = SigUtil.preCheck(request, token, clientId, sig);

        if (res != null) {
            return res;
        } else {
            return new WebResult(WebResultConstant.SUCCESS, "token校验成功");
        }

    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout(HttpServletRequest request, @RequestParam("token") String token, @RequestParam("clientId") String clientId, @RequestParam("sig") String sig) {

        _log.info("访问接口的请求uri为 : {}", request.getRequestURI());
        // token 和 sig的校验
        Object res = SigUtil.logoutPreCheck(request, token, clientId, sig);

        if (res != null) {
            return res;
        }

        String sessionId = RedisUtil.get(THINKJOY_OAUTH_SERVER_TOKEN + "_" + token);
        // ①删除server_session_id
        RedisUtil.remove(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sessionId);
        // ②删除server_session_token
        RedisUtil.remove(THINKJOY_OAUTH_SERVER_TOKEN + "_" + token);

        // ③删除server_session_ids
        RedisUtil.lrem(THINKJOY_OAUTH_SERVER_SESSION_IDS, 1, sessionId);

        // ④删除shiro_session_id
        RedisUtil.remove(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sessionId);

        return new WebResult(WebResultConstant.SUCCESS, "logout成功");
    }

    /**
     * 安陆福斯特考试系统所需接口
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/useraccount", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserAccount(@RequestParam(value = "username", required = false) String username) {
        if (StringUtils.isBlank(username)) {
            return new WebResult(WebResultConstant.EMPTY_USERNAME, "用户账号为空!");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        UserAccountDto userAccount = ucenterUserService.getUserAccount(map);

        if (userAccount == null) {
            return new WebResult(WebResultConstant.INVALID_ACCOUNT, "无此账户信息!");
        }

        String password = userAccount.getPassword();
        if (StringUtils.isNotBlank(password)) {
            String ms = AESUtil.AESDecode(password);
            String s = MD5Util.MD5(ms);
            userAccount.setPassword(s);
        } else {
            return new WebResult(WebResultConstant.EMPTY_PASSWORD, "密码为空!");
        }
        return new WebResult(WebResultConstant.SUCCESS, userAccount);
    }

}
