package com.newyu.utils.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

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
public class EchoClientHandler extends ChannelHandlerAdapter {
    int counter=0;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] data="hi,Lilinfeng. welcome to netty.$_".getBytes();
        for (int i=0;i<10;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(data));
        }
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
