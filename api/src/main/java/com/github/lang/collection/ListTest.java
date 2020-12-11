package com.github.lang.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhanghang
 * @date 2020/12/11 2:14 下午
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class ListTest {

    @Test
    public void testArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("str");
    }

    @Test
    public void testCopyOnWriteArrayList() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        list.add("str");
        list.get(0);
    }

}
