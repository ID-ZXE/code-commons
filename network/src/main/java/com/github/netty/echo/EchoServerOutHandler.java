package com.github.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * @author hangs.zhang
 * @date 2020/04/25 14:34
 * *****************
 * function:
 */
public class EchoServerOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        String response = "I am ok!";
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.writeAndFlush(encoded, promise);

        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("out success");
                }
            }
        });
    }
}
