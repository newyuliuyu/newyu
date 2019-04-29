package com.newyu.fx;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.newyu.domain.exam.Subject;
import com.newyu.domain.fx.ScoreInfo;
import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ClassName: ListAPITest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午3:35 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ListAPITest {

    @Test
    public void testFilter() throws Exception {

        List<String> list = Lists.newArrayList();


    }

    @Test
    public void findFirst() throws Exception{
        List<Subject> subjects = Lists.newArrayList();
        subjects.add(Subject.builder().name("name1").build());
        subjects.add(Subject.builder().name("name2").build());
        subjects.add(Subject.builder().name("name3").build());


        Optional<Subject> subjectOptional = subjects.stream().filter(x->{
            System.out.println("****************************");
            return x.getName().equals("name1");
        }).findFirst();

        System.out.println();
    }

    @Test
    public void testFilter2() throws Exception {

//        Multimaps.newListMultimap();
        Multimap<String, String> ll = ArrayListMultimap.create();
        ll.put("1", "1");
        ll.put("1", "2");
        ll.put("1", "3");
        ll.put("2", "1");
        ll.put("2", "2");
        ll.put("2", "3");
        System.out.println();
    }

    @Test
    public void groupBy() throws Exception {

        List<String> items = Arrays.asList("apple", "apple", "banana",
                "apple", "orange", "banana", "papaya");

//        Map<String, List<String>> dataset = items.stream().collect(Collectors.groupingBy(x -> x));
        List<String> data = items.stream()
                .filter(x -> x.equals("apple"))
                .sorted()
                .collect(Collectors.toList());
        System.out.println();

    }

    @Test
    public void groupByCount() throws Exception {

        List<String> items = Arrays.asList("apple", "apple", "banana",
                "apple", "orange", "banana", "papaya");

//        Map<String, List<String>> dataset = items.stream().collect(Collectors.groupingBy(x -> x));
//        Object obj = items.stream()
//                .collect(Collectors.groupingBy(x -> x, Collectors.reducing(0L, e -> 1L, Long::sum)));
        Object obj = items.stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.reducing("aaa==", String::concat)));

        System.out.println();

    }
    @Test
    public void sum() throws Exception {

        List<Integer> items = Arrays.asList(1,2,3,4,5,6,7,8,9);

        Object obj = items.stream();

        System.out.println();

    }

    @Test
    public void scoreInfo() throws Exception {

        List<ScoreInfo> scoreInfos = Lists.newArrayList();
        scoreInfos.add(ScoreInfo.builder()
                .score(100d).num(20).build());
        scoreInfos.add(ScoreInfo.builder()
                .score(80d).num(20).build());
        scoreInfos.add(ScoreInfo.builder()
                .score(76d).num(20).build());
        scoreInfos.add(ScoreInfo.builder()
                .score(50d).num(20).build());
        scoreInfos.add(ScoreInfo.builder()
                .score(40d).num(20).build());

        double sumScore  = scoreInfos.stream().reduce(0d,(v1,v2)->v1+v2.getScore()*v2.getNum(),(v1,v2)->v1+v2);

        System.out.println(sumScore);
        System.out.println(100*20+80*20+76*20+50*20+40*20);

        System.out.println();


        DecimalFormat format = new DecimalFormat("0.##");
        format.setRoundingMode(RoundingMode.HALF_UP);

        System.out.println(format.format(0.7895123));
        System.out.println(format.format(50.000));

    }
    @Test
    public void join() throws Exception {
        List<String> data=Lists.newArrayList("1","2","3");
        System.out.println(String.join(",",data.stream().limit(5).collect(Collectors.toList())));
    }


}
