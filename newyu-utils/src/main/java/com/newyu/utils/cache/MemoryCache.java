package com.newyu.utils.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: MemoryCache <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-17 上午9:26 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class MemoryCache {
    private static MemoryCache cache = new MemoryCache();
    private ConcurrentHashMap<String, Object> cacheData = new ConcurrentHashMap<>();

    public static MemoryCache instance() {
        return cache;
    }

    public void put(String key, Object value) {
        cacheData.put(key, value);
    }

    public void remove(String key) {
        cacheData.remove(key);
    }

    public <T> T getValue(String key) {
        return (T) cacheData.get(key);
    }

    public Set<String> keys() {
        return cacheData.keySet();
    }

}
