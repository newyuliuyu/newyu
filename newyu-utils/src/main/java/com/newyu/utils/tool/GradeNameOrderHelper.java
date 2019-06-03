/**
 * Project Name:easydata.web
 * File Name:GradeNameOrderHelper.java
 * Package Name:com.ez.data.utils
 * Date:2017年4月19日上午10:44:45
 * Copyright (c) 2017, easytnt All Rights Reserved.
 */
package com.newyu.utils.tool;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: GradeNameOrderHelper <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年4月19日 上午10:44:45 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class GradeNameOrderHelper {
    private static Pattern pattern = Pattern.compile("[一二三四五六七八九十]+");

    public static int transformationGradeNameToNum(String gradeName) {
        String grade1 = "";
        if (gradeName.contains("高")) {
            grade1 = "3";
        } else if (gradeName.contains("初")) {
            grade1 = "2";
        } else if (gradeName.contains("小")) {
            grade1 = "1";
        }

        Matcher matcher = pattern.matcher(gradeName);
        int grade2 = 0;
        if (matcher.find()) {
            String num = matcher.group();
            grade2 = NumberHelper.chineseToNumber(num);
        }
        return Integer.parseInt(grade1 + grade2);
    }

    public static GradeInfo getGradeInfo(String gradeName) {
        return getGradeInfo(gradeName, new Date());
    }

    public static GradeInfo getGradeInfo(String gradeName, Date date) {

        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setGradeName(gradeName);
        int month = Integer.parseInt(Dateutil.getMonth(date));
        int year = Integer.parseInt(Dateutil.getYear(date));

        gradeInfo.setYear(year);
        gradeInfo.setMonth(month);

        if (month >= 2 || month < 9) {
            gradeInfo.setSemester(2);
        } else {
            gradeInfo.setSemester(1);
        }


        if ("一年级".equals(gradeName)) {
            gradeInfo.setStudySetion(1);
        } else if ("二年级".equals(gradeName)) {
            gradeInfo.setStudySetion(1);
            year = year - 1;
        } else if ("三年级".equals(gradeName)) {
            gradeInfo.setStudySetion(1);
            year = year - 2;
        } else if ("四年级".equals(gradeName)) {
            gradeInfo.setStudySetion(1);
            year = year - 3;
        } else if ("五年级".equals(gradeName)) {
            gradeInfo.setStudySetion(1);
            year = year - 4;
        } else if ("六年级".equals(gradeName)) {
            gradeInfo.setStudySetion(1);
            year = year - 5;
        } else if ("七年级".equals(gradeName) || "初一".equals(gradeName)) {
            gradeInfo.setStudySetion(2);
            year = year;
        } else if ("八年级".equals(gradeName) || "初二".equals(gradeName)) {
            gradeInfo.setStudySetion(2);
            year = year - 1;
        } else if ("九年级".equals(gradeName) || "初三".equals(gradeName)) {
            gradeInfo.setStudySetion(2);
            year = year - 2;
        } else if ("高一".equals(gradeName)) {
            gradeInfo.setStudySetion(3);
        } else if ("高二".equals(gradeName)) {
            gradeInfo.setStudySetion(2);
            year = year - 1;
        } else if ("高三".equals(gradeName)) {
            gradeInfo.setStudySetion(2);
            year = year - 2;
        }
        if (gradeInfo.getSemester() == 1) {
            gradeInfo.setEntranceYear(year);
        } else {
            gradeInfo.setEntranceYear(year - 1);
        }

        if (gradeInfo.getStudySetion() == 1) {
            gradeInfo.setGraduationYear(gradeInfo.getEntranceYear() + 6);
        } else {
            gradeInfo.setGraduationYear(gradeInfo.getEntranceYear() + 3);
        }
        return gradeInfo;
    }

}
