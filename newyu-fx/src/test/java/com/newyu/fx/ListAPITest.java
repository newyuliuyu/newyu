package com.newyu.fx;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    public void groupBy() throws Exception {

        List<String> items = Arrays.asList("apple", "apple", "banana",
                "apple", "orange", "banana", "papaya");

        Map<String, List<String>> dataset = items.stream().collect(Collectors.groupingBy(x -> x));

        System.out.println();

    }
}
