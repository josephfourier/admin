package com.thinkjoy.web.server.controller.third;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.*;
import com.thinkjoy.common.xftpay.HttpUtils;
import com.thinkjoy.web.common.constant.ThirdAppConstant;
import com.thinkjoy.web.common.constant.ThirdResult;
import com.thinkjoy.web.common.exception.ThirdBussinessException;
import com.thinkjoy.web.server.controller.third.entity.LoginAccessTokenReq;
import com.thinkjoy.web.server.controller.third.entity.LoginAccessTokenResp;
import com.thinkjoy.web.server.controller.third.entity.TokenApply;
import com.thinkjoy.web.server.controller.third.entity.TokenCallBack;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author wangcheng
 * @date 18/1/30
 */
@Controller
@RequestMapping("/third")
public class AlwxController extends BaseController {

    private final static Logger _log = LoggerFactory.getLogger(AlwxController.class);

    public static final String ACCESS_URL = PropertiesUtil.getInstance().getProperties().getProperty("third.al.wx.url");
    public static final String ACCOUNT = PropertiesUtil.getInstance().getProperties().getProperty("third.al.wx.account");
    public static final String SECRET = PropertiesUtil.getInstance().getProperties().getProperty("third.al.wx.secret");
    public static final String TOKEN_KEY = PropertiesUtil.getInstance().getProperties().getProperty("third.al.wx.redis.tokenKey");

    @RequestMapping(value = "/alwx/teacher", method = RequestMethod.GET)
    public void alwx(HttpServletResponse response,
                     @RequestParam(value = "relationCode", required = false) String schoolKey,
                     @RequestParam(value = "phone", required = false) String phone) throws IOException {

        if (StringUtils.isBlank(schoolKey) || StringUtils.isBlank(phone)) {
            throw new ThirdBussinessException("请求参数为空,请确认参数是否正确!");
        }

        //①先从redis中获取token,如果没获取到则表示失效,重新获取
        String token = RedisUtil.get(TOKEN_KEY);
        _log.info("alwx()-->token=={}", token);
        TokenCallBack tokenCallBack = new TokenCallBack();
        LoginAccessTokenResp loginAccessTokenResp = new LoginAccessTokenResp();
        if (StringUtils.isBlank(token)) {
            //②获取微校的token用于调用其接口
            ThirdResult result = getTokenApply(tokenCallBack, ACCOUNT, SECRET);
            if (null != result) {
                throw new ThirdBussinessException(result.getMessage());
            }
        }
        //④根据account,token,schoolKey(职教云的schoolCode),phone调用微校接口,获取accessToken和微校对应的schoolCode
        ThirdResult aTokenResult = getLoginAccessToken(ACCOUNT, loginAccessTokenResp, tokenCallBack, token, schoolKey, phone);
        if (null != aTokenResult) {
            throw new ThirdBussinessException(aTokenResult.getMessage());
        }

        //token过期或不正确,则重新获取,并重新执行登录逻辑(如果登录返回结果仍是token过期或不正确,则退出)
        if (loginAccessTokenResp.getResultCode().equals(ThirdAppConstant.ALWX.OVERDUE_TOKEN)
                || loginAccessTokenResp.getResultCode().equals(ThirdAppConstant.ALWX.MISSING_TOKEN)
                || loginAccessTokenResp.getResultCode().equals(ThirdAppConstant.ALWX.INVALID_TOKEN)) {
            tokenCallBack = new TokenCallBack();
            loginAccessTokenResp = new LoginAccessTokenResp();
            String _token = RedisUtil.get(TOKEN_KEY);

            //相等则表示还没有人更新此值
            if (StringUtils.isBlank(_token) || token.equals(_token)) {
                ThirdResult result = getTokenApply(tokenCallBack, ACCOUNT, SECRET);
                if (null != result) {
                    throw new ThirdBussinessException(result.getMessage());
                }
            } else {
                //不相等则表示别人已经更新过此值了,直接用即可
                token = _token;
            }

            ThirdResult _aTokenResult = getLoginAccessToken(ACCOUNT, loginAccessTokenResp, tokenCallBack, token, schoolKey, phone);
            if (null != _aTokenResult) {
                throw new ThirdBussinessException(_aTokenResult.getMessage());
            }

            if (!loginAccessTokenResp.getResultCode().equals(ThirdAppConstant.ALWX.SUCCESS)) {
                throw new ThirdBussinessException(loginAccessTokenResp.getResultMsg());
            }
        }

        String loginUrl = ACCESS_URL + "exncb/login/toTeaPc?accessToken=" + loginAccessTokenResp.getAccessToken() + "&schoolCode=" + loginAccessTokenResp.getSchoolCode();
        _log.info("alwx()-->loginUrl=={}", loginUrl);

        //⑤重定向到微校登录地址
        response.sendRedirect(loginUrl);

        return;
    }

    /**
     * 获取微校接口调用凭证(token)
     *
     * @param tokenCallBack 返回参数
     * @param account       微校分配的appid
     * @param secret        微校分配的appsecret
     * @return
     */
    protected ThirdResult getTokenApply(TokenCallBack tokenCallBack, String account, String secret) {
        try {
            String md5secret = MD5Util.LowerCase_MD5(secret);
            _log.info("md5secret=={}", md5secret);
            if (StringUtils.isBlank(md5secret)) {
                return new ThirdResult(ThirdAppConstant.ALWX.INVALID_MD5, "md5加密失败!", null);
            }
            TokenApply tokenApply = new TokenApply();
            tokenApply.setSecret(md5secret);
            tokenApply.setAccount(account);
            tokenApply.setAuthType(ThirdAppConstant.ALWX.THIRD_PART);
            String json = JsonUtil.tranObjectToJsonStr(tokenApply);
            String encryptBASE64 = BASE64Util.encryptBASE64(json.getBytes("UTF-8"));
            String post = HttpUtils.post(ACCESS_URL + "external/token/get", encryptBASE64, null, "application/json;charset=utf-8");
            TokenCallBack tCallBack = BASE64Util.decryptBASE64(post, TokenCallBack.class);
            _log.info("getTokenApply()--->json=={}", json);
            _log.info("getTokenApply()--->encryptBASE64=={}", encryptBASE64);
            _log.info("getTokenApply()--->post=={}", post);
            _log.info("getTokenApply()--->tCallBack=={}", tCallBack);
            if (tCallBack == null) {
                //返回获取token失败
                return new ThirdResult(ThirdAppConstant.ALWX.NULLPOINT, "获取tokenCallBack为空!", null);
            }
            if (!tCallBack.getResultCode().equals(ThirdAppConstant.ALWX.SUCCESS)) {
                return new ThirdResult(tCallBack.getResultCode(), tCallBack.getResultMsg(), null);
            }

            tokenCallBack.setResultCode(tCallBack.getResultCode());
            tokenCallBack.setResultMsg(tCallBack.getResultMsg());
            tokenCallBack.setToken(tCallBack.getToken());
            long expireAt = Long.parseLong(tCallBack.getToken().getExpireAt());
            _log.info("getTokenApply()--->expireAt=={}", expireAt);
            //③调用微校接口,获取token,并缓存至redis,避免重复调用
            RedisUtil.setKeyPexpireAt(TOKEN_KEY, tCallBack.getToken().getValue(), expireAt);

        } catch (Exception e) {
            e.printStackTrace();
            _log.error("获取tokenCallBack异常!");
            return new ThirdResult(ThirdAppConstant.ALWX.FAILED, "获取tokenCallBack异常!", null);
        }
        return null;
    }

    /**
     * 获取微校用户登录凭证(accessToken)
     *
     * @param account              微校分配的appid
     * @param loginAccessTokenResp 返回的参数
     * @param tCallBack            获取token要用
     * @param token                token
     * @param schoolKey            职教云安陆学校所对应的学校code
     * @param phone                老师手机号
     * @return
     */
    protected ThirdResult getLoginAccessToken(String account,
                                              LoginAccessTokenResp loginAccessTokenResp,
                                              TokenCallBack tCallBack,
                                              String token,
                                              String schoolKey,
                                              String phone) {
        try {
            String tpToken;
            if (tCallBack.getToken() == null) {
                tpToken = token;
            } else {
                tpToken = tCallBack.getToken().getValue();
            }
            _log.info("getLoginAccessToken()-->token=={}", token);
            _log.info("getLoginAccessToken()-->tpToken=={}", tpToken);
            String getLoginAccessTokenUrl = ACCESS_URL + "external/api/getLoginAccessToken?tpAccount=" + account + "&tpToken=" + tpToken;
            LoginAccessTokenReq loginAccessTokenReq = new LoginAccessTokenReq();
            loginAccessTokenReq.setPhone(phone);
            loginAccessTokenReq.setSchoolKey(schoolKey);

            String json = JsonUtil.tranObjectToJsonStr(loginAccessTokenReq);
            String encryptBASE64 = BASE64Util.encryptBASE64(json.getBytes("UTF-8"));
            String post = HttpUtils.post(getLoginAccessTokenUrl, encryptBASE64, null, "application/json;charset=utf-8");
            LoginAccessTokenResp resp = BASE64Util.decryptBASE64(post, LoginAccessTokenResp.class);

            _log.info("getLoginAccessToken()--->json=={}", json);
            _log.info("getLoginAccessToken()--->encryptBASE64=={}", encryptBASE64);
            _log.info("getLoginAccessToken()--->post=={}", post);
            _log.info("getLoginAccessToken()--->getLoginAccessTokenUrl=={}", getLoginAccessTokenUrl);
            _log.info("getLoginAccessToken()--->resp=={}", resp);

            if (resp == null) {
                return new ThirdResult(ThirdAppConstant.ALWX.NULLPOINT, "获取loginAccessTokenResp为空!", null);
            }

            if (!(resp.getResultCode().equals(ThirdAppConstant.ALWX.OVERDUE_TOKEN)
                    || resp.getResultCode().equals(ThirdAppConstant.ALWX.MISSING_TOKEN)
                    || resp.getResultCode().equals(ThirdAppConstant.ALWX.INVALID_TOKEN)
                    || resp.getResultCode().equals(ThirdAppConstant.ALWX.SUCCESS))) {
                return new ThirdResult(resp.getResultCode(), resp.getResultMsg(), null);
            }

            loginAccessTokenResp.setResultCode(resp.getResultCode());
            loginAccessTokenResp.setResultMsg(resp.getResultMsg());
            loginAccessTokenResp.setAccessToken(resp.getAccessToken());
            loginAccessTokenResp.setSchoolCode(resp.getSchoolCode());

        } catch (Exception e) {
            e.printStackTrace();
            _log.error("获取loginAccessTokenResp异常!");
            return new ThirdResult(ThirdAppConstant.ALWX.FAILED, "获取loginAccessTokenResp异常!", null);
        }
        return null;
    }

}
