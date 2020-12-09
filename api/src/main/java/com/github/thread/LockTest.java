package com.github.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;
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

    private final ReentrantLock lock = new ReentrantLock();

    @Test
    public void foobar() {
        // lock方法放在try语句块之外
        lock.lock();
        try {
            LOGGER.info("业务逻辑");
            // 业务逻辑
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void foobar2() {
        if (lock.tryLock()) {
            LOGGER.info("获取锁失败");
        } else {
            lock.unlock();
        }

        try {
            int timeout = 1000;
            if (!lock.tryLock(timeout, TimeUnit.MILLISECONDS)) {
                LOGGER.info("获取锁超时");
            } else {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            // 响应InterruptedException异常会清除中断的标志位
            LOGGER.error("响应中断", e);
        }

    }

}
