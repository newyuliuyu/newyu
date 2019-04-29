package com.newyu.domain.exam;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: SubjectTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-29 下午2:45 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class SubjectTest {

    @Test
    public void testCreatItemGroup() throws Exception {
        List<Item> items = Lists.newArrayList();
        items.add(Item.builder().ability("").build());
        items.add(Item.builder().ability("1").build());
        items.add(Item.builder().ability("2").build());
        items.add(Item.builder().ability("3").build());
        items.add(Item.builder().ability("4").choice(true).build());
        Subject subject = Subject.builder().build();
        subject.setItems(items);

       Map<String,List<Item>> datamap = items.stream().filter(x -> {
            return StringUtils.isNotBlank(x.getAbility()) && !x.isChoice();
        }).collect(Collectors.groupingBy(x->x.getAbility()));


    }

}