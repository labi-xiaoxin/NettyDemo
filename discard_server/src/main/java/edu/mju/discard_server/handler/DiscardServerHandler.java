package edu.mju.discard_server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *  服务端channel处理器适配器
 * @Author: wyp
 * @Date: 2021/6/2 23:10
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每当从客户端收到新的数据时，这个方法会在收到新消息时被调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //丢弃接收的信息
//        ((ByteBuf)msg).release();

        //为了便于查看，输出消息
        ByteBuf msgByteBuf = (ByteBuf)msg;
        try {
            while(msgByteBuf.isReadable()){
                System.out.println((char)msgByteBuf.readByte());
                System.out.flush();
            }
        }finally {
            msgByteBuf.release();
        }
    }

    /**
     * 方法抛出异常时调用该方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
