package com.newyu.service.data;

import com.newyu.domain.dto.ExamDatasource;
import com.newyu.domain.exam.*;
import com.newyu.service.ExamService;
import com.newyu.utils.id.IdGenerator;
import com.newyu.utils.tool.GradeInfo;
import com.newyu.utils.tool.GradeNameOrderHelper;

import java.util.Date;


/**
 * ClassName: CreateExam <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 上午11:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ExamDatasourceConvertExam {

    private ExamService examService;
    private IdGenerator idGenerator;

    public ExamDatasourceConvertExam(ExamService examService, IdGenerator idGenerator) {
        this.examService = examService;
        this.idGenerator = idGenerator;
    }

    public Exam convert(ExamDatasource examDatasource) {

        Exam result = getExam(examDatasource);
        if (result == null) {
            GradeInfo gradeInfo = GradeNameOrderHelper.getGradeInfo(examDatasource.getGradeName());
            result = Exam.builder()
                    .id(idGenerator.nextId())
                    .name(examDatasource.getExamName())
                    .state(ExamState.create)
                    .sourceId(examDatasource.getSourceId())
                    .gradeName(toGradeName(gradeInfo.getGradeName()))
                    .entranceSchoolYear(gradeInfo.getEntranceYear())
                    .semester(toSegment(gradeInfo.getSemester()))
                    .learningSegment(toLearningSegment(gradeInfo.getStudySetion()))
                    .examLevel(ExamLevel.School_Exam)
                    .examType(examDatasource.getExamType())
                    .beginDate(examDatasource.getBeginDate())
                    .endDate(examDatasource.getEndDate())
                    .createDate(new Date())
                    .build();
            examService.createExam(result);
        }
        return result;
    }


    private GradeName toGradeName(String gradeName) {
        GradeName[] gradeNames = GradeName.values();
        for (GradeName the : gradeNames) {
            if (the.getName().equalsIgnoreCase(gradeName)) {
                return the;
            }
        }
        return null;
    }

    private Semester toSegment(int semester) {
        Semester[] semesters = Semester.values();
        for (Semester the : semesters) {
            if (the.getCode() == semester) {
                return the;
            }
        }
        return null;
    }

    private LearningSegment toLearningSegment(int learningSegment) {
        LearningSegment[] learningSegments = LearningSegment.values();
        for (LearningSegment the : learningSegments) {
            if (the.getCode() == learningSegment) {
                return the;
            }
        }
        return null;
    }


    private Exam getExam(ExamDatasource examDatasource) {
        return examService.getExamFromSourceId(examDatasource.getSourceId());
    }

}
