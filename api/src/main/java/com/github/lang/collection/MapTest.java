package com.github.lang.collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author hangs.zhang
 * @date 2020/05/11 23:04
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class MapTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Executor executor = Executors.newCachedThreadPool();

    @Test
    public void testHashMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        // 支持null key
        map.put(null, 1);
        LOGGER.info("null key`value:{}", map.get(null));
    }

    @Test
    public void testTreeMap() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 1);
        map.put(3, 3);
        map.put(2, 2);
        // 迭代是按照顺序输出
        map.get(1);
        map.forEach((k, v) -> LOGGER.info("key:{}", k));
    }

    @Test
    public void testLinkedHashMap() throws InterruptedException {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put("1", "1");
        linkedHashMap.put("2", "1");
        linkedHashMap.put("3", "1");
        linkedHashMap.put("4", "1");

        // 访问的元素被放到了双向链表的尾部
        linkedHashMap.get("1");
        linkedHashMap.get("3");

        // LinkedHashMap在遍历的时候, 如果外部发了get操作, 也会发生ConcurrentModificationException
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300L);
                } catch (InterruptedException e) {
                    // do nothing
                }
                linkedHashMap.get("1");
            }
        });
        try {
            for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
                Thread.sleep(150L);
                LOGGER.info("key:{}", entry.getKey());
            }
        } catch (ConcurrentModificationException e) {
            LOGGER.info("发生ConcurrentModificationException");
        }
    }


    @Test
    public void testConcurrentHashMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        map.get("key");

        try {
            map.put(null, "value");
        } catch (NullPointerException e) {
            LOGGER.info("can not put null key");
        }

        try {
            map.put("key", null);
        } catch (NullPointerException e) {
            LOGGER.info("can not put null value");
        }

    }

}
