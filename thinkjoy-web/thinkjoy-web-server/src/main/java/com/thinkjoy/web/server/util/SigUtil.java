package com.thinkjoy.web.server.util;

import com.thinkjoy.ams.dao.model.OauthClientDetails;
import com.thinkjoy.ams.dao.model.OauthClientDetailsExample;
import com.thinkjoy.ams.rpc.api.OauthClientDetailsService;
import com.thinkjoy.common.util.MD5Util;
import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.common.util.SpringContextUtil;
import com.thinkjoy.web.common.constant.WebResult;
import com.thinkjoy.web.common.constant.WebResultConstant;
import com.thinkjoy.web.common.exception.BusindessException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangcheng on 17/9/1.
 */
public class SigUtil {

    // 全局会话key
    private final static String THINKJOY_OAUTH_SERVER_SESSION_ID = "thinkjoy-oauth-server-session-id";
    // 全局会话key列表
    private final static String THINKJOY_OAUTH_SERVER_SESSION_IDS = "thinkjoy-oauth-server-session-ids";
    // code key
    private final static String THINKJOY_OAUTH_SERVER_TOKEN = "thinkjoy-oauth-server-token";

    //获取签名
    public static String getSig(String uri, String clientId) {

        OauthClientDetailsService oauthClientDetailsService =
                (OauthClientDetailsService)SpringContextUtil.getBean("oauthClientDetailsService");


        OauthClientDetailsExample oauthClientDetailsExample = new OauthClientDetailsExample();
        oauthClientDetailsExample.createCriteria().andClientIdEqualTo(clientId);
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.selectFirstByExample(oauthClientDetailsExample);


        if (StringUtils.isBlank(oauthClientDetails.getClientSecret())) {
            throw new BusindessException("clientSecret不能为空!");
        }

        String sig = genSig(uri, clientId, oauthClientDetails.getClientSecret());

        return sig;
    }

    //生成签名
    public static String genSig(String uri, String clientId, String clientSecret) {

        StringBuilder sb = new StringBuilder();
        sb.append(uri).append(clientId).append(clientSecret);

        String sig = MD5Util.MD5(sb.toString());

        return sig;
    }


    private static Object validateParams(String token, String clientId, String sig){
        if (StringUtils.isBlank(token)) {
            return new WebResult(WebResultConstant.EMPTY_TOKEN, "token不能为空");
        }

        if (StringUtils.isBlank(clientId)) {
            return new WebResult(WebResultConstant.EMPTY_CLIENTID, "clientId不能为空");
        }

        if (StringUtils.isBlank(sig)) {
            return new WebResult(WebResultConstant.EMPTY_SIG, "sig不能为空");
        }

        return null;
    }


    public static Object preCheck(HttpServletRequest request,
                           String token,
                           String clientId,
                           String sig){
        // ①校验参数
        Object sigRes = validateParams(token, clientId, sig);

        if (sigRes != null){
            return sigRes;
        }

        // ②校验token超时
        Object res = checkExpireToken(token);

        if (res != null) {
            return res;
        }

        // ③校验sig
        String requestURI = request.getRequestURI();

        String  getSigRes = SigUtil.getSig(requestURI, clientId);

        if (!sig.equals(getSigRes)){
            return new WebResult(WebResultConstant.INVALID_SIG, "无效的sig");
        }

        return null;
    }

    /**
     * logout接口的参数校验,不进行token失效校验
     * @param request
     * @param token
     * @param clientId
     * @param sig
     * @return
     */
    public static Object logoutPreCheck(HttpServletRequest request,
                                  String token,
                                  String clientId,
                                  String sig){
        // ①校验参数
        Object sigRes = validateParams(token, clientId, sig);

        if (sigRes != null){
            return sigRes;
        }

        // ②校验sig
        String requestURI = request.getRequestURI();

        String  getSigRes = SigUtil.getSig(requestURI, clientId);

        if (!sig.equals(getSigRes)){
            return new WebResult(WebResultConstant.INVALID_SIG, "无效的sig");
        }

        return null;
    }

    public static Object checkExpireToken(String token) {

        long expTime = RedisUtil.ttl(THINKJOY_OAUTH_SERVER_TOKEN + "_" + token);

        if (expTime == -2) {//未查到对应的token,或已经超时
            return new WebResult(WebResultConstant.INVALID_TOKEN, "TOKEN超时或无效");
        }

        return null;
    }




}
