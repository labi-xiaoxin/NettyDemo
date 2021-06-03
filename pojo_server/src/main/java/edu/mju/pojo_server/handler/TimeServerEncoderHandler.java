package edu.mju.pojo_server.handler;

import edu.mju.pojo_server.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeServerEncoderHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime unixTime = (UnixTime) msg;
        ByteBuf buffer = ctx.alloc().buffer(4);
        buffer.writeInt((int)unixTime.getValue());
        ctx.write(buffer,promise);
    }
}
