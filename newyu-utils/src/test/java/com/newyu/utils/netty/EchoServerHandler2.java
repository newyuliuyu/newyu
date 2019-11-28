package com.newyu.utils.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.text.MessageFormat;

/**
 * ClassName: EchoServerHandler <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-10-16 上午11:05 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class EchoServerHandler2 extends ChannelHandlerAdapter {
    int counter = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(MessageFormat.format("this is [{0}] timers receive client:[{1}]", ++counter, msg));
        ctx.writeAndFlush(msg);
    }
}
