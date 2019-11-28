package com.newyu.utils.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * ClassName: EchoClientHandler <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-10-16 上午11:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class EchoClientHandler2 extends ChannelHandlerAdapter {
    int counter=0;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Unexpected exception from downstream : "
                + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = userInfos(10);
        for (UserInfo userInfo : userInfos) {
            ctx.write(userInfo);
        }
        ctx.flush();
    }

    private UserInfo[] userInfos(int sendNumber) {
        UserInfo[] userInfos = new UserInfo[sendNumber];
        for (int i = 0; i < sendNumber; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.age = i;
            userInfo.name = "ABCDEFG=>" + i;
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(MessageFormat.format("this[{0}] times receive server:[{1}]",++counter,msg));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

       ctx.flush();
    }
}
