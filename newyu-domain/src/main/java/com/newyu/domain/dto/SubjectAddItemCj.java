package com.newyu.domain.dto;

import com.newyu.domain.commons.UploadFile;
import lombok.*;

/**
 * ClassName: SubjectAddItemCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-5 下午1:57 <br/>
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
public class SubjectAddItemCj {
    private long subjectId;
    private String itemName;
    private String itemType;
    private double fullScore;
    private UploadFile itemCjFile;
}
