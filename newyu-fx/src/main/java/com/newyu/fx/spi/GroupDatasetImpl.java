package com.newyu.fx.spi;

import com.newyu.domain.exam.StudentCj;
import com.newyu.fx.GroupDataset;
import com.newyu.fx.GroupValue;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ClassName: DatasetImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 下午4:03 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class GroupDatasetImpl extends DatasetImpl implements GroupDataset {

    private GroupValue groupValue;

    public void setGroupValue(GroupValue groupValue) {
        this.groupValue = groupValue;
    }

    public void setStudentCjs(List<StudentCj> studentCjs) {
        for (StudentCj studentCj : studentCjs) {
            this.add(studentCj);
        }
    }

    @Override
    public GroupValue getGroupValue() {
        return groupValue;
    }
}
