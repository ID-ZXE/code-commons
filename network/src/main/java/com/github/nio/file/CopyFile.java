package com.github.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hangs.zhang
 * @date 2020/04/24 22:52
 * *****************
 * function:
 */
public class CopyFile {

    static public void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java CopyFile infile outfile");
            System.exit(1);
        }
        String infile = args[0];
        String outfile = args[1];

        // 从流中获取通道
        FileInputStream inputStream = new FileInputStream(infile);
        FileOutputStream outputStream = new FileOutputStream(outfile);

        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            // 读入之前要清空
            // 并不是真正的清空数组，而是清空记忆，让position为0
            buffer.clear();
            // position自动前进
            int r = inputStreamChannel.read(buffer);
            if (r == -1) {
                break;
            }
            // 同时将limit赋值为position 将position赋值为0
            // position = 0; limit=读到的字节数;
            buffer.flip();
            // 从buffer中读取之后,写入到文件
            outputStreamChannel.write(buffer);
        }
    }

}
