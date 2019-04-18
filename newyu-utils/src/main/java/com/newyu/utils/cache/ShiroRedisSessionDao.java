package com.newyu.utils.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

/**
 * ClassName: ShiroRedisSessionDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 17-11-6 下午1:41 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ShiroRedisSessionDao extends CachingSessionDAO {
    private JedisPool jedisPool;
    private long timeout = 0;

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    protected void doUpdate(Session session) {

    }

    @Override
    protected void doDelete(Session session) {

    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return null;
    }


    @Override
    protected Cache<Serializable, Session> createActiveSessionsCache() {
        Cache<Serializable, Session> cache = super.createActiveSessionsCache();
        if (timeout != 0 && (cache instanceof ShiroRedisCache)) {
            ShiroRedisCache tmp = (ShiroRedisCache) cache;
            tmp.setTimeout(timeout);
        }

        return cache;
    }
}