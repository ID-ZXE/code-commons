package com.github.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.*;

/**
 * @author hangs.zhang
 * @date 2020/06/28 19:30
 * *****************
 * function:
 */
public class FutureTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(1000);
            return "result";
        });

        String result = future.get();
        LOGGER.info("result:{}", result);
    }

}
