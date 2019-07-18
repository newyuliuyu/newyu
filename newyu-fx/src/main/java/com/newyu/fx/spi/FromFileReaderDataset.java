package com.newyu.fx.spi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.exam.*;
import com.newyu.domain.org.*;
import com.newyu.fx.Dataset;
import com.newyu.fx.FxContext;
import com.newyu.fx.ReaderDataset;
import com.newyu.service.data.FromRowDataReader;
import com.newyu.service.data.OrgMgr;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.FileProcessUtil;
import com.newyu.utils.io.file.Rowdata;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: FromFileReaderDataset <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 下午4:02 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class FromFileReaderDataset implements ReaderDataset {

    private DatasetImpl dataset = new DatasetImpl();

    private FxContext context;

    private Path fileDirPath;

    private Set<StudentExtendField> studentExtendFields;

    public static FromFileReaderDataset newInstance(String fileDirPath, Set<StudentExtendField> extendFields) {
        return newInstance(Paths.get(fileDirPath), extendFields);
    }

    public static FromFileReaderDataset newInstance(Path fileDirPath, Set<StudentExtendField> studentExtendFields) {
        FromFileReaderDataset result = new FromFileReaderDataset();
        result.fileDirPath = fileDirPath;
        result.studentExtendFields = studentExtendFields;
        return result;
    }


    @Override
    public Dataset read(FxContext context) {
        this.context = context;
        readBmk();
        readCj();
        return dataset;
    }

    private void readBmk() {
        log.debug("加载报名库数据从[{}/bmk.csv]", fileDirPath);
        OrgMgr orgMgr = new OrgMgr();
        FileProcess fileProcess = FileProcessUtil.getFileProcess(fileDirPath.resolve("bmk.csv"));
        try {
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                StudentCj studentCj = getStudentCj(rowdata, orgMgr);
                dataset.add(studentCj);
            }
        } finally {
            fileProcess.close();
        }
    }

    private StudentCj getStudentCj(Rowdata rowdata, OrgMgr orgMgr) {
        Student student = FromRowDataReader.getStudent(rowdata, studentExtendFields, orgMgr);
        StudentCj studentCj = StudentCj.builder()
                .student(student)
                .build();
        return studentCj;
    }


    private void readCj() {
        List<Subject> subjects = context.getExamBaseInfoMgr().getSubjects();
        for (Subject subject : subjects) {
            if (subject.isExamSubject()) {
                loadSubjectCj(subject);
            }
        }
    }

    private void loadSubjectCj(Subject subject) {
        log.debug("加载科目成绩从[{}/{}cj.csv]", fileDirPath, subject.getName());
        FileProcess fileProcess = FileProcessUtil.getFileProcess(fileDirPath.resolve(subject.getName() + "cj.csv"));
        Map<String, TeachClazz> teachClazzMap = Maps.newHashMap();
        try {
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                String zkzh = rowdata.getData("zkzh");
                Optional<StudentCj> studentCj = dataset.get(zkzh);
                if (!studentCj.isPresent()) {
                    String msg = MessageFormat.format("[{0}]科目成绩准考证为[{1}]的学生没有在报名库中找到信息", subject, zkzh);
                    log.warn(msg);
                    continue;
                }

                TeachClazz teachClazz = FromRowDataReader.getTeachClazz(subject.getName(), studentCj.get().getStudent().getSchool(), rowdata, teachClazzMap);
                SubjectCj subjectCj = getSubjectCj(subject, rowdata);
                studentCj.get().addSubjectCj(subjectCj);
                subjectCj.setTeachClazz(teachClazz);
                if (!subject.getItems().isEmpty()) {
                    try {
                        List<ItemCj> itemCjs = getItemCj(subject, rowdata);
                        subjectCj.setItemCjs(itemCjs);
                        setItemCjChoiced(subject, subjectCj);
                    } catch (Exception e) {
                        String msg = MessageFormat.format("学生[{0}]获取小题成绩出错", studentCj.get());
                        throw new RuntimeException(msg, e);
                    }
                }
            }
        } finally {
            fileProcess.close();
        }
    }

    private void setItemCjChoiced(Subject subject, SubjectCj subjectCj) {
        List<ChoiceItemGroup> choiceItemGroups = subject.queryChoiceItemGroups();
        choiceItemGroups.forEach(x -> {
            List<Item> items = x.getItems();
            List<ItemCj> itemCjs = items.stream().map(x1 -> subjectCj.queryItemCj(x1.getName())).collect(Collectors.toList());
            itemCjs.sort((v1, v2) -> Double.compare(v1.getScore(), v2.getScore()));
            int num = itemCjs.size() - x.getChoiceNum();
            for (int i = 0; i < num; i++) {
                itemCjs.get(i).setChoiced(false);
            }
        });
    }

    private List<ItemCj> getItemCj(Subject subject, Rowdata rowdata) {
        List<ItemCj> itemCjs = Lists.newArrayList();
        List<Item> items = subject.getItems();
        for (Item item : items) {
            String strScore = rowdata.getData(item.getName());
            double score = 0;
            try {
                score = Double.parseDouble(strScore);
            } catch (Exception e) {
                String msg = MessageFormat.format("科目[{0}]从文件获取小题[{1}]的值[{2}]转换成double出错", subject, item, strScore);
                throw new RuntimeException("", e);
            }
            String selected = "";
            if (!item.getItemType().equals(ItemType.Not_Select)) {
                selected = rowdata.getData(item.getName() + "选项");
            }
            ItemCj itemCj = ItemCj.builder().itemId(item.getId()).score(score).selected(selected).itemName(item.getName()).build();
            itemCjs.add(itemCj);
        }
        return itemCjs;
    }

    private SubjectCj getSubjectCj(Subject subject, Rowdata rowdata) {

        boolean qk = Boolean.parseBoolean(rowdata.getData("qk"));
        Double score = Double.parseDouble(rowdata.getData("score"));
        Double kgscore = Double.parseDouble(rowdata.getData("kgscore"));
        Double zgscore = Double.parseDouble(rowdata.getData("zgscore"));
        return SubjectCj.builder()
                .subject(subject.getName())
                .qk(qk)
                .score(score)
                .kgScore(kgscore)
                .zgScore(zgscore)
                .build();
    }
}
