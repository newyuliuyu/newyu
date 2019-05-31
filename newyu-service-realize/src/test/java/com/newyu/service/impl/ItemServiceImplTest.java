package com.newyu.service.impl;

import com.google.common.collect.Lists;
import com.newyu.domain.exam.Item;
import com.newyu.domain.exam.ItemType;
import com.newyu.service.AppConfig;
import com.newyu.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: ItemServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-31 下午4:06 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;


    @Test
    public void createItem() throws Exception{
        Item item=Item.builder()
                .examId(1L)
                .subjectId(4L)
                .id(1L)
                .name("1")
                .score(5)
                .knowledge("知识点")
                .ability("能力结构")
                .titleType("题型")
                .bigTitleName("大题")
                .smallTitleName("小题")
                .itemType(ItemType.Single_Select)
                .answer("A")
                .fullOptional("ABCD")
                .otherSubject("其他科目")
                .choice(false)
                .choiceInfo("")
                .fieldName("filedName")
                .displayOrder(1)
                .titleBlock("titleBlock")
                .build();


        itemService.createItem(item);
    }
    @Test
    public void createItems() throws Exception{
        List<Item> items= Lists.newArrayList();
        items.add(Item.builder()
                .examId(1L)
                .subjectId(4L)
                .id(2L)
                .name("2")
                .score(5)
                .build());
        items.add(Item.builder()
                .examId(1L)
                .subjectId(4L)
                .id(3L)
                .name("3")
                .score(5)
                .build());
        items.add(Item.builder()
                .examId(1L)
                .subjectId(4L)
                .id(4L)
                .name("4")
                .score(5)
                .build());
        itemService.createItems(items);
    }

    @Test
    public void getItem() throws Exception{
        Item item = itemService.getItem(1);
        System.out.println();
    }
}