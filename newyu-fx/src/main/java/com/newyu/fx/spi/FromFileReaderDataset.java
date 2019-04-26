package com.newyu.fx.spi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.exam.*;
import com.newyu.domain.org.*;
import com.newyu.fx.Dataset;
import com.newyu.fx.FxContext;
import com.newyu.fx.ReaderDataset;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.FileProcessUtil;
import com.newyu.utils.io.file.Rowdata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public static FromFileReaderDataset newInstance(String fileDirPath) {
        return newInstance(Paths.get(fileDirPath));
    }

    public static FromFileReaderDataset newInstance(Path fileDirPath) {
        FromFileReaderDataset result = new FromFileReaderDataset();
        result.fileDirPath = fileDirPath;
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
        Map<String, School> schoolMap = new HashMap<>(16);
        Map<String, Clazz> clazzMap = new HashMap<>(16);
        Map<String, City> cityMap = new HashMap<>(16);
        Map<String, County> countyMap = new HashMap<>(16);
        Map<String, Province> provinceMap = new HashMap<>(32);
        FileProcess fileProcess = FileProcessUtil.getFileProcess(fileDirPath.resolve("bmk.csv"));
        try {
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                Province province = getProvince(rowdata, provinceMap);
                City city = getCity(rowdata, cityMap);
                County county = getCounty(rowdata, countyMap);
                School school = getSchool(rowdata, schoolMap);
                Clazz clazz = getClazz(rowdata, schoolMap, clazzMap);
                StudentCj studentCj = getStudentCj(rowdata);
                dataset.add(studentCj);
            }
        } finally {
            fileProcess.close();
        }
    }

    private StudentCj getStudentCj(Rowdata rowdata) {


        String name = rowdata.getData("name");
        String zkzh = rowdata.getData("zkzh");
        String code = rowdata.getData("code");
        String ownId = rowdata.getData("ownId");
        String wl = rowdata.getData("wl");


        StudentCj studentCj = StudentCj.builder().name(name)
                .zkzh(zkzh)
                .code(code)
                .ownId(ownId)
                .wl(getWLType(wl))
                .build();
        return studentCj;
    }

    private WLType getWLType(String wl) {
        int wlValue = Integer.parseInt(wl);
        WLType[] wlTypes = WLType.values();
        for (WLType wlType : wlTypes) {
            if (wlType.getCode() == wlValue) {
                return wlType;
            }
        }
        return WLType.Not_Branch_Subject;
    }

    private Province getProvince(Rowdata rowdata, Map<String, Province> provinceMap) {
        String code = rowdata.getData("provinceCode");
        String name = rowdata.getData("provinceName");
        if (StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
            Province province = provinceMap.get(code);
            if (province == null) {
                province = Province.builder().code(code).name(name).build();
                provinceMap.put(code, province);
            }
            return province;
        }
        return null;
    }

    private City getCity(Rowdata rowdata, Map<String, City> cityMap) {
        String code = rowdata.getData("cityCode");
        String name = rowdata.getData("cityName");
        if (StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
            City city = cityMap.get(code);
            if (city == null) {
                city = City.builder().code(code).name(name).build();
                cityMap.put(code, city);
            }
            return city;
        }
        return null;
    }

    private County getCounty(Rowdata rowdata, Map<String, County> countyMap) {
        String code = rowdata.getData("countyCode");
        String name = rowdata.getData("countyName");
        if (StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
            County county = countyMap.get(code);
            if (county == null) {
                county = County.builder().code(code).name(name).build();
                countyMap.put(code, county);
            }
            return county;
        }
        return null;
    }

    private School getSchool(Rowdata rowdata, Map<String, School> schoolMap) {
        String code = rowdata.getData("schoolCode");
        String name = rowdata.getData("schoolName");
        if (StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
            School school = schoolMap.get(code);
            if (school == null) {
                school = School.builder().code(code).name(name).build();
                schoolMap.put(code, school);
            }
            return school;
        }
        return null;
    }

    private Clazz getClazz(Rowdata rowdata, Map<String, School> schoolMap, Map<String, Clazz> clazzMap) {
        School school = getSchool(rowdata, schoolMap);
        String code = rowdata.getData("clazzCode");
        String name = rowdata.getData("clazzName");
        if (StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
            Clazz clazz = clazzMap.get(code);
            if (clazz == null) {
                clazz = Clazz.builder().code(code).name(name).build();
                clazz.setSchool(school);
                clazzMap.put(code, clazz);
            }
            return clazz;
        }
        return null;
    }

    private TeachClazz getTeachClazz(String subjectName, School school, Rowdata rowdata, Map<String, TeachClazz> teachClazzMap) {
        String code = rowdata.getData("teachClazzCode");
        String name = rowdata.getData("teachClazzName");
        if (StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
            TeachClazz teachClazz = teachClazzMap.get(code);
            if (teachClazz == null) {
                teachClazz = TeachClazz.teachClazzBuilder().code(code).name(name).build();
                teachClazz.setSubjectName(subjectName);
                teachClazz.setSchool(school);
                teachClazzMap.put(code, teachClazz);
            }
            return teachClazz;
        }
        return null;
    }


    private void readCj() {
        List<Subject> subjects = context.getSubject();
        for (Subject subject : subjects) {
            if (subject.isExamSubject()) {
                loadSubjectCj(subject);
            }
        }
    }

    private void loadSubjectCj(Subject subject) {
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
                TeachClazz teachClazz = getTeachClazz(subject.getName(), studentCj.get().getSchool(), rowdata, teachClazzMap);
                SubjectCj subjectCj = getSubjectCj(subject, rowdata);
                studentCj.get().addSubjectCj(subjectCj);
                subjectCj.setTeachClazz(teachClazz);
                if (!subject.getItems().isEmpty()) {
                    try {
                        List<ItemCj> itemCjs = getItemCj(subject, rowdata);
                        subjectCj.setItemCjs(itemCjs);
                    } catch (Exception e) {
                        String msg = MessageFormat.format("学生[{0}]获取小题成绩出错", studentCj.get());
                        throw new RuntimeException("", e);
                    }
                }
            }
        } finally {
            fileProcess.close();
        }
    }

    private List<ItemCj> getItemCj(Subject subject, Rowdata rowdata) {
        List<ItemCj> itemCjs = Lists.newArrayList();
        List<Item> items = subject.getItems();
        for (Item item : items) {
            String strScore = rowdata.getData(item.getName() + "得分");
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
                .subject(subject)
                .qk(qk)
                .score(score)
                .kgScore(kgscore)
                .zgScore(zgscore)
                .build();
    }
}
