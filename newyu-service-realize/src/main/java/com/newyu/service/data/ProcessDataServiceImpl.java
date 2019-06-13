package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.newyu.domain.commons.ImportFiled;
import com.newyu.domain.commons.SysConfig;
import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.dto.ExamDatasource;
import com.newyu.domain.dto.SubjectDatasource;
import com.newyu.domain.exam.*;
import com.newyu.domain.fx.SubjectDataVersion;
import com.newyu.service.*;
import com.newyu.service.dao.ImportFiledDao;
import com.newyu.service.dao.SubjectDataVersionDao;
import com.newyu.service.impl.SysConfigMgr;
import com.newyu.utils.id.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private SubjectService subjectService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private ExamXSubjectXItemService examXSubjectXItemService;
    @Autowired
    private StudentExtendFieldService studentExtendFieldService;
    @Autowired
    private ExamOrgService examOrgService;
    @Autowired
    private ImportFiledDao importFiledDao;
    @Autowired
    private SubjectDataVersionDao subjectDataVersionDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExamData(ExamDatasource examDatasource) {
        ExamDatasourceConvertExam examDatasourceConvertExam = new ExamDatasourceConvertExam(examService, idGenerator);
        Exam exam = examDatasourceConvertExam.convert(examDatasource);
        updateBmk(exam, examDatasource.getBmks());
        updateSubjectCj(exam, examDatasource.getSubjectDatasources());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Subject> importExamDataOnlyZf(ExamDatasource examDatasource) {
        ExamDatasourceConvertExam examDatasourceConvertExam = new ExamDatasourceConvertExam(examService, idGenerator);
        Exam exam = examDatasourceConvertExam.convert(examDatasource);
        updateBmk(exam, examDatasource.getBmks());

        List<Subject> subjects = saveSubjects(exam, examDatasource.getSubjectDatasources());
        ProcessOnlyZFCj processOnlyZFCj = new ProcessOnlyZFCj(exam, subjects, examDatasource.getBmks().get(0), getSaveFileDir());
        processOnlyZFCj.proces();
        return subjects;
    }

    private List<Subject> saveSubjects(Exam exam, List<SubjectDatasource> subjectDatasources) {
        List<Subject> subjects = Lists.newArrayList();
        for (SubjectDatasource subjectDatasource : subjectDatasources) {
            Subject subject =subjectDatasource.toSubject();
            subjects.add(subject);
            subject.setExamId(exam.getId());
            subject.setId(idGenerator.nextId());
            subjectService.createSubject(subject);
            udpateSubjectDataVerion(subject);
        }
        return subjects;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBmk(Exam exam, List<UploadFile> bmks) {
        processUploadFile(bmks);
        ProcessBmk processBmk = new ProcessBmk(exam, bmks, getSaveFileDir(), getStudentExtendFields());
        processBmk.process();
        setExamLevelAndWL(exam.getId(), processBmk.getWL(), processBmk.getExamLevel());
        saveExamOrg(exam.getId(), processBmk.getOrgMgr());
        saveBmkFiled(exam.getId(), processBmk.getImportFiled());
    }

    private void setExamLevelAndWL(long examId, int wl, ExamLevel examLevel) {
        Exam exam = Exam.builder().id(examId).wl(wl).examLevel(examLevel).build();
        examService.updateExamWLAndLevel(exam);
    }

    private void saveExamOrg(long examId, OrgMgr orgMgr) {
        examOrgService.deleteProvice(examId);
        examOrgService.deleteCity(examId);
        examOrgService.deleteCounty(examId);
        examOrgService.deleteSchool(examId);
        examOrgService.deleteClazz(examId);
        examOrgService.deleteOrgXSchool(examId);

        if (!orgMgr.getProvinceMap().isEmpty()) {
            examOrgService.saveProvince(examId, Lists.newArrayList(orgMgr.getProvinceMap().values()));
        }
        if (!orgMgr.getCityMap().isEmpty()) {
            examOrgService.saveCity(examId, Lists.newArrayList(orgMgr.getCityMap().values()));
        }
        if (!orgMgr.getCountyMap().isEmpty()) {
            examOrgService.saveCounty(examId, Lists.newArrayList(orgMgr.getCountyMap().values()));
        }
        if (!orgMgr.getSchoolMap().isEmpty()) {
            examOrgService.saveSchool(examId, Lists.newArrayList(orgMgr.getSchoolMap().values()));
        }
        if (!orgMgr.getClazzMap().isEmpty()) {
            examOrgService.saveClazz(examId, Lists.newArrayList(orgMgr.getClazzMap().values()));
        }
        if (orgMgr.getOrgXSchools().size() > 1) {
            examOrgService.saveOrgXSchool(examId, Lists.newArrayList(orgMgr.getOrgXSchools()));
        }
    }

    private void saveBmkFiled(long examId, List<ImportFiled> importFileds) {
        importFiledDao.deleteFiled(examId, 0L);
        importFiledDao.saveFiled(examId, importFileds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSubjectCj(Exam exam, List<SubjectDatasource> subjectDatasources) {
        for (SubjectDatasource subjectDatasource : subjectDatasources) {
            updateSubjectCj(exam, subjectDatasource);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSubjectCj(Exam exam, SubjectDatasource subjectDatasource) {
        SubjectDatasourceConvertSubject convertSubject = new SubjectDatasourceConvertSubject(examXSubjectXItemService, subjectService, idGenerator);
        Subject subject = convertSubject.convert(exam.getId(), subjectDatasource);
        updateSubjectXmb(exam, subject, subjectDatasource);
        updateSubjectCj(exam, subject, subjectDatasource);
    }

    private void updateSubjectXmb(Exam exam, Subject subject, SubjectDatasource subjectDatasource) {
        if (subjectDatasource.getXmb() == null) {
            return;
        }
        ProcessItem processItem = new ProcessItem(idGenerator, exam, subject, processUploadFile(subjectDatasource.getXmb()), getSaveFileDir());
        processItem.process();
        updateSubjectCj(subject);
    }

    private void updateSubjectCj(Exam exam, Subject subject, SubjectDatasource subjectDatasource) {
        ProcessCj processCj = new ProcessCj(exam, subject, processUploadFile(subjectDatasource.getCj()), getSaveFileDir());
        processCj.proces();
        udpateSubjectDataVerion(subject);
        saveCjFiled(exam.getId(), subject.getId(), processCj.getImportFileds());
    }

    private void updateSubjectCj(Subject subject) {
        subjectService.updateSubjectScore(subject);
        subjectService.deleteSubjectItem(subject);
        itemService.createItems(subject.getItems());
    }

    private void udpateSubjectDataVerion(Subject subject) {
        SubjectDataVersion version = subjectDataVersionDao.get(subject.getId());
        if (version == null) {
            version = SubjectDataVersion.builder().examId(subject.getExamId())
                    .subjectId(subject.getId())
                    .previousVesrion(0)
                    .curVesrion(1)
                    .build();
            subjectDataVersionDao.createSubjectDataVersion(version);
        } else {
            version.setCurVesrion(version.getCurVesrion() + 1);
            subjectDataVersionDao.update(version);
        }
    }

    private void saveCjFiled(long examId, long subjectId, List<ImportFiled> importFileds) {
        importFiledDao.deleteFiled(examId, subjectId);
        if (importFileds.size() > 0) {
            importFiledDao.saveFiled(examId, importFileds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSubjectItemCj(Subject subject, SubjectDatasource subjectDatasource) {
        Exam exam = examService.getExam(subject.getExamId());
        processUploadFile(subjectDatasource.getXmb());
        processUploadFile(subjectDatasource.getCj());
        ProcessAddItemCj processAddItemCj = new ProcessAddItemCj(idGenerator, exam, subject, subjectDatasource, getSaveFileDir());
        processAddItemCj.proces();
        List<Item> newItems = processAddItemCj.getItems();
        subject.getItems().addAll(newItems);
        Subject.calcaluteSubjectScore(subject);
        updateSubjectCj(subject);
    }

    private Set<StudentExtendField> getStudentExtendFields() {
        return studentExtendFieldService.getStudentExtendFields();
    }

    private String getSaveFileDir() {
        SysConfig sysConfig = SysConfigMgr.newInstance().get(SysConfigCode.saveDir);
        if (sysConfig == null || StringUtils.isBlank(sysConfig.getValue())) {
            throw new RuntimeException("保存报名库和成绩的文件的目录地址没有设置");
        }
        return sysConfig.getValue();
    }

    private void processUploadFile(List<UploadFile> uploadFiles) {
        Optional.ofNullable(uploadFiles).orElse(Lists.newArrayList()).stream().forEach(this::processUploadFile);
    }

    private UploadFile processUploadFile(UploadFile uploadFile) {
        SysConfig sysConfig = SysConfigMgr.newInstance().get(SysConfigCode.uploadDir);
        if (sysConfig == null || StringUtils.isBlank(sysConfig.getValue())) {
            throw new RuntimeException("上传文件的目录地址没有设置");
        }
        Path path = Paths.get(sysConfig.getValue(), uploadFile.getNewFile());
        uploadFile.setNewFile(path.toString());
        return uploadFile;
    }
}
