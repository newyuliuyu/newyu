package com.newyu.domain.dto;

import com.newyu.domain.commons.UploadFile;
import lombok.*;

/**
 * ClassName: SubjectDatasource <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-5 下午1:38 <br/>
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
public class SubjectDatasource {
    private String subjectName;
    private double fullScore;
    private UploadFile xmb;
    private UploadFile cj;
    /**
     * 学生图像
     */
    private UploadFile studentImgs;
    /**
     * 切割方案
     */
    private UploadFile cuttingPlan;
}
