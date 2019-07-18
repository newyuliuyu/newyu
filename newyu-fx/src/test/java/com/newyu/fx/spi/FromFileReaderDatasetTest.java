package com.newyu.fx.spi;

import com.google.common.collect.Sets;
import com.newyu.domain.exam.Student;
import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.fx.GroupInfo;
import com.newyu.fx.AppConfig;
import com.newyu.fx.Dataset;
import com.newyu.fx.FxContext;
import com.newyu.fx.GroupDataset;
import com.newyu.service.ExamService;
import com.newyu.service.ExamXSubjectXItemService;
import com.newyu.service.FxParamService;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ClassName: FromFileReaderDatasetTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-6-14 上午11:08 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FromFileReaderDatasetTest {

    @Autowired
    private ExamService examService;
    @Autowired
    private ExamXSubjectXItemService examXSubjectXItemService;
    @Autowired
    private FxParamService fxParamService;

    private FxContext context;

    @Before
    public void init() throws Exception {
        context = DefaultFxContext.builder()
                .examId(1139341162646257664L)
                .examService(examService)
                .examXSubjectXItemService(examXSubjectXItemService)
                .fxParamService(fxParamService)
                .build();
    }

    @Test
    public void readdata() throws Exception {
        FromFileReaderDataset reader = FromFileReaderDataset.newInstance("/home/liuyu/test/save/" + context.getExamBaseInfoMgr().getExam().getId(), Sets.newHashSet());
        Dataset<StudentCj> dataset = reader.read(context);
        System.out.println();

        StudentCj studentCj = dataset.getList().get(0);

        Object value = PropertyUtils.getProperty(studentCj, "student.wl");
        Object value1 = PropertyUtils.getProperty(studentCj, "subjectCjs.语文.teachClazz");

        Field[]  kk = StudentCj.class.getFields();
        Field[]  kk1 = Student.class.getDeclaredFields();

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.add("clazz");

        List<GroupDataset<StudentCj>> studentCjs = dataset.getGroupDataset(groupInfo);

        System.out.println();

    }

}