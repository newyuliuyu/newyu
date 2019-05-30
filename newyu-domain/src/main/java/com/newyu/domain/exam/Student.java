package com.newyu.domain.exam;

import com.newyu.domain.org.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * ClassName: Student <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-8 下午5:36 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "wl", "zkzh", "ownId", "subjectCjs", "school", "clazz"})
@Builder
public class Student {
    private String name;
    private String zkzh;
    private String code;
    private String ownId;
    private WLType wl;
    private School school;
    private Clazz clazz;
    private County county;
    private City city;
    private Province province;
    private Map<String, String> extendValueMap;

    public String getOwnId() {
        if (StringUtils.isNotBlank(ownId)) {
            return getCode();
        }
        return ownId;
    }

    public String getCode() {
        if (StringUtils.isNotBlank(code)) {
            return getZkzh();
        }
        return code;
    }
}
