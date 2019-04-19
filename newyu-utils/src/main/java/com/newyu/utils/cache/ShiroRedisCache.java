package com.newyu.utils.cache;

import com.google.common.collect.Lists;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * ClassName: ShiroRedisCache <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 17-11-6 下午2:17 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JedisPool jedisPool;
    private String name;

    private long timeout = 0;

    public ShiroRedisCache(JedisPool jedisPool, String name) {
        this.jedisPool = jedisPool;
        this.name = name + ":";
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }


    private void setCacheTimeout(Jedis jedis, byte[] key) {
        if (timeout > 0) {
            jedis.pexpire(key, timeout);
        }
    }

    private byte[] getKeyByte(K key) {
        if (key instanceof String) {
            return (name + key).toString().getBytes();
        } else {
            return SerializationUtils.serialize(key);
        }
    }

    @Override
    public V get(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        byte[] keyByte = getKeyByte(key);
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] value = jedis.get(keyByte);
            return (V) SerializationUtils.deserialize(value);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        byte[] keyByte = getKeyByte(key);
        byte[] valueByte = SerializationUtils.serialize(value);
        Jedis jedis = jedisPool.getResource();
        try {
            V oldValueByte = get(key);
            jedis.set(keyByte, valueByte);
//            setCacheTimeout(jedis, keyByte);
            return oldValueByte;
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        Jedis jedis = jedisPool.getResource();
        try {
            V oldValueByte = get(key);
            jedis.del(getKeyByte(key));
            return oldValueByte;
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void clear() throws CacheException {
        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.keys(name + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                for (String key : keys) {
                    jedis.del(key);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public int size() {
        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.keys(name + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return 0;
            } else {
                return keys.size();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Set<K> keys() {
        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.keys(name + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                int lenght = name.length();
                Set<K> newKeys = new HashSet<K>();
                for (String key : keys) {
                    key = key.substring(lenght);
                    newKeys.add((K) key);
                }
                return newKeys;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Collection<V> values() {
        List<V> values = Lists.newArrayList();
        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.keys(name + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                int lenght = name.length();
                for (String key : keys) {
                    key = key.substring(lenght);
                    values.add(get((K) key));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return values;
    }
}
