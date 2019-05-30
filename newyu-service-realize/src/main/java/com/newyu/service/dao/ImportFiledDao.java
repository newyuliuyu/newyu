package com.newyu.service.dao;

import com.newyu.domain.commons.ImportFiled;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ImportFiledDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-16 下午1:21 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface ImportFiledDao {
    void saveFiled(@Param("examId") long examId,
                   @Param("fileds") List<ImportFiled> importFileds);

    void deleteFiled(@Param("examId") long examId,
                     @Param("subjectId") long subjectId);
}
