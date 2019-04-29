package com.newyu.fx;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.fx.FxFiled;
import com.newyu.domain.org.Clazz;
import com.newyu.domain.org.School;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: GroupValueTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 上午10:31 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class GroupValueTest {

    @Test
    public void test01() throws Exception {
        GroupValue groupValue1 = new GroupValue();
        groupValue1.addValue("1", 1);

        GroupValue groupValue2 = new GroupValue();
        groupValue2.setParent(groupValue1);
        groupValue2.addValue("2", 2);


        GroupValue groupValue4 = new GroupValue();
        groupValue4.addValue("1", 1);

        GroupValue groupValue3 = new GroupValue();
        groupValue3.setParent(groupValue4);
        groupValue3.addValue("2", 2);

        System.out.println(groupValue3.equals(groupValue2));

        System.out.println();
    }

    @Test
    public void test02() throws Exception {

        School school = School.builder().code("schoolcode1").name("schoolname1").build();
        Clazz clazz = Clazz.builder().code("clazzcode1").name("clazzname1").build();
        clazz.setSchool(school);

        Map<String, Object> valueMap = Maps.newHashMap();
        valueMap.put("schoo", school);
        valueMap.put("clazz", clazz);
        valueMap.put("a", 1);
        valueMap.put("b", "test");

        Object fxDatas = valueMap.entrySet().stream().flatMap(x -> {
            List<FxData> tmp = createFxData(x.getKey(), x.getValue());
            return tmp.stream();
        }).collect(Collectors.toList());

        System.out.println();

    }

    private List<FxData> createFxData(String key, Object value) {
        List<String> names = getName(key, value);
        List<Object> values = getValue(value);
        List<FxData> fxDatas = Lists.newArrayList();
        for (int i = 0; i < names.size(); i++) {
            fxDatas.add(FxData.builder().name(names.get(i)).value(values.get(i)).build());
        }
        return fxDatas;
    }


    private List<String> getName(String key, Object value) {
        List<String> result = Lists.newArrayList();
        FxFiled fxFiled = value.getClass().getAnnotation(FxFiled.class);
        if (fxFiled != null && fxFiled.of().length > 0) {
            String[] fileds = fxFiled.of();
            for (String filed : fileds) {
                filed = key + "_" + filed;
                filed = filed.replaceAll("\\.", "_");
                result.add(filed);
            }
        } else {
            key = key.replaceAll("\\.", "_");
            result.add(key);
        }
        return result;
    }

    private List<Object> getValue(Object value) {
        List<Object> result = Lists.newArrayList();
        FxFiled fxFiled = value.getClass().getAnnotation(FxFiled.class);
        if (fxFiled != null && fxFiled.of().length > 0) {
            String[] fileds = fxFiled.of();
            try {
                for (String filed : fileds) {
                    Object obj = PropertyUtils.getProperty(value, filed);
                    result.add(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            result.add(value.toString());
        }
        return result;
    }


}