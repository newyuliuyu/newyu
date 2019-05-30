package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.dto.SubjectDatasource;
import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.Item;
import com.newyu.domain.exam.ItemType;
import com.newyu.domain.exam.Subject;
import com.newyu.utils.id.IdGenerator;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.FileProcessUtil;
import com.newyu.utils.io.file.Rowdata;
import com.newyu.utils.tool.FileUtil;
import com.newyu.utils.tool.NumberHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProcessCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-13 上午9:35 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class ProcessAddItemCj {
    private Exam exam;
    private IdGenerator idGenerator;
    private Subject subject;
    private SubjectDatasource subjectDatasource;
    private Path saveDirPath;
    private Map<String, Rowdata> itemCjMap = Maps.newHashMap();
    private List<Item> items = Lists.newArrayList();

    private static final String LINE_SEPARATOR;

    static {
        // avoid security issues
        StringBuilderWriter buf = new StringBuilderWriter(4);
        PrintWriter out = new PrintWriter(buf);
        out.println();
        LINE_SEPARATOR = buf.toString();
        out.close();
    }

    public ProcessAddItemCj(IdGenerator idGenerator, Exam exam, Subject subject, SubjectDatasource subjectDatasource, String saveDir) {
        this.exam = exam;
        this.idGenerator = idGenerator;
        this.subject = subject;
        this.saveDirPath = Paths.get(saveDir, exam.getId() + "");
        this.subjectDatasource = subjectDatasource;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean proces() {
        checkSubjectCjFile();
        readItem();
        readCj();
        mergeCj();
        return true;
    }


    private void checkSubjectCjFile() {
        Path cjPath = getSubjectCjPath();
        if (FileUtil.existFile(cjPath.toString())) {
            String msg = MessageFormat.format("{0}成绩文件不存在，不能增加科目小题成绩", subject);
        }
    }

    private void readItem() {
        FileProcess fileProcess = FileProcessUtil.getFileProcess(subjectDatasource.getXmb().getNewFile());
        int idx = subject.getItems().size() + 1;
        try {
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                Item item = ProcessItem.createItem(exam.getId(), subject.getId(), idGenerator.nextId(), rowdata, idx++);
                ProcessItem.checkItem(item);
                checkItem(item);
                items.add(item);
            }
        } finally {
            fileProcess.close();
        }
        ProcessItem.checkRepeat(items);
    }

    private void checkItem(Item item) {
        Item item1 = subject.queryItem(item.getName());
        if (item1 != null) {
            String msg = MessageFormat.format("{0}新增加的题目{1}已经存在", subject, item);
            throw new RuntimeException(msg);
        }
    }

    private Path getSubjectCjPath() {
        Path cjPath = saveDirPath.resolve(subject.getName() + "cj.csv");
        return cjPath;
    }

    private void readCj() {
        String filepath = subjectDatasource.getCj().getNewFile();
        FileProcess fileProcess = FileProcessUtil.getFileProcess(filepath);
        try {
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                String zkzh = rowdata.getData("准考证号");
                if (StringUtils.isBlank(zkzh)) {
                    String msg = MessageFormat.format("[{0}]小题文件中没有准考证号", subject);
                    throw new RuntimeException(msg);
                }
                for (Item item : items) {
                    String value = getItemCjScore(item, rowdata);
                    if (NumberHelper.isNumeric(value)) {
                        String msg = MessageFormat.format("[{0},{1}],学生:{2}的成绩不是数字[{3}]", subject, item, zkzh, value);
                        throw new RuntimeException(msg);
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            fileProcess.close();
        }
    }

    private String getItemCjScore(Item item, Rowdata rowdata) {
        String filed = item.getFieldName();
        if (StringUtils.isBlank(filed)) {
            filed = item.getName();
        }
        String value = rowdata.getData(filed);
        return value;
    }


    private void mergeCj() {
        FileProcess fileProcess = FileProcessUtil.getFileProcess(getSubjectCjPath());
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            List<String> headers = getHeaderLine();
            byte[] data = (String.join(",", headers) + LINE_SEPARATOR).getBytes("UTF-8");
            byteArrayOutputStream.write(data);
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                String zkzh = rowdata.getData("准考证号");
                Rowdata itemCjRowdata = itemCjMap.get(zkzh);
                if (itemCjRowdata == null) {
                    String msg = MessageFormat.format("[{0}],学生:{1}没有找到小题成绩]", subject, zkzh);
                    throw new RuntimeException(msg);
                }
                List<String> cjData = readeCj(headers, rowdata, itemCjRowdata);
                data = (String.join(",", cjData) + LINE_SEPARATOR).getBytes("UTF-8");
                byteArrayOutputStream.write(data);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            String savePath = saveDirPath.resolve(subject.getName() + "cj.csv").toString();
            FileUtil.save(byteArrayInputStream, savePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            fileProcess.close();
        }
    }


    private List<String> readeCj(List<String> headers, Rowdata rowdata, Rowdata itemCjRowdata) {
        int sz = headers.size() - items.size();
        List<String> result = Lists.newArrayList();
        for (int i = 0; i < sz; i++) {
            result.add(rowdata.getData(result.get(i)));
        }
        for (Item item : items) {
            result.add(getItemCjScore(item, rowdata));
        }
        return result;
    }

    private List<String> getHeaderLine() {

        List<String> result = Lists.newArrayList();

        result.add("zkzh");
        result.add("teachClazzCode");
        result.add("teachClazzName");
        result.add("teachClazzGroup");
        result.add("qk");
        result.add("score");

        List<Item> items = subject.getItems();
        if (!items.isEmpty()) {
            result.add("kgScore");
            result.add("zgScore");
            for (Item item : items) {
                result.add(item.getName());
                if (!item.getItemType().equals(ItemType.Not_Select)) {
                    result.add(item.getName() + "选项");
                }
            }
        }
        for (Item item : items) {
            result.add(item.getName());
        }
        return result;
    }
}
