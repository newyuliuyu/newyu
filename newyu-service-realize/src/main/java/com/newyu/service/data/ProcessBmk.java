package com.newyu.service.data;

import com.newyu.domain.commons.UploadFile;

import java.util.List;

/**
 * ClassName: ProcessBmk <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午1:48 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ProcessBmk {
    private long examId;
    private List<UploadFile> bmks;

    public ProcessBmk(long examId, List<UploadFile> bmks) {
        this.examId = examId;
        this.bmks = bmks;
    }

    public boolean process() {

        return true;
    }

}
