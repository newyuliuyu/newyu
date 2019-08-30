package com.newyu.utils.excel;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Random;

/**
 * ClassName: RankTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-24 下午5:50 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class RankTest {

    @Test
    public void test() throws Exception {
        Table table = new Table();
        table.addHeader(new Row().add("t0", "t1", "t2", "t3"));
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String g = "g2";
            if (i < 5) {
                g = "g1";
            }
            table.addBody(new Row().add(g, "v" + i, random.nextInt(100), Rank.builder().rankFiledName("t2").build()));
        }
        ExcelTableBuilder.instance().builder("/home/liuyu/test/test-data/cj/test.xls", table);
    }

    @Test
    public void group() throws Exception {
        Table table = new Table();
        table.addHeader(new Row().add("t0", "t1", "t2", "t3"));
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String g = "g2";
            if (i < 5) {
                g = "g1";
            }
            table.addBody(new Row().add(g, "v" + i, random.nextInt(100), Rank.builder().groupFiledName("t0").rankFiledName("t2").build()));
        }
        ExcelTableBuilder.instance().builder("/home/liuyu/test/test-data/cj/test.xls", table);
    }

    @Test
    public void filter() throws Exception {
        Table table = new Table();
        table.addHeader(new Row().add("t0", "t1", "t2", "t3"));
        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            String g = "g2";
            if (i < 5) {
                g = "g1";
            } else if (i > 10) {
                g = "g3";
            }
            if(i>10){
                table.addBody(new Row().add(g, "v" + i, random.nextInt(100),""));
            }else{
                table.addBody(new Row().add(g, "v" + i, random.nextInt(100), Rank.builder()

                        .rankFiledName("t2")
                        .filterFiledName("t0")
                        .filterVlaues(Lists.newArrayList("g3"))
                        .build()));
            }

        }
        ExcelTableBuilder.instance().builder("/home/liuyu/test/test-data/cj/test.xls", table);
    }

}