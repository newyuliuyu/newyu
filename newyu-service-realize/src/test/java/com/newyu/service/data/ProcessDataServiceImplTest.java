package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.newyu.domain.commons.SysConfig;
import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.dto.ExamDatasource;
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
    private ProcessDataServiceImpl processDataService;
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


    public Exam getExam() {
        ExamDatasource examDatasource = ExamDatasource.builder()
                .examName("考试名字123")
                .examType("其他")
                .gradeName("高一")
                .beginDate(new Date())
                .endDate(new Date())
                .sourceId("test1")
                .build();
        ExamDatasourceConvertExam examDatasourceConvertExam = new ExamDatasourceConvertExam(examService, idGenerator);
        Exam exam = examDatasourceConvertExam.convert(examDatasource);
        return exam;
    }


}