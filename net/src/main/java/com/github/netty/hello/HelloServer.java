package com.github.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hangs.zhang
 * @date 2019/10/19 15:08
 * *****************
 * function: 实现客户端发送请求, 服务器返回hello
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {
        // 1 定义线程组
        // 主线程组 用于接收客户端链接 但是不处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组 处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // netty服务器的创建, ServerBootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    // 设置nio双向通道
                    .channel(NioServerSocketChannel.class)
                    // 子处理器, 用户处理workerGroup
                    // 每一个channel由多个handler组成pipeline
                    .childHandler(new HelloServerInitializer());

            // 绑定端口 启动server 启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            // 监听关闭的channel 设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
