package edu.mju.stream_server2.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeClientDecoderHandler extends ByteToMessageDecoder {
    /**
     * 当有新的数据接收，该方法会被调用，用来处理内部的累积缓冲。
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //缓冲区数据不够时，放任意数据
        if(in.readableBytes() < 4){
            return;
        }

        //当out添加一个对象时，意味着解码器解码成功，后台会丢弃累积缓冲区中读过的数据。
        out.add(in.readBytes(4));
    }
}
