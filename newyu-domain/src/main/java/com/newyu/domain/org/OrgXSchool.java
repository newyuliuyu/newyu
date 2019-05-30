package com.newyu.domain.org;

import lombok.*;

/**
 * ClassName: OrgXSchool <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-16 上午10:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"province", "city", "county", "school"})
@ToString(of = {"province", "city", "county", "school"})
@Builder
public class OrgXSchool {
    private Province province;
    private City city;
    private County county;
    private School school;
}
