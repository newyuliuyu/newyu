package com.newyu.service.impl;

import com.newyu.domain.commons.SysConfig;
import com.newyu.service.SysConfigService;
import com.newyu.service.dao.SysConfigDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * ClassName: SysAttrServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午2:08 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigDao sysConfigDao;

    @Override
    public Set<SysConfig> querySysConfig() {
        return sysConfigDao.querySysAttrs();
    }
}
