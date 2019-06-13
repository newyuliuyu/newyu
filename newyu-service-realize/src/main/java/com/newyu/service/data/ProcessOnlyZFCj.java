package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.exam.Exam;
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
public class ProcessOnlyZFCj {
    private Exam exam;
    private UploadFile cjFile;
    private Path saveDirPath;
    private List<Subject> subjects;
    private static final String LINE_SEPARATOR;

    static {
        // avoid security issues
        StringBuilderWriter buf = new StringBuilderWriter(4);
        PrintWriter out = new PrintWriter(buf);
        out.println();
        LINE_SEPARATOR = buf.toString();
        out.close();
    }

    public ProcessOnlyZFCj( Exam exam, List<Subject> subjects, UploadFile cjFile, String saveDir) {
        this.exam = exam;
        this.cjFile = cjFile;
        this.subjects = subjects;
        saveDirPath = Paths.get(saveDir, exam.getId() + "");
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public boolean proces() {
        FileProcess fileProcess = FileProcessUtil.getFileProcess(cjFile.getNewFile());
        try {
            List<Rowdata> dataset = readerData(fileProcess);
            for (Subject subject : subjects) {
                saveSubjectCj(subject, dataset);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            fileProcess.close();
        }
        return true;
    }

    private void saveSubjectCj(Subject subject, List<Rowdata> dataset) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = (String.join(",", getHeaderLine()) + LINE_SEPARATOR).getBytes("UTF-8");
        byteArrayOutputStream.write(data);
        for (Rowdata rowdata : dataset) {
            data = (String.join(",", readeCj(subject, rowdata)) + LINE_SEPARATOR).getBytes("UTF-8");
            byteArrayOutputStream.write(data);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        String savePath = saveDirPath.resolve(subject.getName() + "cj.csv").toString();
        FileUtil.save(byteArrayInputStream, savePath);
    }


    private List<Rowdata> readerData(FileProcess fileProcess) {
        List<Rowdata> result = Lists.newArrayList();
        while (fileProcess.next()) {
            Rowdata rowdata = fileProcess.getRowdata();
            result.add(rowdata);
        }
        return result;
    }

    private List<String> readeCj(Subject subject, Rowdata rowdata) {
        String zkzh = rowdata.getData("准考证号");
        if (StringUtils.isBlank(zkzh)) {
            String msg = MessageFormat.format("[{0}]学生没有准考证号", subject);
            throw new RuntimeException(msg);
        }
        String value = rowdata.getData(subject.getName());
        if (NumberHelper.isNumeric(value)) {
            String msg = MessageFormat.format("[{0}],学生:{1}的总分成绩不是数字[{2}]", subject, zkzh, value);
            throw new RuntimeException(msg);
        }
        List<String> result = Lists.newArrayList();
        result.add(zkzh);
        result.add("");
        result.add("");
        result.add("");
        result.add("0");
        result.add(value);
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
        return result;
    }
}
