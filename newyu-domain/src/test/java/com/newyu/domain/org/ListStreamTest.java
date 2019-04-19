package com.newyu.domain.org;

import com.newyu.domain.exam.Item;
import com.newyu.domain.exam.Subject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: ListStreamTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 下午1:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ListStreamTest {

    @Test
    public void toMap() throws Exception {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.builder().id(1L).name("test1").build());
        subjects.add(Subject.builder().id(2L).name("test2").build());
        subjects.add(Subject.builder().id(3L).name("test3").build());
        subjects.add(Subject.builder().id(4L).name("test4").build());

        subjects.stream().collect(Collectors.toMap(key -> key.getName(), value -> value));
    }

    @Test
    public void filterAndToSet() throws Exception {
        List<Item> items = new ArrayList<>();
//        items.add(Item.builder().id(1).name("1").choiceInfo("从12,13中选1题每题10分").build());
//        items.add(Item.builder().id(2).name("2").choiceInfo("从12,13中选1题每题10分").build());
        items.add(Item.builder().id(2).name("3").build());
        items.add(Item.builder().id(2).name("4").build());
//        items.add(Item.builder().id(2).name("5").choiceInfo("从22,23中选1题每题10分").build());
//        items.add(Item.builder().id(2).name("6").choiceInfo("从22,23中选1题每题10分").build());

        Set<String> sets = items.stream().filter(x -> StringUtils.isNotBlank(x.getChoiceInfo()))
                .map(x -> x.getChoiceInfo()).collect(Collectors.toSet());
        System.out.println();
    }
}
