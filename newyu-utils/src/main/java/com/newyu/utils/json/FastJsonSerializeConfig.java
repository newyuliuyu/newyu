package com.newyu.utils.json;

import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.Map;

/**
 * ClassName: FastJsonSerializeConfig <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 17-12-12 下午4:09 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class FastJsonSerializeConfig {
    public static void setSerializeConfig(SerializeConfig config, Map<String, Object> typeMap) {
        try {
            for (String type : typeMap.keySet()) {
                Class myType = getType(type);
                config.put(myType, typeMap.get(type));
            }
        } catch (Exception e) {
            throw new RuntimeException("创建对象失败", e);
        }
    }

    private static Class getType(String type) {
        if ("byte".equals(type)) {
            return byte.class;
        } else if ("short".equals(type)) {
            return short.class;
        } else if ("int".equals(type)) {
            return int.class;
        } else if ("long".equals(type)) {
            return long.class;
        } else if ("float".equals(type)) {
            return float.class;
        } else if ("double".equals(type)) {
            return double.class;
        } else if ("boolean".equals(type)) {
            return boolean.class;
        } else if ("char".equals(type)) {
            return char.class;
        } else if ("Object[]".equals(type)) {
            return Object[].class;
        } else if ("byte".equals(type)) {
            return byte[].class;
        } else if ("short".equals(type)) {
            return short[].class;
        } else if ("int".equals(type)) {
            return int[].class;
        } else if ("long".equals(type)) {
            return long[].class;
        } else if ("float".equals(type)) {
            return float[].class;
        } else if ("double".equals(type)) {
            return double[].class;
        } else if ("boolean".equals(type)) {
            return boolean[].class;
        } else if ("char".equals(type)) {
            return char[].class;
        } else {
            try {
                return Class.forName(type);
            } catch (Exception e) {
                throw new RuntimeException("创建对象失败", e);
            }
        }
    }
}
