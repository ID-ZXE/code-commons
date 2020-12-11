package com.github.queue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/05/01 21:38
 * *****************
 * function:
 */
public class QueueTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 测试put阻塞
     */
    @Test
    public void testArrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.put("a");
        queue.put("b");
        queue.put("c");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                String data = queue.take();
                LOGGER.info("取出data:{}", data);
            } catch (InterruptedException e) {
                // do nothing
            }
        }).start();
        LOGGER.info("开始插入第三个元素");
        queue.put("d");
        LOGGER.info("main end");
    }

    /**
     * 测试take阻塞
     */
    @Test
    public void testArrayBlockingQueue2() throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    String data = queue.take();
                    LOGGER.info("取出data:{}", data);
                }
            } catch (InterruptedException e) {
                // do nothing
            }
        }).start();
        Thread.sleep(1000);
        queue.put("a");
        Thread.sleep(1000);
        queue.put("b");
        Thread.sleep(1000);
        queue.put("c");
        Thread.sleep(1000);
        queue.put("d");
        LOGGER.info("main end");
    }

}
