package com.newyu.service.impl;

import com.newyu.domain.exam.Item;
import com.newyu.service.ItemService;
import com.newyu.service.dao.SubjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: ItemServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-17 下午2:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private SubjectDao subjectDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createItem(Item item) {
        subjectDao.createItem(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createItems(List<Item> items) {
        subjectDao.createItems(items);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteItem(Item item) {
        return subjectDao.deleteItem(item);
    }

    @Override
    public Item getItem(int id) {
        return subjectDao.getItem(id);
    }
}
