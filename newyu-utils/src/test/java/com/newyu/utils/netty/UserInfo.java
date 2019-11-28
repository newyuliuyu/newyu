package com.newyu.utils.netty;

import org.msgpack.annotation.Message;

/**
 * ClassName: UserInfo <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-10-16 下午2:08 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Message
public class UserInfo {
    public Integer age;
    public String name;
}
