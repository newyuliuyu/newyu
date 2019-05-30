package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.newyu.domain.commons.ImportFiled;
import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.exam.*;
import com.newyu.domain.org.City;
import com.newyu.domain.org.County;
import com.newyu.domain.org.OrgXSchool;
import com.newyu.domain.org.Province;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.FileProcessUtil;
import com.newyu.utils.io.file.Rowdata;
import com.newyu.utils.tool.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: ProcessBmk <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午1:48 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class ProcessBmk {
    private Exam exam;
    private List<UploadFile> bmks;
    private Path saveDirPath;
    private OrgMgr orgMgr = new OrgMgr();
    private Set<StudentExtendField> studentExtendFields;
    private Map<StudentExtendField, Boolean> extendFieldHasValueMap = Maps.newHashMap();
    private StudentMgr studentMgr = new StudentMgr();
    private boolean wenke = false;
    private boolean like = false;
    private boolean notBranchSubject = false;
    private boolean hasClazzGroup = false;


    public ProcessBmk(Exam exam, List<UploadFile> bmks, String saveDir, Set<StudentExtendField> studentExtendFields) {
        this.exam = exam;
        this.bmks = bmks;
        saveDirPath = Paths.get(saveDir, exam.getId() + "");
        this.studentExtendFields = studentExtendFields;
    }

    public List<ImportFiled> getImportFiled() {
        List<ImportFiled> result = Lists.newArrayList();
        if (!orgMgr.getClazzMap().isEmpty()) {
            result.add(ImportFiled.builder().code("clazz").name("班级").hasValue(true).build());
        }
        if (hasClazzGroup) {
            result.add(ImportFiled.builder().code("clazz.group").name("班级分组").hasValue(true).build());
        }
        if (!orgMgr.getSchoolMap().isEmpty()) {
            result.add(ImportFiled.builder().code("school").name("学校").hasValue(true).build());
        }
        if (!orgMgr.getCountyMap().isEmpty()) {
            result.add(ImportFiled.builder().code("county").name("区县").hasValue(true).build());
        }
        if (!orgMgr.getCityMap().isEmpty()) {
            result.add(ImportFiled.builder().code("city").name("地市").hasValue(true).build());
        }
        if (!orgMgr.getProvinceMap().isEmpty()) {
            result.add(ImportFiled.builder().code("province").name("省").hasValue(true).build());
        }

        extendFieldHasValueMap.entrySet().forEach(x -> {
            result.add(ImportFiled.builder().code(x.getKey().getCode()).name(x.getKey().getName()).hasValue(x.getValue()).build());
        });
        return result;
    }


    public OrgMgr getOrgMgr() {
        return orgMgr;
    }

    public int getWL() {
        if (notBranchSubject) {
            return 0;
        } else if (wenke && like) {
            return 3;
        } else if (like) {
            return 1;
        } else if (wenke) {
            return 2;
        }
        return 0;
    }

    public ExamLevel getExamLevel() {
        if (!orgMgr.getProvinceMap().isEmpty()) {
            return ExamLevel.Provice_Exam;
        } else if (!orgMgr.getCityMap().isEmpty()) {
            return ExamLevel.City_Exam;
        } else if (!orgMgr.getCountyMap().isEmpty()) {
            return ExamLevel.County_Exam;
        } else if (!orgMgr.getSchoolMap().isEmpty() && orgMgr.getSchoolMap().size() > 1) {
            return ExamLevel.Union_Exam;
        }
        return ExamLevel.School_Exam;
    }


    public boolean process() {
        if (checkBmk()) {
            addOldBmk();
            processBmkFiles();
            saveStudentBmk();
        }
        return true;
    }

    private void saveStudentBmk() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        List<String> studentExtendFieldCodes = Optional.ofNullable(studentExtendFields).orElse(Sets.newHashSet()).stream()
                .map(StudentExtendField::getCode).collect(Collectors.toList());

        StringBuilder rowdata = new StringBuilder();
        rowdata.append("ownId,zkzh,code,name,wl," +
                "clazzCode,clazzName,clazzGroup," +
                "schoolCode,SchoolName," +
                "countyCode,countyName," +
                "cityCode,cityName," +
                "provinceCode,provinceName");
        if (studentExtendFieldCodes != null && !studentExtendFieldCodes.isEmpty()) {
            String tmp = String.join(",", studentExtendFieldCodes);
            rowdata.append(",").append(tmp);
        }
        rowdata.append("\n");
        String encoding = "UTF-8";
        try {
            byteArrayOutputStream.write(rowdata.toString().getBytes(encoding));
        } catch (Exception e) {
            throw new RuntimeException("写数据到报名库文件报错", e);
        }


        studentMgr.list().stream().forEach(x -> {
            String data = getStudentLinedata(x, studentExtendFieldCodes);
            try {
                byteArrayOutputStream.write(data.getBytes(encoding));
            } catch (Exception e) {
                throw new RuntimeException("写数据到报名库文件报错", e);
            }
        });
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        String savePath = saveDirPath.resolve("bmk.csv").toString();
        FileUtil.save(byteArrayInputStream, savePath);
    }

    private String getStudentLinedata(Student student, List<String> studentExtendFieldCodes) {
        StringBuilder theStudentData = new StringBuilder();

        String clazzData = ",,";
        if (student.getClazz() != null) {
            clazzData = student.getClazz().getCode() + "," + student.getClazz().getName() + "," + student.getClazz().getGroup();
        }
        String schoolData = ",";
        if (student.getSchool() != null) {
            schoolData = student.getSchool().getCode() + "," + student.getSchool().getName();
        }
        String countyData = ",";
        if (student.getCounty() != null) {
            countyData = student.getCounty().getCode() + "," + student.getCounty().getName();
        }
        String cityData = ",";
        if (student.getCity() != null) {
            cityData = student.getCity().getCode() + "," + student.getCity().getName();
        }
        String provinceData = ",";
        if (student.getProvince() != null) {
            provinceData = student.getProvince().getCode() + "," + student.getProvince().getName();
        }


        List<String> extendFiledValues = Lists.newArrayList();
        for (String code : studentExtendFieldCodes) {
            String value = student.getExtendValueMap().get(code);
            if (value == null) {
                value = "";
            }
            extendFiledValues.add(value);
        }
        String extendFiledData = String.join(",", extendFiledValues);


        theStudentData.append(student.getOwnId()).append(",")
                .append(student.getZkzh()).append(",")
                .append(student.getCode()).append(",")
                .append(student.getName()).append(",")
                .append(student.getWl().getCode()).append(",")
                .append(clazzData).append(",")
                .append(schoolData).append(",")
                .append(countyData).append(",")
                .append(cityData).append(",")
                .append(provinceData);
        if (studentExtendFieldCodes != null && !studentExtendFieldCodes.isEmpty()) {
            theStudentData.append(",").append(extendFiledData);
        }
        theStudentData.append("\n");
        return theStudentData.toString();
    }

    private void processBmkFiles() {
        Optional.ofNullable(bmks).orElse(Lists.newArrayList()).stream().forEach(this::createBmk);
        createOrgXSchool();
    }

    private void createOrgXSchool() {
        Province province = Province.builder().code("").name("").build();
        City city = City.builder().code("").name("").build();
        County county = County.builder().code("").name("").build();
        Set<OrgXSchool> orgXSchoolSet = studentMgr.list().stream().map(x -> {
            OrgXSchool orgXSchool = new OrgXSchool();
            orgXSchool.setSchool(x.getSchool());
            orgXSchool.setProvince(x.getProvince() == null ? province : x.getProvince());
            orgXSchool.setCity(x.getCity() == null ? city : x.getCity());
            orgXSchool.setCounty(x.getCounty() == null ? county : x.getCounty());
            return orgXSchool;
        }).collect(Collectors.toSet());
        orgMgr.setOrgXSchools(orgXSchoolSet);
    }

    private void createBmk(UploadFile uploadFile) {
        FileProcess fileProcess = FileProcessUtil.getFileProcess(uploadFile.getNewFile());
        try {
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                Student student = FromRowDataReader.getStudent(rowdata, studentExtendFields, orgMgr);
                checkStudent(student);
                processExtendFiledHasValue(student);
                studentMgr.add(student);
            }
        } finally {
            fileProcess.close();
        }
    }

    private void checkStudent(Student student) {
        checkZKZH(student);
        checkSchool(student);
        checkWL(student);
        checkClazz(student);
    }

    private void checkClazz(Student student) {
        if (student.getClazz() != null && StringUtils.isNotBlank(student.getClazz().getGroup())) {
            hasClazzGroup = true;
        }
    }

    private void checkWL(Student student) {
        if (student.getWl().equals(WLType.Like)) {
            like = true;
        } else if (student.getWl().equals(WLType.Like)) {
            wenke = true;
        } else {
            notBranchSubject = true;
        }

        if (notBranchSubject && (like || wenke)) {
            String msg = MessageFormat.format("报名库中不能存在友分文理又不分文理的的学生", student);
            throw new RuntimeException(msg);
        }
    }

    private void checkZKZH(Student student) {
        if (StringUtils.isBlank(student.getZkzh())) {
            String msg = MessageFormat.format("[{0}]没有准考证", student);
            throw new RuntimeException(msg);
        }
    }

    private void checkSchool(Student student) {
        if (student.getSchool() == null) {
            String msg = MessageFormat.format("[{0}]没有学校数据", student);
            throw new RuntimeException(msg);
        }
    }

    private void processExtendFiledHasValue(Student student) {
        Optional.ofNullable(studentExtendFields).orElse(Sets.newHashSet()).forEach(x -> setStudentExtendFieldHasValue(x, student));
    }

    private void setStudentExtendFieldHasValue(StudentExtendField studentExtendField, Student student) {
        String value = student.getExtendValueMap().get(studentExtendField.getCode());
        if (StringUtils.isNotBlank(value)) {
            extendFieldHasValueMap.put(studentExtendField, true);
        }
    }


    private boolean checkBmk() {
        if (bmks == null || bmks.isEmpty()) {
            log.debug("[{}]没有报名库，不对报名库进行处理", exam);
            return false;
        }
        return true;
    }

    public void addOldBmk() {
        Path oldBmk = saveDirPath.resolve("bmk.csv");
        List<UploadFile> bmkFiles = Lists.newArrayList();
        if (FileUtil.existFile(oldBmk.toString())) {
            UploadFile uploadFile = UploadFile.builder().newFile(oldBmk.toString()).build();
            bmkFiles.add(uploadFile);
        }
        bmkFiles.addAll(bmks);
        bmks = bmkFiles;
    }

}
