package com.newyu.domain.exam;

import lombok.*;

/**
 * ClassName: Item <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 上午11:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"examId", "subjectId", "num"})
@ToString(of = {"num", "name", "score"})
@Builder
public class Item {
    private int id;
    private Long examId;
    private Long subjectId;
    private String name;
    private double score;

    private String knowledge = "";
    private String ability = "";
    private String titleType = "";

    private String bigTitleName = "";
    private String smallTitleName = "";

    /**
     * 1 单项选择题 2 多项选择题 0非选择题
     */

    private ItemType itemType = ItemType.Not_Select;
    /**
     * 正确选项
     */
    private String answer = "";
    /**
     * 题目选项列表,默认为ABCD
     */
    private String fullOptional = "";
    /**
     * 综合科目，如文理综
     */
    private String otherSubject = "";
    /**
     * 是否是选做题的一道选题
     */
    private boolean choice = false;
    /**
     * 选做题规则："从12,13中选1题每题10分"
     */
    private String choiceInfo = "";
    /**
     * 非选择题对应的成绩数据表中的字段索引，需要根据它查找对应的项
     * 题目对应成绩文件的规则
     * 1.选择题 name 代表分数 name+选项代表选项  如果填写了该字段，就是字段 filedName代替name
     * 2.主观题与选择题类似就是没有选项
     */
    private String fieldName = "";
    /**
     * 顺序号
     */
    private int displayOrder = 0;

    private String titleBlock;

}
