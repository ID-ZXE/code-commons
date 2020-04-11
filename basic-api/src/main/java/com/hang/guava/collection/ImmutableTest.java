package com.hang.guava.collection;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午8:18
 * *********************
 * function:
 */
public class ImmutableTest {

    public static void main(String[] args) {
        testGuavaImmutable();
        testJDKImmutable();
    }

    private static void testGuavaImmutable() {
        List<String> list = new ArrayList<>();
        list.add("name");
        list.add("school");
        List<String> immutableList = ImmutableList.copyOf(list);
        list.add("data");
        System.out.println(immutableList);
    }

    private static void testJDKImmutable() {
        List<String> list = new ArrayList<>();
        list.add("name");
        list.add("school");
        List<String> immutableList = Collections.unmodifiableList(list);
        list.add("data");
        System.out.println(immutableList);
    }

}
