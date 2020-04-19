package com.github.java8.lambda.parallel;

import org.junit.Test;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author hangs.zhang
 * @date 2018/11/2
 * *****************
 * function:
 */
public class Demo {

    private static final String LINE = "*******";

    @Test
    public void test() {
        long value = 100000;
        // 串行流  花费大约126
        long start = System.currentTimeMillis();
        Long res = Stream.iterate(1L, i -> i + 1).limit(value).reduce(0L, Long::sum);
        System.out.println("result1:" + res);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(LINE);

        // 并行流  花费大约43
        long start2 = System.currentTimeMillis();
        Long res2 = Stream.iterate(1L, i -> i + 1).limit(value).parallel().reduce(0L, Long::sum);
        System.out.println("result2:" + res2);
        System.out.println(System.currentTimeMillis() - start2);
        System.out.println(LINE);

        // 免去拆箱的开销
        long start3 = System.currentTimeMillis();
        Long res3 = LongStream.rangeClosed(0, value).reduce(0L, Long::sum);
        System.out.println("result3:" + res3);
        System.out.println(System.currentTimeMillis() - start3);
        System.out.println(LINE);
    }

}
