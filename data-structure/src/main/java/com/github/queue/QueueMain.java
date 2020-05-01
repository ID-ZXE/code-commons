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
public class QueueMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void arrayBlockingQueue() throws InterruptedException {
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
                e.printStackTrace();
            }
        }).start();
        LOGGER.info("开始插入第三个元素");
        queue.put("d");
        LOGGER.info("end");
    }

}
