package com.github.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhanghang
 * @date 2021/1/6 4:45 下午
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class RateLimiterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final ExecutorService EXECUTORS = Executors.newFixedThreadPool(10);

    private static final RateLimiter rateLimiter = RateLimiter.create(1);

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("start jvm");
        Thread.sleep(10000);
        LOGGER.info("start task");
        for (int i = 0; i < 10; i++) {
            EXECUTORS.submit(() -> {
                for (int num = 0; num < 10; num++) {
                    rateLimiter.acquire();
                    LOGGER.info("{} acquire", Thread.currentThread().getName());
                }
            });
        }
    }

}
