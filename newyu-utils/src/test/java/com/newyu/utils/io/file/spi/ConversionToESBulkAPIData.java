package com.newyu.utils.io.file.spi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.FileProcessUtil;
import com.newyu.utils.io.file.Rowdata;
import com.newyu.utils.json.Json2;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: ConversionToESBulkAPIData <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-15 下午1:57 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ConversionToESBulkAPIData {

    private Path root = Paths.get("/home/liuyu/test/test-data/cj/20");

    @Test
    public void test02() throws Exception {
        Map<String, TeacherInfo> data = getTeacherInfo(2017);

        System.out.println(Boolean.parseBoolean("FALSE"));
        System.out.println(Boolean.parseBoolean("TRUE"));
        System.out.println();
    }

    private Map<String, TeacherInfo> getTeacherInfo(int year) throws Exception {
        Map<String, TeacherInfo> data = Maps.newHashMap();

        FileProcess process = FileProcessUtil.getFileProcess(root.resolve(year + "bj.xls"));
        while (process.next()) {
            Rowdata rowdata = process.getRowdata();
            String subject = rowdata.getData("科目");
            String teacher1 = rowdata.getData("高一");
            String teacher2 = rowdata.getData("高二");
            String tearche3 = rowdata.getData("高三");
            String clazzNme = rowdata.getData("班级");
            String changeTeacher = rowdata.getData("有老师改变");

            TeacherInfo teacherInfo = TeacherInfo.builder().grade1(teacher1).grade2(teacher2).grade3(tearche3).change(Boolean.parseBoolean(changeTeacher)).build();
            data.put(subject + "." + clazzNme, teacherInfo);
            System.out.println();
        }
        return data;
    }


    @Test
    public void test() throws Exception {

        String[] likeSubjectNames = new String[]{"语文", "数学", "英语", "物理", "化学", "生物"};
        String[] wenkeSubjectNames = new String[]{"语文", "数学", "英语", "政治", "历史", "地里"};

        int year = 2019;
        Map<String, TeacherInfo> teacherDataset = getTeacherInfo(year);
        FileProcess process = FileProcessUtil.getFileProcess(root.resolve(year + ".xls"));
        while (process.next()) {
            Rowdata rowdata = process.getRowdata();
            StudentCj studentCj = StudentCj.builder()
                    .year(year)
                    .zkzh(rowdata.getData("准考证号"))
                    .name(rowdata.getData("姓名"))
                    .gender(rowdata.getData("性别"))
                    .wl(Integer.parseInt(rowdata.getData("文理")))
                    .schoolCode(rowdata.getData("学校代码"))
                    .schoolName(rowdata.getData("学校名称"))
                    .clazzCode(rowdata.getData("班级代码"))
                    .clazzName(rowdata.getData("班级名称"))
                    .zkyuwen(Double.parseDouble(rowdata.getData("语文1")))
                    .zkyingyu(Double.parseDouble(rowdata.getData("数学1")))
                    .zkshuxue(Double.parseDouble(rowdata.getData("英语1")))
                    .zkwuli(Double.parseDouble(rowdata.getData("物理")))
                    .zkhuaxue(Double.parseDouble(rowdata.getData("化学")))
                    .zkshengwu(Double.parseDouble(rowdata.getData("生物")))
                    .zkzhengzhi(Double.parseDouble(rowdata.getData("政治")))
                    .zklishi(Double.parseDouble(rowdata.getData("历史")))
                    .zkdili(Double.parseDouble(rowdata.getData("地理")))
                    .zkzonghe(Double.parseDouble(rowdata.getData("综合1")))
                    .zkzf(Double.parseDouble(rowdata.getData("总分1")))
                    .gkyuwen(Double.parseDouble(rowdata.getData("语文")))
                    .gkyingyu(Double.parseDouble(rowdata.getData("英语")))
                    .gkshuxue(Double.parseDouble(rowdata.getData("数学")))
                    .gkzonghe(Double.parseDouble(rowdata.getData("综合")))
                    .gkzf(Double.parseDouble(rowdata.getData("总分")))
                    .build();

            if (studentCj.getWl() == 1) {
                String clazzName = studentCj.getClazzName();
                List<String> tmp = Lists.newArrayList();
                Set<Boolean> changeTeacher = Sets.newHashSet();
                TeacherInfo teacherInfo = teacherDataset.get("语文." + clazzName);
                studentCj.setYuwen(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("英语." + clazzName);
                studentCj.setYingyu(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("数学." + clazzName);
                studentCj.setShuxue(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("物理." + clazzName);
                studentCj.setWuli(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("化学." + clazzName);
                studentCj.setHuaxue(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("生物." + clazzName);
                studentCj.setShengwu(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());

                studentCj.setTeachers(String.join("-", tmp));
                studentCj.setChangeTeacher(changeTeacher.size() != 1);
            } else {
                String clazzName = studentCj.getClazzName();
                List<String> tmp = Lists.newArrayList();
                Set<Boolean> changeTeacher = Sets.newHashSet();
                TeacherInfo teacherInfo = teacherDataset.get("语文." + clazzName);
                studentCj.setYuwen(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("英语." + clazzName);
                studentCj.setYingyu(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("数学." + clazzName);
                studentCj.setShuxue(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("政治." + clazzName);
                studentCj.setZhengzhi(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("历史." + clazzName);
                studentCj.setLishi(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());
                teacherInfo = teacherDataset.get("地理." + clazzName);
                studentCj.setDili(teacherInfo);
                tmp.add(teacherInfo.getGrade3());
                changeTeacher.add(teacherInfo.isChange());

                studentCj.setTeachers(String.join("-", tmp));
                studentCj.setChangeTeacher(changeTeacher.size() != 1);
            }

            Map<String, Map<String, String>> index = Maps.newHashMap();
            Map<String, String> id = Maps.newHashMap();
            id.put("_id", studentCj.getZkzh());
            index.put("index", id);
            FileUtils.write(root.resolve(year + ".json").toFile(), Json2.toJson(index) + "\n", "UTF-8", true);
            FileUtils.write(root.resolve(year + ".json").toFile(), Json2.toJson(studentCj) + "\n", "UTF-8", true);
        }
        process.close();
    }

}
