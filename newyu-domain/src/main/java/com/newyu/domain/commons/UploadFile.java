package com.newyu.domain.commons;

import lombok.*;

/**
 * ClassName: UploadFile <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-5 下午1:31 <br/>
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
public class UploadFile {
    private String newFile;
    private String oldFile;
}
