package com.github.netty.embedded;

import com.github.netty.echo.EchoServerOutHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author hangs.zhang
 * @date 2020/04/25 14:58
 * *****************
 * function:
 */
public class MainTest {

    public static void main(String[] args) {
        ByteBuf byteBuffer = Unpooled.buffer();
        byteBuffer.writeInt(100);

        EmbeddedChannel channel = new EmbeddedChannel(new EchoServerOutHandler());
        channel.writeOutbound(byteBuffer);
    }

}
