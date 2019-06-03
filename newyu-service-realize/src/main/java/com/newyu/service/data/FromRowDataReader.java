package com.newyu.service.data;

import com.google.common.collect.Maps;
import com.newyu.domain.exam.Student;
import com.newyu.domain.exam.StudentExtendField;
import com.newyu.domain.exam.WLType;
import com.newyu.domain.org.*;
import com.newyu.utils.io.file.Rowdata;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * ClassName: FromRowDataReader <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-8 下午6:01 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class FromRowDataReader {
    public static Province getProvince(Rowdata rowdata, Map<String, Province> provinceMap) {
        String code = rowdata.getData("provinceCode", "省代码");
        String name = rowdata.getData("provinceName", "省名称");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
            Province province = provinceMap.get(code);
            if (province == null) {
                province = Province.builder().code(code).name(name).build();
                provinceMap.put(code, province);
            }
            return province;
        }
        return null;
    }

    public static City getCity(Rowdata rowdata, Map<String, City> cityMap) {
        String code = rowdata.getData("cityCode", "地市代码");
        String name = rowdata.getData("cityName", "地市名称");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
            City city = cityMap.get(code);
            if (city == null) {
                city = City.builder().code(code).name(name).build();
                cityMap.put(code, city);
            }
            return city;
        }
        return null;
    }

    public static County getCounty(Rowdata rowdata, Map<String, County> countyMap) {
        String code = rowdata.getData("countyCode", "区县代码");
        String name = rowdata.getData("countyName", "区县名称");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
            County county = countyMap.get(code);
            if (county == null) {
                county = County.builder().code(code).name(name).build();
                countyMap.put(code, county);
            }
            return county;
        }
        return null;
    }

    public static School getSchool(Rowdata rowdata, Map<String, School> schoolMap) {
        String code = rowdata.getData("schoolCode", "学校代码");
        String name = rowdata.getData("schoolName", "学校名称");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
            School school = schoolMap.get(code);
            if (school == null) {
                school = School.builder().code(code).name(name).build();
                schoolMap.put(code, school);
            }
            return school;
        }
        return null;
    }

    public static Clazz getClazz(Rowdata rowdata, Map<String, School> schoolMap, Map<String, Clazz> clazzMap, int wl) {
        School school = getSchool(rowdata, schoolMap);
        String code = rowdata.getData("clazzCode", "班级代码");
        String name = rowdata.getData("clazzName", "班级名称");
        String clazzGroup = rowdata.getData("clazzGroup", "班级分组");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
            Clazz clazz = clazzMap.get(code);
            if (clazz == null) {
                clazz = Clazz.builder().code(code).name(name).group(clazzGroup).build();
                clazz.setSchool(school);
                clazzMap.put(code, clazz);
            }
            return clazz;
        }
        return null;
    }

    public static TeachClazz getTeachClazz(String subjectName, School school, Rowdata rowdata, Map<String, TeachClazz> teachClazzMap) {
        String code = rowdata.getData("teachClazzCode", "教学班代码");
        String name = rowdata.getData("teachClazzName", "教学班名称");
        String clazzGroup = rowdata.getData("teachClazzGroup", "教学班分组");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
            TeachClazz teachClazz = teachClazzMap.get(code);
            if (teachClazz == null) {
                teachClazz = TeachClazz.teachClazzBuilder().code(code).name(name).group(clazzGroup).build();
                teachClazz.setSubjectName(subjectName);
                teachClazz.setSchool(school);
                teachClazzMap.put(code, teachClazz);
            }
            return teachClazz;
        }
        return null;
    }

    public static Student getStudent(Rowdata rowdata, Set<StudentExtendField> studentExtendFields, OrgMgr orgMgr) {

        String name = rowdata.getData("name", "姓名");
        String zkzh = rowdata.getData("zkzh", "准考证号");
        String code = rowdata.getData("code", "学号");
        String ownId = rowdata.getData("ownId", "学生ID");
        String wl = rowdata.getData("wl", "文理");
        Student student = Student.builder()
                .name(name)
                .zkzh(zkzh)
                .code(code)
                .ownId(ownId)
                .wl(getWLType(wl))
                .extendValueMap(getExtendFieldValue(rowdata, studentExtendFields))
                .build();
        student.setProvince(getProvince(rowdata, orgMgr.getProvinceMap()));
        student.setCity(getCity(rowdata, orgMgr.getCityMap()));
        student.setCounty(getCounty(rowdata, orgMgr.getCountyMap()));
        student.setSchool(getSchool(rowdata, orgMgr.getSchoolMap()));
        student.setClazz(getClazz(rowdata, orgMgr.getSchoolMap(), orgMgr.getClazzMap(), student.getWl().getCode()));
        return student;
    }

    private static WLType getWLType(String wl) {
        int wlValue = Integer.parseInt(wl);
        WLType[] wlTypes = WLType.values();
        for (WLType wlType : wlTypes) {
            if (wlType.getCode() == wlValue) {
                return wlType;
            }
        }
        return WLType.Not_Branch_Subject;
    }

    private static Map<String, String> getExtendFieldValue(Rowdata rowdata, Set<StudentExtendField> studentExtendFields) {
        Map<String, String> valueMap = Maps.newHashMap();
        if (studentExtendFields != null) {
            for (StudentExtendField studentExtendField : studentExtendFields) {
                String[] fileds = studentExtendField.getFileName().split("\\|");
                String value = rowdata.getData(fileds);
                if (StringUtils.isNotBlank(value)) {
                    valueMap.put(studentExtendField.getCode(), value);
                }
            }
        }

        return valueMap;
    }
}
