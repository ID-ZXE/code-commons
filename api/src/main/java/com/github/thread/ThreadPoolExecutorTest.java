package com.github.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午7:54
 * *********************
 * function:
 * <p>
 * Executor框架-将线程的创建、维护和任务分离,避免了Thread的创建、启动消耗
 */
public class ThreadPoolExecutorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void test() throws InterruptedException {
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(300);
        // 策略选择为抛出RejectException
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 10, TimeUnit.MINUTES, blockingQueue, new CustomThreadFactory("test"),
                new ThreadPoolExecutor.AbortPolicy());
        // 动态设置线程池的参数
        executor.setCorePoolSize(200);
        executor.setMaximumPoolSize(300);

        for (int i = 0; i < 20; i++) {
            final int a = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(a * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 监控线程池的参数
        LOGGER.info("coreSize:{}", executor.getCorePoolSize());
        Thread.sleep(300);
        LOGGER.info("activeCount:{}", executor.getActiveCount());
        // 创建过的最大线程数量
        Thread.sleep(300);
        LOGGER.info("largestPoolSize:{}", executor.getLargestPoolSize());
        // 需要执行的任务数量
        Thread.sleep(300);
        LOGGER.info("taskCount:{}", executor.getTaskCount());
        // 已经完成的任务数量
        Thread.sleep(300);
        LOGGER.info("completeTaskCount:{}", executor.getCompletedTaskCount());
    }

    @Test
    public void test2() {
        // ExecutorService executorService = Executors.newCachedThreadPool();
        // executorService.submit()
        // executorService.invokeAll()
        // executorService.invokeAny()
        // 处理Executor框架的异常的时候,需要不断的t = t.getCause,这样才能拿到真正的异常
    }

    @Test
    public void test3() {
        // 线程池钩子,需要继承ThreadPoolExecutor,然后重写
        // beforeExecute
        // afterExecute
        // terminated
    }

}
