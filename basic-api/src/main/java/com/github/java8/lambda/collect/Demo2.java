package com.github.java8.lambda.collect;

import com.ccsu.lambda.a_demo.DealDishByLambdaTest;
import com.ccsu.lambda.a_demo.Dish;
import org.junit.Test;

import java.util.*;


import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * @author hangs.zhang
 * @date 2018/11/2
 * *****************
 * function:
 */
public class Demo2 {

    private List<Dish> menu = DealDishByLambdaTest.menu;

    private String line = "********";

    /**
     * collect的使用
     * 可以看出，不使用collect也可以完成同样的操作
     */
    @Test
    public void test1() {
        // toList
        List<Dish> list = menu.stream().collect(toList());
        // toSet
        Set<Dish> set = menu.stream().collect(toSet());
        // toCollection
        ArrayList<Dish> arrayList = menu.stream().collect(toCollection(ArrayList::new));
        // 计算流中元素个数
        Long collect = menu.stream().collect(counting());
        // 整数求和
        Integer summingInt = menu.stream().collect(summingInt(Dish::getCalories));
        // 求平均值
        Double averagingInt = menu.stream().collect(averagingInt(Dish::getCalories));
        // 统计值
        IntSummaryStatistics summaryStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        // 字符串连接
        String join = menu.stream().map(Dish::getName).collect(joining(","));
        System.out.println(join);
        System.out.println(line);
        // maxBy 求出按照给定比较器所选出的最大元素的optional
        Optional<Dish> maxBy = menu.stream().collect(maxBy(comparingInt(Dish::getCalories)));
        maxBy.ifPresent(System.out::println);
        System.out.println(line);
        // minBy
        Optional<Dish> minBy = menu.stream().collect(minBy(comparing(Dish::getName)));
        minBy.ifPresent(System.out::println);
        System.out.println(line);
        // reducing
        Integer reducing = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(reducing);
        System.out.println(line);
        // collectingAndThen,包裹另外一个收集器，对其结果使用转换函数
        Integer size = menu.stream().collect(collectingAndThen(toList(), List::size));
        System.out.println("size " + size);
        System.out.println(line);
        // groupingBy 根据一个属性的值对流中的项目作同组，并将属性值作为结果map的建
        Map<Dish.Type, List<Dish>> map = menu.stream().collect(groupingBy(Dish::getType));
        // partitioningBy 根据流中的项目应用谓词的结果来对项目进行分区
        Map<Boolean, List<Dish>> map2 = menu.stream().collect(partitioningBy(Dish::getVegetarian));
    }

    @Test
    public void test2() {
        // Collector<T,A,R>
        // T是流中要收集项目的泛型
        // A是累加器的泛型
        // R是收集操作获得的对象
    }

}
