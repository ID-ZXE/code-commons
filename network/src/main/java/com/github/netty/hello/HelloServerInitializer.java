package com.github.netty.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author hangs.zhang
 * @date 2019/10/19 15:25
 * *****************
 * function: 初始化器 channel注册后会执行里面相应的初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 通过SocketChannel去获取相应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 通过管道添加handler httpServerCodec是netty提供的助手类
        // 当请求到服务端时 我们需要自己进行编解码 响应到客户端
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        // 自定义handler
        pipeline.addLast("customHandler", new HelloCustomHandler());
    }

}
