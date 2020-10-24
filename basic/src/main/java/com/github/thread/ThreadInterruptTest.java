package com.github.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午6:54
 * *********************
 * function:
 */
public class ThreadInterruptTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testInterrupt1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                // 通过InterruptedException异常响应中断
                // 这里的state会输出为false,thread响应之后会将标志位清零
                LOGGER.info("i was interrupted, current state {}", Thread.currentThread().isInterrupted());
            }
        }, "interrupted-thread");
        thread.start();
        Thread.sleep(1000L);
        // 中断线程
        thread.interrupt();
        Thread.sleep(3000L);
    }

    /**
     * 线程不会收到任何干扰
     */
    @Test
    public void testInterrupt2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 10L) {
                LOGGER.info("I`m busy");
            }
            // state true 状态发生改变 但是线程并不会因此终止
            LOGGER.info("My State is {}", Thread.currentThread().isInterrupted());
        }, "interrupted-thread");
        thread.start();
        Thread.sleep(5L);
        // 中断
        thread.interrupt();
        Thread.sleep(10L);
    }

    @Test
    public void testInterrupt3() throws InterruptedException {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            boolean interrupt = false;
            while ((System.currentTimeMillis() - start) < 10L) {
                // Thread.interrupted()获取标识位,并且将标识位修改
                boolean interrupted = Thread.interrupted();
                if (interrupted) {
                    LOGGER.info("线程中断,但是不响应这个中断,当前标志位:{}", interrupted);
                    interrupt = true;
                } else if (interrupt) {
                    LOGGER.info("线程被中断一次, 但是线程并未停止");
                    break;
                } else {
                    LOGGER.info("I`m busy...");
                }
            }
            LOGGER.info("I`m done with my job");
        }, "interrupted-thread");
        thread.start();
        Thread.sleep(5L);
        // 中断
        thread.interrupt();
        Thread.sleep(10L);
    }

    @Test
    public void testInterrupt4() throws InterruptedException {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 10L) {
                // 获取标识位,并且不修改标识位
                if (Thread.currentThread().isInterrupted()) {
                    LOGGER.info("somebody has interrupt me, ignore it...");
                } else {
                    LOGGER.info("I`m busy...");
                }
            }
            LOGGER.info("I`m done with my job");
        }, "interrupted-thread");
        thread.start();
        Thread.sleep(5L);
        // 中断
        thread.interrupt();
        Thread.sleep(10L);
    }

}
