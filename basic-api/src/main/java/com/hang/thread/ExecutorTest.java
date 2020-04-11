package com.hang.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executorService = Executors.newCachedThreadPool();

        /// executorService.submit()
        /// executorService.invokeAll()
        /// executorService.invokeAny()

        // 处理Executor框架的异常的时候,需要不断的t = t.getCause,这样才能拿到真正的异常
    }



}
