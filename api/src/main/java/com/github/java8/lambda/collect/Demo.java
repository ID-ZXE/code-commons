package com.github.java8.lambda.collect;

import com.github.java8.lambda.hello.DealDishByLambdaTest;
import com.github.java8.lambda.hello.Dish;
import org.junit.Test;

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * @author hangs.zhang
 * @date 2018/10/31
 * *****************
 * function:
 */
public class Demo {

    private List<Dish> menu = DealDishByLambdaTest.menu;

    @Test
    public void test() {
        int sum = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(sum);

        Double avg = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avg);

        IntSummaryStatistics summaryStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(summaryStatistics);
    }

    @Test
    public void test2() {
        String strMenu = menu.stream().map(Dish::getName).collect(joining(","));
        System.out.println(strMenu);
    }

    @Test
    public void test3() {
        Optional<Dish> optional = menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        // 参数1是初始值，2是转换函数，3是累积函数
        Integer sum = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    // 分组
    // groupingBy方法传递了一个Function，提取了key
    @Test
    public void test4() {
        Map<Dish.Type, List<Dish>> map = menu.stream().collect(groupingBy(Dish::getType));
        map.forEach((k, v) -> System.out.println(k + "\t" + v));

        // 灵活定义分组
        Map<String, List<Dish>> result = menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400) {
                return "DIET";
            } else if (dish.getCalories() <= 700) {
                return "NORMAL";
            } else {
                return "FAT";
            }
        }));
        result.forEach((k, v) -> System.out.println(k + "\t" + v));
        System.out.println("*************************************");
        // 多级分组
        Map<Dish.Type, Map<String, List<Dish>>> result2 = menu.stream()
                .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                    if (dish.getCalories() <= 400)
                        return "DIET";
                    else if (dish.getCalories() <= 700)
                        return "NORMAL";
                    else
                        return "FAT";
                })));
        result2.forEach((k, v) -> System.out.println(k + "\t" + v));
    }

    /**
     * 按照子组收集数据
     */
    @Test
    public void test5() {
        // 1
        Map<Dish.Type, Long> map = menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println(map);
        System.out.println("***********************");
        // 2
        Map<Dish.Type, Optional<Dish>> map2 = menu.stream().collect(groupingBy(Dish::getType,
                maxBy(Comparator.comparing(Dish::getCalories))));
        System.out.println(map2);
        System.out.println("************************");
        // 3 将收集器的结果转换成另外一种结果
        Map<Dish.Type, Dish> map3 = menu.stream().collect(groupingBy(Dish::getType,
                collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(map3);
        System.out.println("************************");
        // 4 与其他收集器联合使用的例子
        Map<Dish.Type, Integer> map4 = menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(map4);
    }

    /**
     * partication操作，分区
     */
    @Test
    public void test6() {
        // partication与group配合
        Map<Boolean, Map<Dish.Type, List<Dish>>> map1 = menu.stream()
                .collect(partitioningBy(Dish::getVegetarian, groupingBy(Dish::getType)));
        System.out.println(map1);
        System.out.println("*************************");
        //
        Map<Boolean, Dish> map2 = menu.stream().collect(partitioningBy(Dish::getVegetarian,
                collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(map2);
        System.out.println("*************************");

    }

}


