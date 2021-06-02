package edu.mju.discard_server.server;

import edu.mju.discard_server.handler.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: wyp
 * @Date: 2021/6/2 23:19
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port){
        this.port = port;
    }

    public void start(){
        //接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理接收进来的连接
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //引导器
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    //处理最近接收进来的Channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    //设置指定的channel实现的配置参数
                    .option(ChannelOption.SO_BACKLOG,128)
                    //父channel接收到的连接
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
            ;

            ChannelFuture sync = serverBootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
