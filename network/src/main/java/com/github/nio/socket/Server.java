package com.github.nio.socket;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/04/24 23:22
 * *****************
 * function:
 */
public class Server implements Runnable {

    private Selector selector;

    private final ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private final ByteBuffer wirteBuffer = ByteBuffer.allocate(1024);

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }

    public Server() {
        init();
        System.out.println("server start");
    }

    public void init() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(8888));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            // 事件监听
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        accept(key);
                    }
                    if (key.isReadable()) {
                        read(key);
                    }
                    if (key.isWritable()) {
                        write(key);
                    }
                }
            }
        }
    }

    public void read(SelectionKey key) {
        readBuffer.clear();
        SocketChannel channel = (SocketChannel) key.channel();
        int res = -1;
        try {
            res = channel.read(readBuffer);
            if (res == -1) {
                key.channel().close();
                key.cancel();
            }
            readBuffer.flip();
            byte[] bytes = new byte[readBuffer.remaining()];
            readBuffer.get(bytes);
            System.out.println("from" + channel.getRemoteAddress() + ":" + new String(bytes));
            // 事件切换
            channel.register(selector, SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(SelectionKey key) {
        try {
            wirteBuffer.clear();
            SocketChannel channel = (SocketChannel) key.channel();
            System.out.println("put message to client");
            String str = scanner.nextLine();
            wirteBuffer.put(str.getBytes());
            wirteBuffer.flip();
            channel.write(wirteBuffer);
            // 事件切换
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverChannel.accept();
            // 设置为非阻塞
            channel.configureBlocking(false);
            // 连接之后监听读事件
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
