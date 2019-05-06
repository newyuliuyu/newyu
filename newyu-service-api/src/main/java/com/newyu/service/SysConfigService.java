package com.newyu.service;

import com.newyu.domain.commons.SysConfig;

import java.util.Set;

/**
 * ClassName: SysAttrService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午2:07 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface SysConfigService {
    /**
     * 获取系统属性值
     *
     * @return
     */
    Set<SysConfig> querySysConfig();
}
