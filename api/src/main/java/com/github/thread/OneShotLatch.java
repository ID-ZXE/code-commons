package com.github.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author zhanghang
 * @date 2020/12/12 1:48 下午
 * *****************
 * function: 利用AQS实现一次性门闩
 */
public class OneShotLatch {

    private final Sync sync = new Sync();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected int tryAcquireShared(int arg) {
            // 返回-1, 则阻塞, 模板方法定义在AQS中
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            // 开门
            setState(1);
            return true;
        }
    }

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() {
        sync.acquireShared(0);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executors = Executors.newCachedThreadPool();
        OneShotLatch latch = new OneShotLatch();
        for (int i = 0; i < 10; i++) {
            executors.submit(() -> {
                LOGGER.info("{}正在等待门锁打开", Thread.currentThread().getName());
                long start = System.currentTimeMillis();
                latch.await();
                LOGGER.info("{}等待{}ms后门锁打开", Thread.currentThread().getName(), (System.currentTimeMillis() - start));
            });
        }
        Thread.sleep(300L);
        LOGGER.info("{} 打开门锁", Thread.currentThread().getName());
        latch.signal();
        Thread.sleep(1000L);
    }

}
