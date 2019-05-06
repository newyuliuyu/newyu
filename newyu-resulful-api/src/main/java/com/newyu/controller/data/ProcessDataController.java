package com.newyu.controller.data;

import com.newyu.domain.commons.UploadFile;
import com.newyu.domain.dto.ExamDatasource;
import com.newyu.domain.dto.SubjectAddItemCj;
import com.newyu.domain.dto.SubjectDatasource;
import com.newyu.utils.excel.ExcelTable;
import com.newyu.utils.excel.ExcelTableBuilder;
import com.newyu.utils.excel.Row;
import com.newyu.utils.excel.Table;
import com.newyu.utils.spring.ModelAndViewFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * ClassName: ProcessDataController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-5 上午11:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessDataController {

    @RequestMapping("/exam")
    public ModelAndView exam(@RequestBody ExamDatasource examDatasource,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        log.debug("处理一次考试数据....");
        return ModelAndViewFactory.instance("").build();
    }

    @RequestMapping("/update/subject/{examId}")
    public ModelAndView updateSubject(@PathVariable long examId,
                                      @RequestBody List<SubjectDatasource> subjectDatasources,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug("处理一次考试科目数据....");
        return ModelAndViewFactory.instance("").build();
    }

    @RequestMapping("/update/subject/add/item")
    public ModelAndView updateSubjectAddItem(@PathVariable long subjectId,
                                             @RequestBody SubjectAddItemCj subjectAddItemCj,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        log.debug("处理一次考试科目数据....");
        return ModelAndViewFactory.instance("").build();
    }

    @RequestMapping("/update/bmk/{examId}")
    public ModelAndView updateBmk(@PathVariable long examId,
                                  @RequestBody List<UploadFile> bmks,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        log.debug("处理一次考试报名库数据....");
        return ModelAndViewFactory.instance("").build();
    }

    @RequestMapping("/exam/onlyZf/{scoreBeginColumnIdx}")
    public ModelAndView examOnlyZf(@PathVariable() int scoreBeginColumnIdx,
                                   @RequestBody ExamDatasource examDatasource,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        log.debug("处理一次考试数据....");
        return ModelAndViewFactory.instance("").build();
    }


    @RequestMapping("/download/template/bmk")
    public void downloadTemplateBmk(HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {


        res.setCharacterEncoding("utf-8");
        res.setContentType("multipart/form-data");
        res.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("报名库".getBytes("UTF-8"), "iso-8859-1") + ".xls");

        OutputStream os = res.getOutputStream();

        Table table = new Table();
        Row row = new Row();
        table.addHeader(row);
        row.add("姓名", "准考证号", "学号", "学生唯一ID", "文理",
                "班级代码", "班级名称", "班级分组", "学校代码", "学校名称",
                "区县代码", "区县名称", "地市代码", "地市名称");
        ExcelTable et = new ExcelTable();
        et.addSheet("报名库");
        ExcelTableBuilder.createTable(et, table, 0, 0);
        et.save(os);
        os.close();
    }

    @RequestMapping("/download/template/cj")
    public void downloadTemplateCj(HttpServletRequest req,
                                   HttpServletResponse res) throws Exception {


        res.setCharacterEncoding("utf-8");
        res.setContentType("multipart/form-data");
        res.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("xx".getBytes("UTF-8"), "iso-8859-1") + ".xls");

        OutputStream os = res.getOutputStream();

        Table table = new Table();
        Row row = new Row();
        table.addHeader(row);
        row.add("准考证号", "学号", "学生唯一ID", "文理",
                "教学班代码", "教学班名称", "教学班分组", "缺考", "总分",
                "客观题总分", "主观题总分", "x题", "x题选项");
        ExcelTable et = new ExcelTable();
        et.addSheet("成績");
        ExcelTableBuilder.createTable(et, table, 0, 0);
        et.save(os);
        os.close();
    }

    @RequestMapping("/download/template/xmb")
    public void downloadTemplateXmb(HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {


        res.setCharacterEncoding("utf-8");
        res.setContentType("multipart/form-data");
        res.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("xx細目表".getBytes("UTF-8"), "iso-8859-1") + ".xls");

        OutputStream os = res.getOutputStream();

        Table table = new Table();
        Row row = new Row();
        table.addHeader(row);
        row.add("题号", "选择题类型", "正确答案", "可选项", "知识点", "能力结构", "题型", "科目", "满分", "是否选做题", "选做题规则", "对应字段", "题块", "大题号");

        row = new Row();
        row.setHeight(150);
        table.addBody(row);
        row.add("1.选择题类型：0表示非选做题 1表示单项选做题 2表示多项选做题\n" +
                "2.可选项:指选择题的备选答案如ABCD\n" +
                "3.科目:指综合科目需要拆分出子科目的时候使用；如果不是综合科目请不要填写\n" +
                "4.是否选做题：0表示不是选做题，1表示选做题\n" +
                "5.选做题规则:\"从{题号}中选{数量}题每题{满分}分\",如：从21,22-33中选择1题每题20分\n" +
                "6.对应字段：值成绩文件中小题分数对应的字段名称，如12题在成绩文件中为\"12题得分\"，这个就应该填写\"12题得分\"\n" +
                "7.题块:暂时不使用不用填写\n" +
                "8.系目表填写完毕以后请删除该模版中的说明文字");


        ExcelTable et = new ExcelTable();
        et.addSheet("細目表");
        ExcelTableBuilder.createTable(et, table, 0, 0);
        et.save(os);
        os.close();
    }
}
