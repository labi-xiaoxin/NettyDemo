package edu.mju.pojo_server.handler;

import edu.mju.pojo_server.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 连接时处理。
     * 完成一个当前时间32位整数消息构建
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        UnixTime unixTime = new UnixTime();
        ChannelFuture channelFuture = ctx.writeAndFlush(unixTime);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
