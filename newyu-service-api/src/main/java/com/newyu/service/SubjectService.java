package com.newyu.service;

import com.newyu.domain.exam.Subject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: SubjectService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 上午11:02 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface SubjectService {

    /**
     * 创建科目
     *
     * @param subject
     */
    void createSubject(Subject subject);

    /**
     * 创建一组科目
     *
     * @param subjects
     */
    void createSubjects(List<Subject> subjects);

    /**
     * 修改一个科目信息
     *
     * @param subject
     * @return
     */
    int updateSubject(Subject subject);

    /**
     * 修改科目分值
     *
     * @param subject
     * @return
     */
    int updateSubjectScore(Subject subject);


    /**
     * 删除一个科目
     *
     * @param subject
     * @return
     */
    int deleteSubject(Subject subject);

    /**
     * 删除科目的細目表
     *
     * @param subject
     * @return
     */
    int deleteSubjectItem(Subject subject);

    /**
     * 获取一个科目
     *
     * @param subjectId
     * @return
     */
    Subject getSubject(long subjectId);


    /**
     * 设置科目的子科目
     *
     * @param subjects
     */
    public static void setChildSubject(List<Subject> subjects) {
        Map<String, Subject> subjectMap = subjects.stream().collect(Collectors.toMap(key -> key.getName(), value -> value));
        subjects.forEach(subject -> {
            if (isCanSetChildSubject(subject)) {
                List<Subject> childSubjects = new ArrayList<>();
                String[] subjectNames = subject.getChildSubjectNames().split(",");
                Arrays.stream(subjectNames).forEach(subjectName -> {
                    Subject the = subjectMap.get(subjectName);
                    if (the != null) {
                        childSubjects.add(the);
                    }
                });
                subject.setChildSubjects(childSubjects);
            }
        });
    }

    /**
     * 判断科目是否可以设置子科目
     *
     * @param subject
     * @return
     */
    public static boolean isCanSetChildSubject(Subject subject) {
        return subject.isMultiSubject()
                && StringUtils.isNotBlank(subject.getChildSubjectNames())
                && (subject.getChildSubjects() == null
                || subject.getChildSubjects().isEmpty());
    }
}
