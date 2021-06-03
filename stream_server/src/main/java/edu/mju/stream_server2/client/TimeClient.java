package edu.mju.stream_server2.client;

import edu.mju.stream_server2.handler.TimeClientDecoderHandler;
import edu.mju.stream_server2.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

    private int port;
    private String host;

    public TimeClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {

        EventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        try {
            //非服务端的channel
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEventLoopGroup)
                    //在客户端channel被创建时使用
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(new TimeClientDecoderHandler(),new TimeClientHandler());
                        }
                    });

            //启动客户端
            ChannelFuture sync = bootstrap.connect(host,port).sync();

            //等待连接关闭
            sync.channel().closeFuture().sync();
        }finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
