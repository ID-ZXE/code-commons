package com.github.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author hangs.zhang
 * @date 2020/04/24 23:23
 * *****************
 * function:
 */
public class NIOClient {

    public static void main(String[] args) {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        try (Scanner scanner = new Scanner(System.in)) {
            SocketChannel channel;
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress("localhost", 8888));
            while (true) {
                System.out.println("put message to Server:");
                String str = scanner.nextLine();
                if (Objects.equals(str, "bye")) break;

                writeBuffer.clear();
                writeBuffer.put(str.getBytes());
                writeBuffer.flip();
                channel.write(writeBuffer);
                readBuffer.clear();
                int read = channel.read(readBuffer);
                if (read == -1) {
                    break;
                }
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                System.out.println("from server:" + new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
