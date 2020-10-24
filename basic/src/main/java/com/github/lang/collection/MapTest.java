package com.github.lang.collection;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/05/11 23:04
 * *****************
 * function:
 */
public class MapTest {

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

        for (Map.Entry entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

}
