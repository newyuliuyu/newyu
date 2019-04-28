package com.newyu.domain.exam;

import com.google.common.collect.Maps;
import com.newyu.domain.org.*;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName: StudentCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午4:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "wl", "zkzh", "ownId", "subjectCjs", "school", "clazz"})
@Builder
public class StudentCj {
    private String name;
    private String zkzh;
    private String code;
    private String ownId;
    private WLType wl;
    private School school;
    private Clazz clazz;
    private County county;
    private City city;
    private Province province;
    private Map<String, SubjectCj> subjectCjs = Maps.newHashMap();

    public void addSubjectCj(SubjectCj subjectCj) {
        subjectCjs.put(subjectCj.getSubject(), subjectCj);
    }

    public SubjectCj getSubjectCj(Subject subject) {
        SubjectCj subjectCj = subjectCjs.get(subject.getName());
        if (subjectCj == null && subject.isChildSubject()) {

        } else if (subjectCj == null && subject.isSimilarFullSubject()) {
            subjectCj = getMultiSubjectScore(subject);
        } else {
            subjectCj = new SubjectCj();
            subjectCj.setSubject(subject.getName());
            subjectCj.setQk(true);
            subjectCj.setMiss(true);
        }
        return subjectCj;
    }


    private SubjectCj createChildSubjectCj(Subject subject) {

        SubjectCj subjectCj = subjectCjs.get(subject.getParentSubject());

        SubjectCj result = new SubjectCj();

        double zgScore = 0d;
        double kgScore = 0d;
        List<Item> items = subject.getItems();
        for (Item item : items) {
            ItemCj itemCj = subjectCj.queryItemCj(item.getName());
            result.addItemCj(itemCj);
            if (item.getItemType().equals(ItemType.Not_Select)) {
                zgScore += itemCj.getScore();
            } else {
                kgScore += itemCj.getScore();
            }
        }
        result.setSubject(subject.getName());
        result.setScore(zgScore + kgScore);
        result.setKgScore(kgScore);
        result.setZgScore(zgScore);
        result.setQk(subjectCj.isQk());
        result.setMiss(subjectCj.isMiss());
        return result;
    }

    private SubjectCj getMultiSubjectScore(Subject multiSubject) {
        List<Subject> subjects = multiSubject.getChildSubjects();

        Double kgScore = 0d;
        Double zgScore = 0d;
        int qkNum = 0;
        int missNum = 0;
        for (Subject subject : subjects) {
            SubjectCj subjectCj = getSubjectCj(subject);
            if (subjectCj.isQk()) {
                qkNum++;
                if (subjectCj.isMiss()) {
                    missNum++;
                }
            } else {
                kgScore += subjectCj.getKgScore();
                zgScore += subjectCj.getZgScore();
            }
        }
        boolean isQk = qkNum == subjects.size();
        boolean isMiss = missNum != subjects.size();
        SubjectCj subjectCj = new SubjectCj();
        subjectCj.setSubject(multiSubject.getName());
        subjectCj.setQk(isQk);
        subjectCj.setQkNum(qkNum);
        subjectCj.setMiss(isMiss);
        subjectCj.setMissNum(missNum);
        subjectCj.setScore(kgScore + zgScore);
        subjectCj.setKgScore(kgScore);
        subjectCj.setZgScore(zgScore);
        return subjectCj;
    }


}
