package edu.mju.pojo_server.handler;

import edu.mju.pojo_server.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 从服务端读取的数据
     *
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        UnixTime unixTime = (UnixTime)msg;
        System.out.println(unixTime);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
