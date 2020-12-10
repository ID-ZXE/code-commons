package com.github.lang.collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2020/05/11 23:04
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class MapTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testHashMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(null, 1);
        map.put(null, 2);
        LOGGER.info("null key`value:{}", map.get(null));
    }

    @Test
    public void testLinkedHashMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put("1", "1");
        linkedHashMap.put("2", "1");
        linkedHashMap.put("3", "1");
        linkedHashMap.put("4", "1");

        // 访问的元素被放到了双向链表的尾部
        linkedHashMap.get("1");
        linkedHashMap.get("3");

        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            LOGGER.info("key:{}", entry.getKey());
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
