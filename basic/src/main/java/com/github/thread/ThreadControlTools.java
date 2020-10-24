package com.github.thread;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午8:51
 * *********************
 * function:
 */
public class ThreadControlTools {

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
    public void test2() {
        ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "initial");
        threadLocal.set("value");
        System.gc();
        LOGGER.info("value:{}", threadLocal.get());
        threadLocal.remove();
    }

}
