package com.github.map;

import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/05/06 22:28
 * *****************
 * function:
 */
public class HashMapTest extends TestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void testSize() {

    }

    public void testIsEmpty() {
    }

    public void testGet() {
    }

    @Test
    public void testPut() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "hangs.zhang");
        map.put("company", "qunar");
        LOGGER.info("get name : {}", map.get("name"));
        LOGGER.info("get company : {}", map.get("company"));
    }

    public void testPutAll() {
    }

    public void testContainsKey() {
    }

    public void testContainsValue() {
    }

    public void testClear() {
    }

    public void testKeySet() {
    }

    public void testValues() {
    }

    public void testEntrySet() {
    }

    public void testRemove() {
    }
}