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
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/04/24 23:22
 * *****************
 * function:
 * <p>
 * 可以使用telnet 127.0.0.1 8888命令进行测试
 * CTRL+]退出telnet
 * <p>
 * 一个NIO线程处理所有TCP连接,不再有BIO那种连接数短缺的问题
 * 同一时刻可以处理多个连接
 */
public class NIOServer implements Runnable {

    private Selector selector;

    private final ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        new Thread(new NIOServer()).start();
    }

    public NIOServer() {
        init();
        System.out.println("server start");
    }

    public void init() {
        try {
            // 多路复用器 收集channel的事件
            // 底层实现与操作系统有关
            selector = Selector.open();
            // 1. 获取通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            // 2. 绑定连接
            serverSocketChannel.bind(new InetSocketAddress(8888));
            // 3. 初始时监听accept事件
            // serverSocketChannel 只关心 accept事件
            // socketChannel 还会关心读和写事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            // 这个地方select函数是阻塞的 不会导致cpu空转
            // linux2.6之后调用的是底层的epoll
            // 不会遍历所有的连接 只挑出有io事件的连接
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            // 事件监听
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 事件处理完之后进行删除
                iterator.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            }
        }
    }

    public void read(SelectionKey key) {
        readBuffer.clear();
        SocketChannel channel = (SocketChannel) key.channel();

        int len = -1;
        try {
            // 这里如果是阻塞模式的话 读取不到数据会阻塞
            // 非阻塞模式的话 会跳过
            len = channel.read(readBuffer);
            if (len == -1) {
                System.out.println(channel.getRemoteAddress() + ":close");
                key.channel().close();
                key.cancel();
                return;
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
            writeBuffer.clear();
            SocketChannel channel = (SocketChannel) key.channel();
            // 发送反馈
            writeBuffer.put("ok!\n".getBytes());
            writeBuffer.flip();
            channel.write(writeBuffer);
            // 事件切换 不过此时连接有可能断开 再次切换到read事件时 会抛出异常
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accept(SelectionKey key) {
        try {
            // accept事件获取的channel是ServerSocketChannel
            // read和write获取到的都是SocketChannel
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            // 在非阻塞模式下,accept方法会立刻返回,无连接时将返回null
            // 底层调用的是linux内核的accept
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
