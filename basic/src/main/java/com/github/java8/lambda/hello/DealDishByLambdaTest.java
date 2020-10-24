package com.github.java8.lambda.hello;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author hangs.zhang
 * @date 2018/10/31
 * *****************
 * function:
 */
public class DealDishByLambdaTest {

    public static List<Dish> menu = Arrays.asList(new Dish("pork", 800, Dish.Type.META, false),
            new Dish("beef", 700, Dish.Type.META, false),
            new Dish("chicken", 400, Dish.Type.META, false),
            new Dish("french", 530, Dish.Type.OTHER, true),
            new Dish("rice", 350, Dish.Type.OTHER, true),
            new Dish("season", 120, Dish.Type.OTHER, true),
            new Dish("pizza", 550, Dish.Type.OTHER, true),
            new Dish("prawns", 300, Dish.Type.FISH, false),
            new Dish("salmon", 450, Dish.Type.FISH, false));

    /**
     * 这是Java7的处理方式，十分的啰嗦
     */
    @Test
    public void test() {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        // 筛选卡路里小于400的
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }
        // 排序
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });
        // 获取所有菜名
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesName.add(dish.getName());
        }
        // 遍历
        for (String name : lowCaloricDishesName) {
            System.out.println(name);
        }
    }

    /**
     * 这是Java8的处理方式，简洁明了
     */
    @Test
    public void test2() {
        // 将stream替换成parallelStream，就可以使用并行流，更加高效
        List<String> lowCaloricDishesName = menu.stream().filter(dish -> dish.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
        lowCaloricDishesName.forEach(System.out::println);
    }

    @Test
    public void test3() {
        Map<Dish.Type, List<Dish>> map = menu.stream().collect(Collectors.groupingBy(Dish::getType));
    }

    /**
     * flatmap方法：把一个流中的每一个值都替换成另一个流，然后把所有的流连接起来成为一个流
     */
    @Test
    public void flatmap() {
        // 1
        List<String> strs = Arrays.asList("Hello", "World");
        strs.stream().map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct().collect(toList())
                .forEach(System.out::println);

        // 2
        // 在这个例子中，flatmap将number2的map收集到的流全部转化成了值，然后再次汇聚为一个流
        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(3, 4);
        List<int[]> pairs = number1.stream()
                .flatMap(i -> number2.stream().map(j -> new int[]{i, j}))
                .collect(toList());
        pairs.forEach(e -> System.out.println(Arrays.toString(e)));
    }

    @Test
    public void match() {
        if (menu.stream().anyMatch(Dish::getVegetarian)) System.out.println("menu anyMatch");

        if (menu.stream().allMatch(dish -> dish.getCalories() < 1000)) System.out.println("menu allMatch");

        if (menu.stream().noneMatch(dish -> dish.getCalories() > 1000)) System.out.println("menu noneMatch");
    }

    @Test
    public void optional() {
        Optional<Dish> optional = menu.stream().filter(Dish::getVegetarian).findAny();
        // 如果存在就打印
        optional.ifPresent(dish -> System.out.println(dish.getName()));
    }

    // 规约操作，将流归约成一个值
    // reduce接受两个参数
    // 一个是初始值，一个是BinaryOperator来将两个元素结合起来产生一个新值
    @Test
    public void reduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        // 无原始值
        Optional<Integer> optional = numbers.stream().reduce((a, b) -> a + b);
        optional.ifPresent(System.out::println);

        // 求max
        Integer max = numbers.stream().reduce(0, Integer::max);
        System.out.println(max);

        // 上面那些操作会有装箱的成本
        sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }

}
