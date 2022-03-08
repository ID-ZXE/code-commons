package com.github.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhanghang
 * @date 2022/3/8 11:57 下午
 * *****************
 * function:
 */
public class ReactorNIOServer {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8888)).start();
    }

}

class Reactor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocket;

    /**
     * Reactor初始化
     */
    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        // 非阻塞
        serverSocket.configureBlocking(false);

        // 分步处理,第一步,接收accept事件
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        // attach callback object, Acceptor
        sk.attach(new Acceptor());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    // Reactor负责dispatch收到的事件
                    dispatch((it.next()));
                }
                selected.clear();
            }
        } catch (IOException ex) {
            // do nothing
        }
    }

    private void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        // 调用之前注册的callback对象
        if (r != null) {
            r.run();
        }
    }

    /**
     * inner class
     */
    private class Acceptor implements Runnable {
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel != null) {
                    new Handler(selector, channel);
                    new MultiThreadHandler(selector, channel);
                }

            } catch (IOException ex) {
                // do nothing
            }
        }
    }

}

class Handler implements Runnable {

    private final SocketChannel socketChannel;

    private final SelectionKey selectionKey;

    private final ByteBuffer input = ByteBuffer.allocate(1024);

    private final ByteBuffer output = ByteBuffer.allocate(1024);

    private static final int READING = 0;

    private static final int SENDING = 1;

    int state = READING;

    Handler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        // Optionally try first read now
        selectionKey = this.socketChannel.register(selector, 0);

        //将Handler作为callback对象
        selectionKey.attach(this);

        //第二步,注册Read就绪事件
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    private boolean inputIsComplete() {
        /* ... */
        return false;
    }

    private boolean outputIsComplete() {
        /* ... */
        return false;
    }

    void process() {
        /* ... */
        return;
    }

    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException ex) {
            // do nothing
        }
    }

    private void read() throws IOException {
        socketChannel.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            // Normally also do first write now

            //第三步,接收write就绪事件
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void send() throws IOException {
        socketChannel.write(output);

        //write完就结束了, 关闭select key
        if (outputIsComplete()) {
            selectionKey.cancel();
        }
    }

}

class MultiThreadHandler implements Runnable {

    private final SocketChannel socketChannel;

    private final SelectionKey selectionKey;

    private final ByteBuffer input = ByteBuffer.allocate(1024);

    private final ByteBuffer output = ByteBuffer.allocate(1024);

    private static final int READING = 0;

    private static final int SENDING = 1;

    private int state = READING;

    private final ExecutorService pool = Executors.newFixedThreadPool(2);

    private static final int PROCESSING = 3;

    public MultiThreadHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        // Optionally try first read now
        // 0表示对这个channel的任何事件都不感兴趣
        selectionKey = this.socketChannel.register(selector, 0);

        //将Handler作为callback对象
        selectionKey.attach(this);

        //第二步,注册Read就绪事件
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    private boolean inputIsComplete() {
        /* ... */
        return false;
    }

    private boolean outputIsComplete() {
        /* ... */
        return false;
    }

    private void process() {
        /* ... */
        return;
    }

    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException ex) { /* ... */ }
    }


    private synchronized void read() throws IOException {
        // read还是在Reactor主线程
        socketChannel.read(input);
        if (inputIsComplete()) {
            state = PROCESSING;
            //使用线程pool异步执行
            pool.execute(new Processor());
        }
    }

    private void send() throws IOException {
        socketChannel.write(output);

        //write完就结束了, 关闭select key
        if (outputIsComplete()) {
            selectionKey.cancel();
        }
    }

    private synchronized void processAndHandOff() {
        process();
        state = SENDING;
        // or rebind attachment
        //process完,开始等待write就绪
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    private class Processor implements Runnable {

        @Override
        public void run() {
            processAndHandOff();
        }

    }

}