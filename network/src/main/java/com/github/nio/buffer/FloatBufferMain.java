package com.github.nio.buffer;

import java.nio.FloatBuffer;

/**
 * @author hangs.zhang
 * @date 2020/04/24 22:49
 * *****************
 * function:
 */
public class FloatBufferMain {

    public static void main(String[] args) {
        // 创建FloatBuffer,容量10,单位float,也就是说创建的buffer放的下10个 float
        FloatBuffer buffer = FloatBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            float f = (float) Math.sin((((float) i) / 10) * (2 * Math.PI));
            // position ++
            buffer.put(f);
        }
        // position = 0
        buffer.flip();
        while (buffer.hasRemaining()) {
            // position ++
            float f = buffer.get();
            System.out.println(f);
        }
    }

}
