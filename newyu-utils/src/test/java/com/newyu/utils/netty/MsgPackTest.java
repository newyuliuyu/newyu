package com.newyu.utils.netty;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.util.List;

/**
 * ClassName: MsgPackTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-10-16 下午1:38 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class MsgPackTest {

    @Test
    public void test01() throws Exception{
        List<String> data = Lists.newArrayList();
        data.add("test1");
        data.add("test2");

        MessagePack msgPack=new MessagePack();
        byte[] raw = msgPack.write(data);

       List<String> data2 =  msgPack.read(raw, Templates.tList(Templates.TString));

        System.out.println(data2.get(0));
        System.out.println(data2.get(1));
    }
}
