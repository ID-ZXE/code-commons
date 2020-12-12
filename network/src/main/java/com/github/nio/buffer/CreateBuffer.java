package com.github.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author hangs.zhang
 * @date 2020/04/24 23:01
 * *****************
 * function:
 */
public class CreateBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 'a');
        buffer.put((byte) 'b');
        buffer.put((byte) 'c');
        buffer.flip();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
    }

}
