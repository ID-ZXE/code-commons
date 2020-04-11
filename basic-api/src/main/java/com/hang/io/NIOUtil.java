package com.hang.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hangs.zhang
 * @date 2018/6/14
 * *********************
 * function:
 */
public class NIOUtil {

    public static void nioCopyFile(String resource, String destination) throws IOException {
        FileInputStream fis = new FileInputStream(resource);
        FileOutputStream fos = new FileOutputStream(destination);
        FileChannel readChannel = fis.getChannel();
        FileChannel writeChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            // 并不是真正的清空数组，清空记忆，让position为0
            buffer.clear();
            int len = readChannel.read(buffer);
            if (len == -1) break;
            // 切换到写模式
            // 同时将limit赋值为position 将position赋值为0
            buffer.flip();
            writeChannel.write(buffer);
        }
        readChannel.close();
        writeChannel.close();
    }

    public static void main(String[] args) throws IOException {
        NIOUtil.nioCopyFile("D:\\file\\file\\resource.txt", "D:\\file\\file\\destination.txt");
    }

}
