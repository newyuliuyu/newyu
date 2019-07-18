package com.newyu.utils;

import com.google.common.collect.Maps;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.FileProcessUtil;
import com.newyu.utils.io.file.Rowdata;
import com.newyu.utils.json.Json2;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Map;

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

    @Test
    public void test() throws Exception {
        String filePath = "/home/liuyu/test/test-data/cj/10/2017.xls";
        String filePath2 = "/home/liuyu/test/test-data/cj/10/2017.json";
        int year = 2017;
        FileProcess process = FileProcessUtil.getFileProcess(filePath);
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

            Map<String, Map<String, String>> index = Maps.newHashMap();
            Map<String, String> id = Maps.newHashMap();
            id.put("_id", studentCj.getZkzh());
            index.put("index", id);
            FileUtils.write(new File(filePath2), Json2.toJson(index) + "\n", "UTF-8", true);
            FileUtils.write(new File(filePath2), Json2.toJson(studentCj) + "\n", "UTF-8", true);
        }
        process.close();
    }

}
