package com.newyu.utils.io.file.spi;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.newyu.utils.tool.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: ProcessData <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-9 下午3:01 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ProcessData {

    private Path rootPath = Paths.get("/home/liuyu/test/test-data/溪洛渡高中成绩统计表20190706");
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Test
    public void test2() throws Exception {

        System.out.println(decimalFormat.format(12.10000001));

//        String test = "溪高2017届（理1班）班学生成绩统计表";
//
//        Pattern pattern = Pattern.compile(".*（(.*)）.*");
//        Matcher matcher = pattern.matcher(test);
//        if (matcher.find()) {
//            String s1 = matcher.group(0);
//            String s2 = matcher.group(1);
//            System.out.println();
//        }


        System.out.println();
    }

    @Test
    public void testSheetName() throws Exception {


//        Path path = rootPath.resolve("溪高（2017届）学生成绩统计表.xls");
//        int year = 2017;
//        Path path = rootPath.resolve("溪高（2018届）学生成绩统计表.xls");
//        int year=2018;
//        Path path = rootPath.resolve("溪高（2019届）学生成绩统计表(2).xls");
//        int year=2019;

        rootPath = Paths.get("/home/liuyu/test/test-data/永一中成绩统计表2019.7.6");
//        Path path = rootPath.resolve("永一中（2017届）学生成绩统计表.xls");
//        int year=2017;
//        Path path = rootPath.resolve("永一中（2018届）学生成绩统计表.xls");
//        int year=2018;
        Path path = rootPath.resolve("永一中（2019届）学生成绩统计表.xls");
        int year=2019;

        Workbook wb = WorkbookFactory.create(FileUtil.read(path.toString()));

        Iterator<Sheet> iterator = wb.sheetIterator();
        while (iterator.hasNext()) {
            sheetProcess(iterator.next(), year);
//            break;
        }


    }

    private void sheetProcess(Sheet sheet, int year) throws Exception {
        int rowNum = sheet.getPhysicalNumberOfRows();
        List<List<String>> values = Lists.newArrayList();
        for (int i = 0; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            List<String> cellValues = Lists.newArrayList();
            for (Cell cell : row) {
                String value = parse(cell);
                cellValues.add(value);
            }
            values.add(cellValues);
        }

        Path path = rootPath.resolve(year + ".csv");
        String headerString = "姓名,性别,语文,数学,英语,物理,化学,政治,历史,地理,生物,体育,信息,音乐,美术,总分,语文,数学,英语,综合,总分,班级\n";
        FileUtils.write(path.toFile(), headerString, "UTF-8", true);
        String[] header = headerString.split(",");
        int idx = 0;
        String className = "";
        for (List<String> line : values) {
            if (idx++ == 0) {
                String name = line.get(0);
                Pattern pattern = Pattern.compile(".*（(.*)）.*");
                Matcher matcher = pattern.matcher(name);
                if (matcher.find()) {
                    className = matcher.group(1);
                    className = className.trim();
                }
                System.out.println(className);
                continue;
            } else if (idx <= 3) {
                continue;
            }
            if (line.get(1).equals("")) {
                continue;
            }
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < header.length - 1; i++) {
                String value = line.get(i + 1);
                String a = "";
                if (i > 1 && !value.equals("")) {
                    value = decimalFormat.format(Double.parseDouble(value));
                }
                text.append(value).append(",");
            }
            text.append(className).append("\n");
            System.out.println(text);
            FileUtils.write(path.toFile(), text.toString(), "UTF-8", true);

        }

        int size = header.length;
        path = rootPath.resolve(year + "bj.csv");
        headerString = "科目,高一,高二,高三,班级,有老师改变\n";
        FileUtils.write(path.toFile(), headerString, "UTF-8", true);
        header = headerString.split(",");
        idx = 0;
        for (List<String> line : values) {
            if (idx++ <= 1) {
                continue;
            }
            try {
                String value = line.get(size);
                if (value.equals("")) {
                    break;
                }
            } catch (Exception e) {
                break;
            }


            StringBuilder text = new StringBuilder();
            Set<String> teacherNameSet = Sets.newHashSet();
            for (int i = 0; i < header.length - 2; i++) {
                String value = "";
                try {
                    value = line.get(size + i);
                }catch (Exception e){
                    value = "";
                }
                teacherNameSet.add(value);
                text.append(value).append(",");
            }
            text.append(className).append(",").append(teacherNameSet.size() != 2).append("\n");
            System.out.println(text);
            FileUtils.write(path.toFile(), text.toString(), "UTF-8", true);
        }

        System.out.println();
    }

    private String parse(Cell cell) {
        if (cell == null) {
            return "";
        }
        String result = new String();
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                // 数字类型
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 处理日期格式、 时间格式
                    Date date = cell.getDateCellValue();
                    result = dateFormat.format(date);
                } else {
                    double va = cell.getNumericCellValue();
                    if (va == (int) va) {
                        // 去掉数值类型后面的".0"
                        result = String.valueOf((int) va);
                    } else {
                        // result = String.valueOf(va); //if the double value is too
                        // big, it will be displayed in E-notation
                        result = decimalFormat.format(va);
                    }
                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                try {
                    result = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    result = String.valueOf(cell.getRichStringCellValue()).trim();
                }
                break;
            case Cell.CELL_TYPE_STRING:
                // String类型
                result = cell.getRichStringCellValue().toString().trim();
                break;
            case Cell.CELL_TYPE_BLANK:
                result = "";
                break;
            default:
                result = "";
                break;
        }
        return result.trim().replaceAll("\\u00A0", "");
    }

}
