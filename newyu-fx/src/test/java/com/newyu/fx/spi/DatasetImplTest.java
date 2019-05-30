package com.newyu.fx.spi;

import com.newyu.domain.exam.Student;
import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.exam.WLType;
import com.newyu.domain.fx.GroupInfo;
import com.newyu.fx.Dataset;
import com.newyu.fx.GroupDataset;
import org.junit.Test;

import java.util.List;

/**
 * ClassName: DatasetImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-26 下午3:54 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */

public class DatasetImplTest {

    @Test
    public void test() throws Exception {
        Dataset<StudentCj> dataset = create();
        List<StudentCj> studentCjs = dataset.filter(x -> x.getStudent().getZkzh().equals("1")).getList();
        System.out.println();
    }

    @Test
    public void test2() throws Exception {
        Dataset<StudentCj> dataset = create();
        List<StudentCj> studentCjs = dataset.getList();
        System.out.println();
    }

    @Test
    public void filterSort() throws Exception {
        Dataset<StudentCj> dataset = create();
        GroupInfo groupInfo = new GroupInfo();
        List<StudentCj> studentCjs = dataset.sort((x, y) -> {
            return y.getStudent().getZkzh().compareTo(x.getStudent().getZkzh());
        }).filter(x -> !x.getStudent().getZkzh().equals("10")).getList();
        System.out.println();
    }

    @Test
    public void filterGroup() throws Exception {
        Dataset<StudentCj> dataset = create();
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.add("wl");
        List<GroupDataset<StudentCj>> studentCjs = dataset.filter(x -> {
            return !x.getStudent().getZkzh().equals("1") && !x.getStudent().getZkzh().equals("5");
        }).getGroupDataset(groupInfo);
        System.out.println();
    }


    public Dataset<StudentCj> create() {
        DatasetImpl dataset = new DatasetImpl();
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("1").name("1").wl(WLType.Like).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("2").name("2").wl(WLType.Like).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("3").name("3").wl(WLType.Like).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("4").name("4").wl(WLType.Like).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("5").name("5").wl(WLType.Like).build()).build());

        dataset.add(StudentCj.builder().student(Student.builder().zkzh("6").name("6").wl(WLType.Wenke).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("7").name("7").wl(WLType.Wenke).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("8").name("8").wl(WLType.Wenke).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("9").name("9").wl(WLType.Wenke).build()).build());
        dataset.add(StudentCj.builder().student(Student.builder().zkzh("10").name("10").wl(WLType.Wenke).build()).build());
        return dataset;
    }

}
