package com.newyu.utils.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ClassName: ShiroRedisCacheManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 17-11-6 下午2:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ShiroRedisCacheManager implements CacheManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();
    private JedisPool jedisPool;


    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的的ShiroRedisCache实例");
        Cache c = caches.get(name);

        if (c == null) {
            c = new ShiroRedisCache<K, V>(jedisPool, name);
            caches.put(name, c);
        }
        return c;
    }
}
