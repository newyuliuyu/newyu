package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.Item;
import com.newyu.domain.exam.ItemType;
import com.newyu.domain.exam.Subject;
import com.newyu.utils.id.IdGenerator;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.FileProcessUtil;
import com.newyu.utils.io.file.Rowdata;
import com.newyu.utils.tool.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: ProcessItem <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-10 下午5:53 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class ProcessItem {
    private Exam exam;
    private UploadFile xmb;
    private String saveDir;
    private Path saveDirPath;
    private Subject subject;
    private IdGenerator idGenerator;

    public ProcessItem(IdGenerator idGenerator, Exam exam, Subject subject, UploadFile xmb, String saveDir) {
        this.exam = exam;
        this.xmb = xmb;
        this.saveDirPath = Paths.get(saveDir, exam.getId() + "");
        this.subject = subject;
        this.idGenerator = idGenerator;
    }

    public boolean process() {
        if (checkItemFile()) {
            processXmbFile();
        }
        return false;
    }

    private void processXmbFile() {
        FileProcess fileProcess = FileProcessUtil.getFileProcess(xmb.getNewFile());
        int idx = 1;
        List<Item> items = Lists.newArrayList();
        try {
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                Item item = createItem(exam.getId(), subject.getId(), idGenerator.nextId(), rowdata, idx++);
                checkItem(item);
                items.add(item);
            }
        } finally {
            fileProcess.close();
        }
        checkRepeat(items);
        subject.setItems(items);
        Subject.calcaluteSubjectScore(subject);
    }


    public static void checkRepeat(List<Item> items) {

        Map<String, Integer> dataMap = items.stream().collect(Collectors.groupingBy(x -> x.getName(), Collectors.reducing(0, x -> 1, Integer::sum)));
        if (dataMap.size() != items.size()) {
            List<String> itemNames = dataMap.entrySet().stream().filter(x -> x.getValue() > 1).map(x -> x.getKey()).collect(Collectors.toList());
            String msg = MessageFormat.format("[{0}]有重复", String.join(",", itemNames));
            throw new RuntimeException(msg);
        }


    }

    public static void checkItem(Item item) {
        checkName(item);
        checkScore(item);
    }

    public static void checkName(Item item) {
        if (StringUtils.isBlank(item.getName())) {
            String msg = MessageFormat.format("[{0}]题目号必须有", item);
            throw new RuntimeException(msg);
        }
    }

    public static void checkScore(Item item) {
        if (item.getScore() <= 0) {
            String msg = MessageFormat.format("[{0}]题目满分必须大于0", item);
            throw new RuntimeException(msg);
        }
    }

    public static Item createItem(long examId, long subjectId, long id, Rowdata rowdata, int idx) {
        Item item = new Item();
        item.setExamId(examId);
        item.setSubjectId(subjectId);
        item.setId(id);
        item.setName(rowdata.getData("题号"));
        String value = rowdata.getData("满分");
        item.setScore(StringUtils.isBlank(value) ? 0 : Double.parseDouble(value));
        item.setKnowledge(rowdata.getData("知识点"));
        item.setAbility(rowdata.getData("能力结构"));
        item.setTitleType(rowdata.getData("题型"));
        item.setBigTitleName(rowdata.getData("大题号"));
        item.setSmallTitleName(rowdata.getData("小题号"));
        ItemType itemType = ItemType.Not_Select;
        value = rowdata.getData("选择题类型");
        if (value.equalsIgnoreCase("1")) {
            itemType = ItemType.Single_Select;
        } else if (value.equalsIgnoreCase("2")) {
            itemType = ItemType.Multi_Select;
        }
        item.setItemType(itemType);
        item.setAnswer(rowdata.getData("正确答案"));
        value = rowdata.getData("可选项");
        item.setFullOptional(StringUtils.isBlank(value) ? "ABCD" : value);
        value = rowdata.getData("是否选做题");
        item.setChoice("1".equalsIgnoreCase(value) ? true : false);
        item.setChoiceInfo(rowdata.getData("选做题规则"));
        item.setFieldName(rowdata.getData("对应字段"));
        item.setTitleBlock(rowdata.getData("题块"));
        item.setDisplayOrder(idx);

        return item;
    }

    private boolean checkItemFile() {
        if (xmb == null || !FileUtil.existFile(xmb.getNewFile())) {
            log.warn("{}-{}没有系目表", exam.getName(), subject.getName());
            return false;
        }
        return true;
    }
}
