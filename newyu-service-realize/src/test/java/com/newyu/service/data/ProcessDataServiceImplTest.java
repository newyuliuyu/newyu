package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.newyu.domain.commons.SysConfig;
import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.dto.ExamDatasource;
import com.newyu.domain.dto.SubjectDatasource;
import com.newyu.domain.exam.Exam;
import com.newyu.service.AppConfig;
import com.newyu.service.ExamService;
import com.newyu.service.SysConfigCode;
import com.newyu.service.impl.SysConfigMgr;
import com.newyu.utils.id.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ProcessDataServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-6-3 上午11:09 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ProcessDataServiceImplTest {

    @Autowired
    private ProcessDataService processDataService;
    @Autowired
    ExamService examService;
    @Autowired
    IdGenerator idGenerator;

    @Before
    public void before() {
        SysConfig sysConfig = SysConfig.builder()
                .code(SysConfigCode.uploadDir)
                .name("上传文件保存路径")
                .value("/home/liuyu/test/upload")
                .build();
        SysConfigMgr.newInstance().addConfig(sysConfig);
        sysConfig = SysConfig.builder()
                .code(SysConfigCode.saveDir)
                .name("处理后文件保存路径")
                .value("/home/liuyu/test/save")
                .build();
        SysConfigMgr.newInstance().addConfig(sysConfig);
    }

    @Test
    public void testImportExamData() throws Exception {

        List<UploadFile> bmks = Lists.newArrayList();
        bmks.add(UploadFile.builder()
                .newFile("bmk.xls")
                .build());

        List<SubjectDatasource> subjectDatasources = Lists.newArrayList();
        subjectDatasources.add(SubjectDatasource.builder()
                .subjectName("语文")
                .fullScore(0)
                .xmb(UploadFile.builder().newFile("语文系目表.xlsx").build())
                .cj(UploadFile.builder().newFile("语文成绩.xls").build())
                .build());
        subjectDatasources.add(SubjectDatasource.builder()
                .subjectName("数学")
                .fullScore(0)
                .xmb(UploadFile.builder().newFile("数学系目表.xlsx").build())
                .cj(UploadFile.builder().newFile("数学成绩.xls").build())
                .build());
        subjectDatasources.add(SubjectDatasource.builder()
                .subjectName("英语")
                .fullScore(0)
                .xmb(UploadFile.builder().newFile("英语系目表.xlsx").build())
                .cj(UploadFile.builder().newFile("英语成绩.xls").build())
                .build());
        subjectDatasources.add(SubjectDatasource.builder()
                .subjectName("社会")
                .fullScore(0)
                .xmb(UploadFile.builder().newFile("社会系目表.xlsx").build())
                .cj(UploadFile.builder().newFile("社会成绩.xls").build())
                .build());
        subjectDatasources.add(SubjectDatasource.builder()
                .subjectName("科学")
                .fullScore(0)
                .xmb(UploadFile.builder().newFile("科学系目表.xlsx").build())
                .cj(UploadFile.builder().newFile("科学成绩.xls").build())
                .build());

        ExamDatasource examDatasource = ExamDatasource.builder()
                .examName("测试考试")
                .examType("其他")
                .gradeName("高一")
                .beginDate(new Date())
                .endDate(new Date())
                .sourceId("test2")
                .bmks(bmks)
                .subjectDatasources(subjectDatasources)
                .build();

        processDataService.importExamData(examDatasource);

    }


    @Test
    public void importBmk() throws Exception {
        Exam exam = getExam();
        List<UploadFile> bmks = Lists.newArrayList();
        bmks.add(UploadFile.builder()
                .newFile("测试报名库.xlsx")
                .build());
        processDataService.updateBmk(exam, bmks);
    }

    @Test
    public void importBmkNotOtherData() throws Exception {
        Exam exam = getExam();
        List<UploadFile> bmks = Lists.newArrayList();
        bmks.add(UploadFile.builder()
                .newFile("没有其他数据.xlsx")
                .build());
        processDataService.updateBmk(exam, bmks);
    }

    @Test
    public void importCj() throws Exception {
        Exam exam = getExam();
        List<SubjectDatasource> subjectDatasources = Lists.newArrayList();
        subjectDatasources.add(SubjectDatasource.builder()
                .subjectName("语文")
                .fullScore(0)
                .xmb(UploadFile.builder().newFile("语文系目表.xlsx").build())
                .cj(UploadFile.builder().newFile("语文成绩.xls").build())
                .build());
        processDataService.updateSubjectCj(exam, subjectDatasources);

    }


    public Exam getExam() {
        ExamDatasource examDatasource = ExamDatasource.builder()
                .examName("测试考试")
                .examType("其他")
                .gradeName("高一")
                .beginDate(new Date())
                .endDate(new Date())
                .sourceId("test2")
                .build();
        ExamDatasourceConvertExam examDatasourceConvertExam = new ExamDatasourceConvertExam(examService, idGenerator);
        Exam exam = examDatasourceConvertExam.convert(examDatasource);
        return exam;
    }


}