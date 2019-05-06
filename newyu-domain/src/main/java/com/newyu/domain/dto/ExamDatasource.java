package com.newyu.domain.dto;

import com.newyu.domain.commons.UploadFile;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ExamDatasource <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-5 下午1:33 <br/>
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
public class ExamDatasource {
    private String examName;
    private String gradeName;
    private String sourceId;
    private String examType;
    private Date beginDate;
    private Date endDate;
    private List<UploadFile> bmks;
    private List<SubjectDatasource> subjectDatasources;

}
