package com.github.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhanghang
 * @date 2022/3/9 12:32 上午
 * *****************
 * function:
 */
public class MainSubReactor implements Runnable {

    /**
     * subReactors集合, 一个selector代表一个subReactor
     */
    private final Selector[] selectors = new Selector[2];

    private int next = 0;

    private final ServerSocketChannel serverSocket;

    public static void main(String[] args) throws IOException {
        new Thread(new MainSubReactor(8888)).start();
    }

    /**
     * Reactor初始化
     */
    public MainSubReactor(int port) throws IOException {
        for (int i = 0; i < selectors.length; i++) {
            selectors[i] = Selector.open();
        }
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        // 非阻塞
        serverSocket.configureBlocking(false);

        // 分步处理,第一步,接收accept事件
        SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
        // attach callback object, Acceptor
        sk.attach(new Acceptor());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < 2; i++) {
                    selectors[i].select();
                    Set<SelectionKey> selected = selectors[i].selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        // Reactor负责dispatch收到的事件
                        dispatch((it.next()));
                    }
                    selected.clear();
                }
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

    private class Acceptor {

        public synchronized void run() throws IOException {
            // 主selector负责accept
            SocketChannel connection = serverSocket.accept();
            if (connection != null) {
                // 选个subReactor去负责接收到的connection
                new Handler(selectors[next], connection);
            }
            if (++next == selectors.length) {
                next = 0;
            }
        }

    }

}
