package com.github.thread;

import java.util.concurrent.*;

/**
 * @author hangs.zhang
 * @date 2020/06/28 19:30
 * *****************
 * function:
 */
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "result";
            }
        });
        String result = future.get();
        System.out.println(result);
    }
}
