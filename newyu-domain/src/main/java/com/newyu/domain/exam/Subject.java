package com.newyu.domain.exam;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: Subject <br/>
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
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name"})
@Builder
public class Subject {
    private Long examId;
    private Long id;
    private String name;

    private int wl;
    private int displayOrder = 0;

    private double fullScore;
    private double kgFullScore;
    private double zgFullScore;

    private String childSubjectNames = "";
    private List<Subject> childSubjects;

    private boolean examSubject = true;
    private boolean multiSubject = false;
    private boolean fullSubject = false;

    private List<Item> items;
    private Map<String, Item> itemMap;


    public List<ChoiceItemGroup> queryChoiceItemGroups() {
        List<ChoiceItemGroup> choiceItemGroups = new ArrayList<>();
        if (items == null && items.isEmpty()) {
            return choiceItemGroups;
        }
        Set<String> choiceInfos = items.stream().filter(x -> StringUtils.isNotBlank(x.getChoiceInfo()))
                .map(x -> x.getChoiceInfo()).collect(Collectors.toSet());
        choiceInfos.forEach(x -> choiceInfos.add(x));
        return choiceItemGroups;
    }

    public List<Subject> splitChildSubject() {
        Multimap<String, Item> itemMultimap = Multimaps.index(items, x -> x.getOtherSubject());
        List<Subject> newChildSubjects = new ArrayList<>();
        if (itemMultimap.size() < 1) {
            return newChildSubjects;
        }
        StringBuilder newChildSubjectNames = new StringBuilder();
        itemMultimap.keySet().stream().forEach(subjectName -> {
            List<Item> newItems = Lists.newArrayList(itemMultimap.get(subjectName));
            Subject newSubject = Subject.builder().name(subjectName)
                    .examId(examId)
                    .examSubject(false)
                    .wl(wl)
                    .items(newItems).build();
            newChildSubjects.add(newSubject);
            newChildSubjectNames.append(subjectName).append(",");
        });
        StringBuilder newChildSubjectNames2 = new StringBuilder();
        if (newChildSubjectNames.length() > 0) {
            newChildSubjectNames2 = newChildSubjectNames.deleteCharAt(newChildSubjectNames.length() - 1);
        }
        newChildSubjects.forEach(x -> calcaluteSubjectScore(x));

        setMultiSubject(true);
        setChildSubjectNames(newChildSubjectNames2.toString());
        return newChildSubjects;
    }

    private static void calcaluteSubjectScore(Subject subject) {
        List<Item> items = subject.getItems();
        double kgScore = 0;
        double zgScore = 0;
        for (Item item : items) {
            if (item.isChoice()) {
                continue;
            }
            if (item.getItemType().equals(ItemType.Not_Select)) {
                zgScore += item.getScore();
            } else {
                kgScore += item.getScore();
            }
        }

        List<ChoiceItemGroup> choiceItemGroups = subject.queryChoiceItemGroups();
        for (ChoiceItemGroup choiceItemGroup : choiceItemGroups) {
            zgScore += choiceItemGroup.getScore();
        }
        subject.setFullScore(kgScore + zgScore);
        subject.setKgFullScore(kgScore);
        subject.setZgFullScore(zgScore);
    }

    public Item queryItem(String itemName) {
        if (itemMap == null) {
            itemMap = items.stream().collect(Collectors.toMap(key -> key.getName(), value -> value));
        }
        return itemMap.get(itemName);
    }

}
