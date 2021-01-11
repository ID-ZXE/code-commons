package com.github.redis.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhanghang
 * @date 2021/1/10 7:38 下午
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class TestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final ExecutorService EXECUTORS = Executors.newFixedThreadPool(100);

    @Test
    public void testTryAcquire1() throws Exception {
        int currentSize = 20;
        AtomicInteger atomicInteger = new AtomicInteger(currentSize);
        CountDownLatch countDownLatch = new CountDownLatch(currentSize);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(currentSize);

        RateLimiter rateLimiter = new FixedWindowRedisRateLimiter("custom", 3);
        for (int i = 0; i < currentSize; i++) {
            EXECUTORS.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            cyclicBarrier.await();
                        } catch (Exception e) {
                            // do nothing
                        }
                        LOGGER.info("{} tryAcquire result {}", Thread.currentThread().getName(), rateLimiter.tryAcquire());
                        int mark = atomicInteger.incrementAndGet();
                        // 分割线
                        if (mark % 20 == 0) LOGGER.info("********************************");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }

    @Test
    public void testAcquire1() throws Exception {
        int currentSize = 20;
        AtomicInteger atomicInteger = new AtomicInteger(currentSize);
        CountDownLatch countDownLatch = new CountDownLatch(currentSize);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(currentSize);

        RateLimiter rateLimiter = new FixedWindowRedisRateLimiter("custom", 3);
        for (int i = 0; i < currentSize; i++) {
            EXECUTORS.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            cyclicBarrier.await();
                        } catch (Exception e) {
                            // do nothing
                        }
                        try {
                            rateLimiter.acquire();
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }

    @Test
    public void testAcquire2() throws InterruptedException {
        int currentSize = 20;
        AtomicInteger atomicInteger = new AtomicInteger(currentSize);
        CountDownLatch countDownLatch = new CountDownLatch(currentSize);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(currentSize);

        RateLimiter rateLimiter = new SlidingWindowRedisRateLimiter("custom", 3);
        for (int i = 0; i < currentSize; i++) {
            EXECUTORS.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            cyclicBarrier.await();
                        } catch (Exception e) {
                            // do nothing
                        }
                        LOGGER.info("{} tryAcquire result {}", Thread.currentThread().getName(), rateLimiter.tryAcquire());
                        int mark = atomicInteger.incrementAndGet();
                        // 分割线
                        if (mark % 20 == 0) LOGGER.info("********************************");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }

}
