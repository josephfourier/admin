package com.thinkjoy.upms.client.shiro.session;

import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.common.web.ServletUtil;
import com.thinkjoy.upms.client.util.SerializableUtil;
import com.thinkjoy.upms.common.constant.UpmsConstant;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.InvalidSessionException;
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
 * Created by  on 2017/2/23.
 */
public class UpmsSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(UpmsSessionDao.class);
    // 每个系统唯一的会话,包括server和各个client,
    // SecurityUtils.getSubject().getSession() 的值就是其value
    private final static String THINKJOY_UPMS_SHIRO_SESSION_ID = "thinkjoy-upms-shiro-session-id";
    // 每个用户登录server端产生的sessionId
    private final static String THINKJOY_UPMS_SERVER_SESSION_ID = "thinkjoy-upms-server-session-id";
    // 所有serverSessionId的集合
    private final static String THINKJOY_UPMS_SERVER_SESSION_IDS = "thinkjoy-upms-server-session-ids";
    // 用户登录server后的登录凭证,用户用此凭证可以在所有client系统使用
    private final static String THINKJOY_UPMS_SERVER_CODE = "thinkjoy-upms-server-code";
    // client端的sessionId,其后拼的值就是client端产生的THINKJOY_UPMS_SHIRO_SESSION_ID的值
    private final static String THINKJOY_UPMS_CLIENT_SESSION_ID = "thinkjoy-upms-client-session-id";
    // THINKJOY_UPMS_CLIENT_SESSION_IDS_CODE 其中CDOE是一个用户登录后在各个client共用的,这个key的vaue就是
    // 一个用户使用的所有client系统的clientSessionId的集合
    private final static String THINKJOY_UPMS_CLIENT_SESSION_IDS = "thinkjoy-upms-client-session-ids";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        RedisUtil.set(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
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
        String session = RedisUtil.get(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
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
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", cacheUpmsSession.getAttribute("FORCE_LOGOUT"));
        }

        String serialize = SerializableUtil.serialize(session);
        serialize = StringUtils.isBlank(serialize) ? "" : serialize;

        String upmsType = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.upms.type");

        if (StringUtils.isNotBlank(upmsType) && upmsType.equals("server")) {
            // 更新THINKJOY_UPMS_SHIRO_SESSION_ID,THINKJOY_UPMS_SERVER_SESSION_ID、THINKJOY_UPMS_SERVER_CODE过期时间
            //登录成功后才会一起维护
            Jedis jedis = null;

            try {
                jedis = RedisUtil.getJedis();

                String code = jedis.get(THINKJOY_UPMS_SERVER_SESSION_ID + "_" + session.getId());
                Transaction multi = jedis.multi();
                multi.setex(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), (int) session.getTimeout() / 1000, serialize);
                if (StringUtils.isNotBlank(code)) {
                    multi.expire(THINKJOY_UPMS_SERVER_SESSION_ID + "_" + session.getId(), (int) session.getTimeout() / 1000);
                    multi.expire(THINKJOY_UPMS_SERVER_CODE + "_" + code, (int) session.getTimeout() / 1000);
                }
                multi.exec();
            } catch (Exception e) {
                _log.error("doUpdate error :" + e);
            } finally {
                RedisUtil.returnResource(jedis);
            }
        }

        if (StringUtils.isNotBlank(upmsType) && upmsType.equals("client")) {
            //登录成功后才会一起维护
            Jedis jedis = null;
            try {
                jedis = RedisUtil.getJedis();
                String code = jedis.get(THINKJOY_UPMS_CLIENT_SESSION_ID + "_" + session.getId());

                Transaction multi = jedis.multi();
                multi.setex(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), (int) session.getTimeout() / 1000, serialize);
                if (StringUtils.isNotBlank(code)) {
                    multi.expire(THINKJOY_UPMS_CLIENT_SESSION_ID + "_" + session.getId(), (int) session.getTimeout() / 1000);
                    multi.expire(THINKJOY_UPMS_CLIENT_SESSION_IDS + "_" + code, (int) session.getTimeout() / 1000);
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
        if (request != null && upmsSession != null) {
            request.setAttribute("session_" + session.getId(), upmsSession);
        }

        _log.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        Jedis jedis = null;
        String sessionId = session.getId().toString();
        String upmsType = ObjectUtils.toString(session.getAttribute(UpmsConstant.UPMS_TYPE));
        try {
            jedis = RedisUtil.getJedis();
            if ("client".equals(upmsType)) {
                // 删除局部会话和同一code注册的局部会话
                String code = jedis.get(THINKJOY_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
                jedis.del(THINKJOY_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
                jedis.srem(THINKJOY_UPMS_CLIENT_SESSION_IDS + "_" + code, sessionId);
            }
            if ("server".equals(upmsType)) {

                // 当前全局会话code
                String code = jedis.get(THINKJOY_UPMS_SERVER_SESSION_ID + "_" + sessionId);
                // 清除全局会话
                jedis.del(THINKJOY_UPMS_SERVER_SESSION_ID + "_" + sessionId);
                // 清除code校验值
                jedis.del(THINKJOY_UPMS_SERVER_CODE + "_" + code);
                // 清除所有局部会话

                Set<String> clientSessionIds = jedis.smembers(THINKJOY_UPMS_CLIENT_SESSION_IDS + "_" + code);
                for (String clientSessionId : clientSessionIds) {
                    jedis.del(THINKJOY_UPMS_CLIENT_SESSION_ID + "_" + clientSessionId);
                    jedis.srem(THINKJOY_UPMS_CLIENT_SESSION_IDS + "_" + code, clientSessionId);

                    //当登出之后,将client端产生的THINKJOY-UPMS-SHIRO-SESSION-ID也清除
                    jedis.del(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + clientSessionId);
                }

                // 维护会话id列表，提供会话分页管理
                jedis.lrem(THINKJOY_UPMS_SERVER_SESSION_IDS, 1, sessionId);
            }
            // 删除session
            jedis.del(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
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
        long total = jedis.llen(THINKJOY_UPMS_SERVER_SESSION_IDS);
        // 获取当前页会话详情
        List<String> ids = jedis.lrange(THINKJOY_UPMS_SERVER_SESSION_IDS, offset, (offset + limit - 1));
        List<Session> rows = new ArrayList<>();
        for (String id : ids) {
            String session = RedisUtil.get(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + id);
            // 过滤redis过期session
            if (null == session) {
                RedisUtil.lrem(THINKJOY_UPMS_SERVER_SESSION_IDS, 1, id);
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
            String session = RedisUtil.get(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
            UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
            upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
            upmsSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
            RedisUtil.set(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(upmsSession), (int) upmsSession.getTimeout() / 1000);
        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, UpmsSession.OnlineStatus onlineStatus) {
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(THINKJOY_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }

}
