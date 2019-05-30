package com.newyu.service.data;

import com.google.common.collect.Sets;
import com.newyu.domain.org.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: OrgMgr <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-8 下午6:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
public class OrgMgr {
    private Map<String, School> schoolMap = new HashMap<>(16);
    private Map<String, Clazz> clazzMap = new HashMap<>(16);
    private Map<String, City> cityMap = new HashMap<>(16);
    private Map<String, County> countyMap = new HashMap<>(16);
    private Map<String, Province> provinceMap = new HashMap<>(32);
    private Set<OrgXSchool> orgXSchools = Sets.newHashSet();
}
