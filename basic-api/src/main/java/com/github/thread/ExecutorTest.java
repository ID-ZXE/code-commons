package com.github.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午7:54
 * *********************
 * function:
 *
 * Executor框架-将线程的创建、维护和任务分离,避免了Thread的创建、启动消耗
 */
public class ExecutorTest {

    public static void main(String[] args) {
        // ExecutorService executorService = Executors.newCachedThreadPool();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        // 动态设置线程池的参数
        executor.setCorePoolSize(200);
        executor.setMaximumPoolSize(300);
        // 监控线程池的参数
        System.out.println("coreSize:" + executor.getCorePoolSize());
        System.out.println("activeCount:" + executor.getActiveCount());

        /// executorService.submit()
        /// executorService.invokeAll()
        /// executorService.invokeAny()

        // 处理Executor框架的异常的时候,需要不断的t = t.getCause,这样才能拿到真正的异常
    }



}
