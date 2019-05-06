package com.newyu.service.data;

import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.dto.ExamDatasource;
import com.newyu.domain.dto.SubjectAddItemCj;
import com.newyu.domain.dto.SubjectDatasource;
import com.newyu.domain.exam.Exam;
import com.newyu.service.ExamService;
import com.newyu.utils.id.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: ProcessDataServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-5 下午1:19 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class ProcessDataServiceImpl implements ProcessDataService {

    @Autowired
    private ExamService examService;
    @Autowired
    private IdGenerator idGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExamData(ExamDatasource examDatasource) {
        ExamDatasourceConvertExam examDatasourceConvertExam = new ExamDatasourceConvertExam(examService, idGenerator);
        Exam exam = examDatasourceConvertExam.convert(examDatasource);
        updateBmk(exam.getId(), examDatasource.getBmks());
        updateSubjectCj(exam.getId(), examDatasource.getSubjectDatasources());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExamDataOnlyZf(int scoreBeginColumn, ExamDatasource examDatasource) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBmk(long eamId, List<UploadFile> bmks) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSubjectCj(long examId, List<SubjectDatasource> subjectDatasources) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSubjectItemCj(long subjectId, SubjectAddItemCj subjectAddItemCj) {

    }
}
