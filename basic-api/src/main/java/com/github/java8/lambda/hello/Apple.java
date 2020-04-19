package com.github.java8.lambda.hello;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author hangs.zhang
 * @date 2018/10/29
 * *****************
 * function:
 */
public class Apple {

    private Integer weight;

    private String color;

    private Integer getWeight() {
        return weight;
    }

    private String getColor() {
        return color;
    }

    private static boolean isHeavyApple(Apple apple) {
        return apple.weight > 150;
    }

    private static List<Apple> filterApple(List<Apple> apples, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    @Test
    public void test() {
        List<Apple> apples = new ArrayList<>();
        Apple apple = new Apple();
        apple.weight = 200;
        apple.color = "red";
        apples.add(apple);
        filterApple(apples, Apple::isHeavyApple).forEach(e -> System.out.println(e.weight));
        // 上述写法可能要定义函数，有些麻烦.可以使用匿名lambda
        filterApple(apples, (a) -> "red".equals(a.color))
                .forEach(e -> System.out.println(e.color));
        // Java8还有一套自己的函数 a_demo
        apples.stream().filter((a -> "red".equals(a.color)))
                .forEach(e -> System.out.println(e.color));
        // 并行执行
        apples.parallelStream().filter((a -> "red".equals(a.color)))
                .collect(toList()).forEach(e -> System.out.println(e.color));

        // 方法引用,一种语法糖
        List<String> strs = Arrays.asList("a", "b", "A", "B");
        // 1
        strs.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        // 2
        strs.sort(String::compareToIgnoreCase);

        // 构造函数
        Supplier<Apple> supplier = Apple::new;
        Apple supplierApple = supplier.get();

        // 简洁高效的比较
        apples.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));

    }

}
