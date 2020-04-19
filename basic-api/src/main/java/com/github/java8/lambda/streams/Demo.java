package com.github.java8.lambda.streams;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author hangs.zhang
 * @date 2018/10/31
 * *****************
 * function:
 */
public class Demo {

    /**
     * 由值创建流
     */
    @Test
    public void test() {
        Stream<String> stream = Stream.of("Java8", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);
        // 创建一个空流
        Stream<String> empty = Stream.empty();
    }

    /**
     * 由数组创建流
     */
    @Test
    public void test2() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int sum = Arrays.stream(numbers).sum();
        System.out.println(sum);
    }

    /**
     * 文件生成流
     */
    @Test
    public void test3() {
        try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            // flatMap的作用是将多个流汇聚成一个，然后处理
            long count = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct().count();
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 由函数生成流-创建无限流
     */
    @Test
    public void test4() {
        // 迭代
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
        // 斐波拉契亚
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t -> t[0])
                .forEach(System.out::println);

        // generate
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

}
