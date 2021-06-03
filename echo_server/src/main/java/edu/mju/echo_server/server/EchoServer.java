package edu.mju.echo_server.server;

import edu.mju.echo_server.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private int port;

    public EchoServer(int port){
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
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    })
                    //设置指定的channel配置参数
                    .option(ChannelOption.SO_BACKLOG,128)
                    //设置指定的父channel配置参数
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture sync = serverBootstrap.bind(port).sync();

            sync.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        }
    }
}
