package edu.mju.pojo_server.server;

import edu.mju.pojo_server.handler.TimeServerEncoderHandler;
import edu.mju.pojo_server.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
    private int port;

    public TimeServer(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //引导器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel){
                            socketChannel.pipeline().addLast(new TimeServerEncoderHandler(),new TimeServerHandler());
                        }
                    })
                    //设置指定的channel配置参数
                    .option(ChannelOption.SO_BACKLOG,128)
                    //设置指定的父channel配置参数
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture sync = serverBootstrap.bind(port).sync();
            System.out.println("服务端已启动");
            sync.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        }
    }
}
