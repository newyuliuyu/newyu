package com.newyu.domain.commons;

import lombok.*;

/**
 * ClassName: SysConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-30 上午10:42 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"code"})
public class SysConfig {
    private String code;
    private String name;
    private String value;

}
