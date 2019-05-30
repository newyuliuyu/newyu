package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.newyu.domain.commons.ImportFiled;
import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.Item;
import com.newyu.domain.exam.ItemType;
import com.newyu.domain.exam.Subject;
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
import java.util.Set;

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
public class ProcessCj {
    private Exam exam;
    private Subject subject;
    private UploadFile cjFile;
    private Path saveDirPath;
    private Set<ImportFiled> importFileds = Sets.newHashSet();

    private static final String LINE_SEPARATOR;

    static {
        // avoid security issues
        StringBuilderWriter buf = new StringBuilderWriter(4);
        PrintWriter out = new PrintWriter(buf);
        out.println();
        LINE_SEPARATOR = buf.toString();
        out.close();
    }

    public ProcessCj(Exam exam, Subject subject, UploadFile cjFile, String saveDir) {
        this.exam = exam;
        this.subject = subject;
        this.cjFile = cjFile;
        saveDirPath = Paths.get(saveDir, exam.getId() + "");
    }

    public List<ImportFiled> getImportFileds() {
        return Lists.newArrayList(importFileds);
    }

    public boolean proces() {
        processCjFile();
        return true;
    }

    private void processCjFile() {
        FileProcess fileProcess = FileProcessUtil.getFileProcess(cjFile.getNewFile());
        List<Item> items = Lists.newArrayList();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] data = (String.join(",", getHeaderLine()) + LINE_SEPARATOR).getBytes("UTF-8");
            byteArrayOutputStream.write(data);
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                data = (String.join(",", getLine(rowdata)) + LINE_SEPARATOR).getBytes("UTF-8");
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
        return result;
    }

    private void checkTeachClazz(Rowdata rowdata) {
        String code = rowdata.getData("教学班代码");
        String name = rowdata.getData("教学班名称");
        String group = rowdata.getData("教学班分组");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) {
            ImportFiled importFiled = ImportFiled.builder()
                    .subject(subject)
                    .code("teachClazz")
                    .name("教学班")
                    .hasValue(true)
                    .build();
            importFileds.add(importFiled);
        }
        if (StringUtils.isNotBlank(group)) {
            ImportFiled importFiled = ImportFiled.builder()
                    .subject(subject)
                    .code("teachClazz.group")
                    .name("教学班分组")
                    .hasValue(true)
                    .build();
            importFileds.add(importFiled);
        }
    }

    private List<String> getLine(Rowdata rowdata) {
        checkTeachClazz(rowdata);


        String zkzh = rowdata.getData("准考证号");
        if (StringUtils.isBlank(zkzh)) {
            String msg = MessageFormat.format("[{0}]学生没有准考证号", subject);
            throw new RuntimeException(msg);
        }
        String qk = rowdata.getData("缺考");
        qk = StringUtils.isBlank(qk) ? "0" : qk;

        List<String> result = Lists.newArrayList();
        result.add(zkzh);
        result.add(rowdata.getData("教学班代码"));
        result.add(rowdata.getData("教学班名称"));
        result.add(rowdata.getData("教学班分组"));
        result.add(qk);

        String value = rowdata.getData("总分");
        if (NumberHelper.isNumeric(value)) {
            String msg = MessageFormat.format("[{0}],学生:{1}的总分成绩不是数字[{2}]", subject, zkzh, value);
            throw new RuntimeException(msg);
        }
        result.add(value);

        List<Item> items = subject.getItems();
        if (!items.isEmpty()) {

            value = rowdata.getData("客观题总分");
            if (NumberHelper.isNumeric(value)) {
                String msg = MessageFormat.format("[{0}],学生:{1}的客观题总分成绩不是数字[{2}]", subject, zkzh, value);
                throw new RuntimeException(msg);
            }
            result.add(value);
            value = rowdata.getData("主观题总分");
            if (NumberHelper.isNumeric(value)) {
                String msg = MessageFormat.format("[{0}],学生:{1}的主观题总分成绩不是数字[{2}]", subject, zkzh, value);
                throw new RuntimeException(msg);
            }
            result.add(value);

            for (Item item : items) {
                String filed = item.getFieldName();
                if (StringUtils.isBlank(filed)) {
                    filed = item.getName();
                }
                value = rowdata.getData(filed);
                if (NumberHelper.isNumeric(value)) {
                    String msg = MessageFormat.format("[{0},{1}],学生:{2}的主观题总分成绩不是数字[{3}]", subject, item, zkzh, value);
                    throw new RuntimeException(msg);
                }
                result.add(value);

                if (!item.getItemType().equals(ItemType.Not_Select)) {
                    value = rowdata.getData(filed + "选项");
                    result.add(value);
                }
            }
        }

        return result;
    }


}
