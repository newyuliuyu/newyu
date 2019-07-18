package com.newyu.fx.spi;

import com.google.common.base.Preconditions;
import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.exam.Subject;
import com.newyu.domain.fx.SubjectDataVersion;
import com.newyu.fx.ExamBaseInfoMgr;
import com.newyu.fx.FxContext;
import com.newyu.fx.SubjectCjConversion;
import com.newyu.service.ExamService;
import com.newyu.service.ExamXSubjectXItemService;
import com.newyu.service.FxParamService;
import com.newyu.service.SubjectService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Predicate;

/**
 * ClassName: FxContextImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 下午1:36 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class DefaultFxContext implements FxContext {

    private ExamService examService;
    private ExamXSubjectXItemService examXSubjectXItemService;
    private FxParamService fxParamService;

    private long examId = -1;

    private ExamBaseInfoMgr examBaseInfoMgr = new ExamBaseInfoMgr();


    public static DefaultFxContextBuilder builder() {
        return new DefaultFxContextBuilder();
    }

    public static class DefaultFxContextBuilder {
        DefaultFxContext context = new DefaultFxContext();

        public DefaultFxContextBuilder examId(long examId) {
            context.examId = examId;
            return this;
        }

        public DefaultFxContextBuilder examXSubjectXItemService(ExamXSubjectXItemService examXSubjectXItemService) {
            context.examXSubjectXItemService = examXSubjectXItemService;
            return this;
        }

        public DefaultFxContextBuilder examService(ExamService examService) {
            context.examService = examService;
            return this;
        }

        public DefaultFxContextBuilder fxParamService(FxParamService fxParamService) {
            context.fxParamService = fxParamService;
            return this;
        }

        public DefaultFxContext build() {
            context.init();
            return context;
        }
    }

    public DefaultFxContext init() {
        prepareCheck();
        exec();
        return this;
    }

    private void prepareCheck() {
        log.debug("数据准备验证。。。");
        Preconditions.checkNotNull(examService, "考试服务类不能为null");
        Preconditions.checkNotNull(examXSubjectXItemService, "考试科目题目服务类不能为null");
        Preconditions.checkNotNull(fxParamService, "分析参数务类不能为null");
        Preconditions.checkArgument(examId > -1, "考试ID必须大于0");
    }

    private void exec() {
        setExam();
        setSubjects();
        setSubjectDataVersions();
    }

    private void setExam() {
        log.debug("加载考试信息");
        examBaseInfoMgr.setExam(examService.getExam(examId));
    }

    private void setSubjects() {
        log.debug("加载考试科目信息");
        List<Subject> subjects = examXSubjectXItemService.querySubjectOfExam(examId);
        SubjectService.setChildSubject(subjects);
        examBaseInfoMgr.setSubjects(subjects);
    }

    private void setSubjectDataVersions() {
        log.debug("加载科目数据版本信息");
        List<SubjectDataVersion> subjectDataVersions = examXSubjectXItemService.querySubjectDataVersion(examId);
        examBaseInfoMgr.setSubjectDataVersions(subjectDataVersions);
    }


    @Override
    public ExamBaseInfoMgr getExamBaseInfoMgr() {
        return examBaseInfoMgr;
    }

    @Override
    public SubjectCjConversion getSubjectCjConversions(Subject subject) {
        return null;
    }

    @Override
    public Predicate<StudentCj> getPredicateOfStudent() {
        return null;
    }

    @Override
    public Predicate<StudentCj> getPredicateOfSubjectCj(Subject subject) {
        return null;
    }


}
