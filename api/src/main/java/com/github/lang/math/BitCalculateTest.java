package com.github.lang.math;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/05/03 08:20
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class BitCalculateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void test() {
        // 确定一个数是否2的次方数
        // (x & (x - 1)) == 0
        List<Integer> nums = Lists.newArrayList(1, 2, 3, 4);
        nums.forEach(num -> LOGGER.info("{} is 2^n ? {}", num, (num & (num - 1)) == 0));
    }

    @Test
    public void test2() {
        List<Integer> nums = Lists.newArrayList(-8, -1, 1, 2, 3, 4, 8, 16, 32, 64);
        nums.forEach(num -> {
            LOGGER.info("{} >> 1, result:{}", num, num >> 1);
            LOGGER.info("{} >> 2, result:{}", num, num >> 2);
            // 无符号右移, 高位补0
            LOGGER.info("{} >>> 2, result:{}", num, num >>> 2);
        });
    }

    @Test
    public void test3() {
        System.out.println(tableSizeFor(2));
        System.out.println(tableSizeFor(8));
        System.out.println(tableSizeFor(9));
        System.out.println(tableSizeFor(15));
        System.out.println(tableSizeFor(19));
    }

    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }

}
