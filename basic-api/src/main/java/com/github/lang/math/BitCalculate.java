package com.github.lang.math;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/05/03 08:20
 * *****************
 * function:
 */
public class BitCalculate {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // 32-3
    private static final int COUNT_BITS = Integer.SIZE - 3;

    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    @Test
    public void test() {
        // 536870911
        LOGGER.info("CAPACITY:{}", CAPACITY);
    }

    @Test
    public void test2() {
        System.out.println(2 & (16 - 1));
        System.out.println(2 % (16));

        System.out.println(3 & (16 - 1));
        System.out.println(3 % (16));

        System.out.println(15 & (16 - 1));
        System.out.println(15 % (16));

        System.out.println(19 & (16 - 1));
        System.out.println(19 % (16));
    }

    @Test
    public void test3() {
        // 8 1000
        // 9 1001
        // 16-1 1111
        // 15-1 1110

        System.out.println(8 & 15 - 1);
        System.out.println(9 & 15 - 1);

        System.out.println(8 & 16 - 1);
        System.out.println(9 & 16 - 1);
    }

    @Test
    public void test4() {
        // 确定一个数是否2的次方数
        int x = 2;
        System.out.println((x & (x - 1)) == 0);

        System.out.println(tableSizeFor(15));
        System.out.println(tableSizeFor(2));
        System.out.println(tableSizeFor(8));
        System.out.println(tableSizeFor(9));
        System.out.println(tableSizeFor(19));
    }

    public int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }

}
