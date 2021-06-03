package edu.mju.stream_server1.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 全局缓冲
     */
    private ByteBuf byteBuf;

    /**
     * 生命周期监听方法
     * 在 ChannelHandLer被添加到实际上下文并准备好处理事件之后调用。
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        byteBuf = ctx.alloc().buffer(4);
    }

    /**
     * 生命周期监听方法
     * 在ChannelHandler从实际上下文中移除后调用，并且它不再处理事件。
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        byteBuf.release();
        byteBuf = null;
    }

    /**
     * 从服务端读取的数据
     *
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf msgBuf = (ByteBuf)msg;
        //所有数据先添加进byeBuf
        byteBuf.writeBytes(msgBuf);
        msgBuf.release();

        //一旦累积大于等于4字节，才进行业务处理
        if(byteBuf.readableBytes() >= 4){
            long read = byteBuf.readUnsignedInt();
            System.out.println(new Date(read*1000));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
