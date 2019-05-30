package com.newyu.service;

import com.newyu.domain.exam.Item;

import java.util.List;

/**
 * ClassName: SubjectItemService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 上午11:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ItemService {

    void createItem(Item item);

    void createItems(List<Item> items);


    int deleteItem(Item item);

    Item getItem(int id);

}
