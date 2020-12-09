package com.github.thread;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.locks.LockSupport;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午8:51
 * *********************
 * function:
 */
public class ThreadControlToolsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void test() {
        // 控制并发访问
        // 值可以动态修改
        // Semaphore

        // 值不可修改
        // one wait for all
        // CountDownLatch
    }

    @Test
    public void threadLocalTest() {
        ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "initial");
        LOGGER.info("value1:{}", threadLocal.get());
        threadLocal.set("value");
        LOGGER.info("value2:{}", threadLocal.get());
        threadLocal.remove();
        LOGGER.info("value3:{}", threadLocal.get());
    }

    /**
     * 响应sleep()方法导致中断标志被清除, 但是LockSupport.park()方法还是不会阻塞
     */
    @Test
    public void lockSupport() throws InterruptedException {
        final String mark = "interrupt state:{}";
        Thread thread = new Thread(() -> {
            LOGGER.info("i`m start");
            LOGGER.info(mark, Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // sleep()方法响应中断会清除标志位, 但是不会更改_counter
                LOGGER.info(mark, Thread.currentThread().isInterrupted());
            }
            LOGGER.info("i`m work");
            LockSupport.park();
            LOGGER.info(mark, Thread.currentThread().isInterrupted());
            LOGGER.info("i`m end");
        }, "lockSupport");
        thread.start();
        thread.interrupt();
        Thread.sleep(1000);
        LOGGER.info("thread end");
    }

    /**
     * 在park之前执行unpark()方法, 线程不会阻塞
     */
    @Test
    public void lockSupport2() {
        LOGGER.info("start");
        LockSupport.unpark(Thread.currentThread());
        /*不会阻塞*/
        LockSupport.park();
        LOGGER.info("end");
    }

    /**
     * 线程中断标志位true, LockSupport.park()不会阻塞
     */
    @Test
    public void lockSupport3() throws InterruptedException {
        LOGGER.info("start");
        Thread thread = new Thread(() -> {
            LOGGER.info("thread start");
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 3L) {
            }
            LockSupport.park();
            LOGGER.info("thread end");
        });
        thread.start();
        // 中断线程
        thread.interrupt();
        Thread.sleep(5L);
        LOGGER.info("end");
    }

}
