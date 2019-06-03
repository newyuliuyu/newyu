package com.newyu.domain.exam;

import com.newyu.utils.mybatis.CodeEnum;

/**
 * ClassName: ExamState <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 上午10:48 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public enum ExamState implements CodeEnum {
    /**
     * 新建考试
     */
    create("新建考试", 0),
    /**
     * 正在分析
     */
    analyzing("正在分析", 2),
    /**
     * 分析成功
     */
    analyzeSucess("分析成功", 1),
    /**
     * 分析失败
     */
    analyzeFail("分析失败", 3),
    /**
     * 分析等待
     */
    analyzeWait("分析等待", 4);

    private String name;
    private int code;

    private ExamState(String name, int code) {
        this.name = name;
        this.code = code;
    }


    @Override
    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
