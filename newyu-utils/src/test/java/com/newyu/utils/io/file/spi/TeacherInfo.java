package com.newyu.utils.io.file.spi;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: TeacherInfo <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-22 下午3:13 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class TeacherInfo {
    private String grade1;
    private String grade2;
    private String grade3;
    private boolean change;
}
