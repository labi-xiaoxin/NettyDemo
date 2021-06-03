package edu.mju.time_server.handler;

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
        //分配一个包含这个消息的新的缓冲。
        // ctx.alloc()：获取当前ByteBufAllocator。int 4字节
        ByteBuf byteBuf = ctx.alloc().buffer(4);
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        byteBuf.writeInt(currentTime);
        System.out.println(currentTime);
        ChannelFuture channelFuture = ctx.writeAndFlush(byteBuf);

//        channelFuture.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                assert channelFuture == future;
//                ctx.close();
//            }
//        });

        //上面等同于如下
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
