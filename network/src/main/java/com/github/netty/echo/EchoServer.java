package com.github.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author hangs.zhang
 * @date 2019/10/19 20:09
 * *****************
 * function:
 */
public class EchoServer {

    public int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(8090).start();
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 此处的handler如果不new的话,第二次会抛出ChannelPipelineException异常 is not a @Sharable handler, so can't be added or removed multiple times.
                            // 除非handler被标记为@Sharable
                            socketChannel.pipeline().addFirst(new EchoServerInHandler());
                            socketChannel.pipeline().addFirst(new EchoServerOutHandler());
                        }
                    });
            // 异步绑定服务器 并且阻塞等待绑定完成
            ChannelFuture channelFuture = bootstrap.bind().sync();
            // 阻塞式的获取CloseFuture
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 释放所有资源
            bossGroup.shutdownGracefully().sync();
        }
    }

}
