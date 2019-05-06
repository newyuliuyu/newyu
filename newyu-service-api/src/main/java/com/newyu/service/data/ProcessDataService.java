package com.newyu.service.data;

import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.dto.ExamDatasource;
import com.newyu.domain.dto.SubjectAddItemCj;
import com.newyu.domain.dto.SubjectDatasource;

import java.util.List;

/**
 * ClassName: ProcessDataService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-5 上午11:15 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ProcessDataService {

    void importExamData(ExamDatasource examDatasource);

    void importExamDataOnlyZf(int scoreBeginColumn, ExamDatasource examDatasource);

    void updateBmk(long eamId, List<UploadFile> bmks);

    void updateSubjectCj(long examId, List<SubjectDatasource> subjectDatasources);

    void addSubjectItemCj(long subjectId, SubjectAddItemCj subjectAddItemCj);
}
