package com.newyu.service.dao;

import com.newyu.domain.commons.SysConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * ClassName: SysAttrDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午2:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface SysConfigDao {
    int create(@Param("sysConfig") SysConfig sysConfig);

    int update(@Param("sysConfig") SysConfig sysConfig);

    Set<SysConfig> querySysAttrs();
}
