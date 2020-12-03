package com.github.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hangs.zhang
 * @date 2020/04/30 23:20
 * *****************
 * function:
 */
public class LockTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testNewCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        LOGGER.info("isEquals{}", condition1 == condition2);
    }

    @Test
    public void lockSupport() throws InterruptedException {
        Thread thread = new Thread(() -> {
            LOGGER.info("i`m start");
            LOGGER.info("interrupt state:{}", Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // sleep清除中断标志位,但是不会更改_counter
                LOGGER.info("interrupt state:{}", Thread.currentThread().isInterrupted());
            }
            LOGGER.info("i`m work");
            LockSupport.park();
            LOGGER.info("interrupt state:{}", Thread.currentThread().isInterrupted());
            LOGGER.info("i`m end");
        }, "lockSupport");
        thread.start();
        thread.interrupt();
        Thread.sleep(1000);
        LOGGER.info("thread end");
    }

}
