package com.newyu.service.impl;

import com.google.common.collect.Maps;
import com.newyu.domain.commons.SysConfig;
import com.newyu.service.SysConfigService;
import com.newyu.utils.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * ClassName: SysAttrMgr <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午2:15 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class SysConfigMgr {
    private static SysConfigMgr that = new SysConfigMgr();
    private Map<String, SysConfig> sysConfigMap = Maps.newHashMap();
    private Lock lock = new ReentrantLock();

    private void load() {
        SysConfigService sysAttrService = SpringContextUtil.getBean(SysConfigService.class);
        Set<SysConfig> sysConfigs = sysAttrService.querySysConfig();
        sysConfigMap = sysConfigs.stream().collect(Collectors.toMap(x -> x.getCode(), x -> x));
    }

    public void reload() {
        lock.lock();
        try {
            load();
        } finally {
            lock.unlock();
        }
    }

    public SysConfig get(String sysAttrCode) {
        lock.lock();
        try {
            if (sysConfigMap.isEmpty()) {
                load();
            }
        } finally {
            lock.unlock();
        }
        return sysConfigMap.get(sysAttrCode);
    }

    public void addConfig(SysConfig sysConfig) {
        sysConfigMap.put(sysConfig.getCode(), sysConfig);
    }

    public static SysConfigMgr newInstance() {

        return that;
    }
}
