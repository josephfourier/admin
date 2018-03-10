package com.thinkjoy.oauth.client.shiro.session;

import com.thinkjoy.ams.dao.model.OauthAccessToken;
import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.common.web.ServletUtil;
import com.thinkjoy.oauth.client.util.OauthConstant;
import com.thinkjoy.oauth.client.util.SerializableUtil;
import com.thinkjoy.common.util.RedisUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * 基于redis的sessionDao，缓存共享session
 * Created by shuzheng on 2017/2/23.
 */
public class OauthSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(OauthSessionDao.class);
    // shiro会话key,value为session对象
    private final static String THINKJOY_OAUTH_SHIRO_SESSION_ID = "thinkjoy-oauth-shiro-session-id";
    // key为sessionId,value为生成的token
    private final static String THINKJOY_OAUTH_SERVER_SESSION_ID = "thinkjoy-oauth-server-session-id";
    // 所有sessionID的集合
    private final static String THINKJOY_OAUTH_SERVER_SESSION_IDS = "thinkjoy-oauth-server-session-ids";
    // key为sessionID,value为token,用于第三方校验token有效性
    private final static String THINKJOY_OAUTH_SERVER_TOKEN = "thinkjoy-oauth-server-token";
    // 局部会话key
    private final static String THINKJOY_OAUTH_CLIENT_SESSION_ID = "thinkjoy-oauth-client-session-id";
    // 单点同一个code所有局部会话key
    private final static String THINKJOY_OAUTH_CLIENT_SESSION_IDS = "thinkjoy-oauth-client-session-ids";

    @Override
    protected Serializable doCreate(Session session) {
        //给新创建的session生成sessionID,UUID规则
        Serializable sessionId = generateSessionId(session);
        //设置sessionID
        assignSessionId(session, sessionId);
        RedisUtil.set(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        _log.debug("doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session s = null;

        // 首先从request中尝试获取,避免一次请求多次调用redis
        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null) {
            s = (Session) request.getAttribute("session_" + sessionId);
        }
        if (s != null) {
            return s;
        }
        // 没有的话,再从redis中获取
        String session = RedisUtil.get(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sessionId);
        _log.debug("doReadSession >>>>> sessionId={}", sessionId);
        s = SerializableUtil.deserialize(session);

        // redis中有得话,缓存session
        if (request != null && s != null) {
            request.setAttribute("session_" + sessionId, s);
        }

        return s;
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        // 更新session的最后一次访问时间
        OauthSession oauthSession = (OauthSession) session;
        OauthSession cacheOauthSession = (OauthSession) doReadSession(session.getId());
        if (null != cacheOauthSession) {
            oauthSession.setStatus(cacheOauthSession.getStatus());
            oauthSession.setAttribute("FORCE_LOGOUT", cacheOauthSession.getAttribute("FORCE_LOGOUT"));
        }


        String serialize = SerializableUtil.serialize(session);
        serialize = StringUtils.isBlank(serialize) ? "" : serialize;
        String webType = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.type");

        String sid = session.getId() + "";
        int timeout = (int) session.getTimeout() / 1000;

        if (StringUtils.isNotBlank(webType) && webType.equals("server")) {
            // 更新THINKJOY_OAUTH_SHIRO_SESSION_ID,THINKJOY_OAUTH_SERVER_SESSION_ID、THINKJOY_OAUTH_SERVER_TOKEN过期时间
            //登录成功后才会一起维护
            Jedis jedis = null;
            try {
                jedis = RedisUtil.getJedis();
                //获取token并反序列化得到token对象,拿到token字符串
                String token = jedis.get(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + session.getId());
                OauthAccessToken oauthAccessToken = SerializableUtil.protostuff_deserialize(token, OauthAccessToken.class);
                String code = oauthAccessToken.getTokenId();

                Transaction multi = jedis.multi();
                multi.setex(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sid, timeout, serialize);
                if (StringUtils.isNotBlank(code)) {
                    multi.expire(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sid, timeout);
                    multi.expire(THINKJOY_OAUTH_SERVER_TOKEN + "_" + code, timeout);
                }
                multi.exec();
            } catch (Exception e) {
                _log.error("doUpdate error :" + e);
            } finally {
                RedisUtil.returnResource(jedis);
            }
        }

        if (StringUtils.isNotBlank(webType) && webType.equals("client")) {
            //登录成功后才会一起维护
            Jedis jedis = null;
            try {
                jedis = RedisUtil.getJedis();
                String code = jedis.get(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + sid);
                Transaction multi = jedis.multi();
                multi.setex(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sid, timeout, serialize);
                if (StringUtils.isNotBlank(code)) {
                    multi.expire(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + sid, timeout);
                    multi.expire(THINKJOY_OAUTH_CLIENT_SESSION_IDS + "_" + code, timeout);
                }
                multi.exec();
            } catch (Exception e) {
                _log.error("doUpdate error :" + e);
            } finally {
                RedisUtil.returnResource(jedis);
            }
        }

        //每次更新的时候更新request中缓存的session对象
        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null && oauthSession != null) {
            request.setAttribute("session_" + session.getId(), oauthSession);
        }

        _log.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        Jedis jedis = null;
        String sessionId = session.getId().toString();
        String webType = ObjectUtils.toString(session.getAttribute(OauthConstant.WEB_TYPE));

        try {
            jedis = RedisUtil.getJedis();
            if ("client".equals(webType)) {
                // 删除局部会话和同一code注册的局部会话
                String code = jedis.get(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + sessionId);
                jedis.del(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + sessionId);
                jedis.srem(THINKJOY_OAUTH_CLIENT_SESSION_IDS + "_" + code, sessionId);
            }
            if ("server".equals(webType)) {
                // 当前全局会话code
                String token = jedis.get(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sessionId);
                OauthAccessToken oauthAccessToken = SerializableUtil.protostuff_deserialize(token, OauthAccessToken.class);
                String code = oauthAccessToken.getTokenId();
                // 清除全局会话
                jedis.del(THINKJOY_OAUTH_SERVER_SESSION_ID + "_" + sessionId);
                // 清除code校验值
                jedis.del(THINKJOY_OAUTH_SERVER_TOKEN + "_" + code);
                // 清除所有局部会话
                Set<String> clientSessionIds = jedis.smembers(THINKJOY_OAUTH_CLIENT_SESSION_IDS + "_" + code);
                for (String clientSessionId : clientSessionIds) {
                    jedis.del(THINKJOY_OAUTH_CLIENT_SESSION_ID + "_" + clientSessionId);
                    jedis.srem(THINKJOY_OAUTH_CLIENT_SESSION_IDS + "_" + code, clientSessionId);

                    //当登出之后,将client端产生的THINKJOY-UPMS-SHIRO-SESSION-ID也清除
                    jedis.del(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + clientSessionId);
                }
                // 维护会话id列表，提供会话分页管理
                jedis.lrem(THINKJOY_OAUTH_SERVER_SESSION_IDS, 1, sessionId);
            }
            // 删除session
            jedis.del(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sessionId);
        } catch (Exception e) {
            _log.error("doDelete error", e);
        } finally {
            RedisUtil.returnResource(jedis);
        }
        _log.debug("doUpdate >>>>> sessionId={}", sessionId);
    }

    /**
     * 获取会话列表
     *
     * @param offset
     * @param limit
     * @return
     */
    public Map getActiveSessions(int offset, int limit) {
        Map sessions = new HashMap();
        Jedis jedis = RedisUtil.getJedis();
        // 获取在线会话总数
        long total = jedis.llen(THINKJOY_OAUTH_SERVER_SESSION_IDS);
        // 获取当前页会话详情
        List<String> ids = jedis.lrange(THINKJOY_OAUTH_SERVER_SESSION_IDS, offset, (offset + limit - 1));
        List<Session> rows = new ArrayList<>();
        for (String id : ids) {
            String session = RedisUtil.get(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + id);
            // 过滤redis过期session
            if (null == session) {
                RedisUtil.lrem(THINKJOY_OAUTH_SERVER_SESSION_IDS, 1, id);
                total = total - 1;
                continue;
            }
            rows.add(SerializableUtil.deserialize(session));
        }
        jedis.close();
        sessions.put("total", total);
        sessions.put("rows", rows);
        return sessions;
    }

    /**
     * 强制退出
     *
     * @param ids
     * @return
     */
    public int forceout(String ids) {
        String[] sessionIds = ids.split(",");
        for (String sessionId : sessionIds) {
            // 会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
            String session = RedisUtil.get(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sessionId);
            OauthSession oauthSession = (OauthSession) SerializableUtil.deserialize(session);
            oauthSession.setStatus(OauthSession.OnlineStatus.force_logout);
            oauthSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
            RedisUtil.set(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(oauthSession), (int) oauthSession.getTimeout() / 1000);
        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, OauthSession.OnlineStatus onlineStatus) {
        OauthSession session = (OauthSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(THINKJOY_OAUTH_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }

}
