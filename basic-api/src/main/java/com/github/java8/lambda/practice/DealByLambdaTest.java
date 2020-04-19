package com.github.java8.lambda.practice;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

/**
 * @author hangs.zhang
 * @date 2018/10/31
 * *****************
 * function:
 */
public class DealByLambdaTest {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    /**
     * 找出2011年的所有交易并且按照交易额排序
     */
    @Test
    public void test1() {
        transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .forEach(e -> System.out.println(e.toString()));
    }

    /**
     * 交易员们在那些城市工作过
     */
    @Test
    public void test2() {
        // 1
        transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct().forEach(System.out::println);
        // 2
        transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .collect(toSet()).forEach(System.out::println);
    }

    /**
     * 查找所有来自剑桥的交易员，并且按照名字排序
     */
    @Test
    public void test3() {
        transactions.stream().map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .distinct()
                .sorted(comparing(Trader::getName))
                .forEach(System.out::println);
    }

    /**
     * 返回所有交易员的姓名字符串，按照字母顺序排序
     */
    @Test
    public void test4() {
        // 1
        String result = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (s1, s2) -> s1 + s2);
        System.out.println(result);
        // 2 更加高效
        result = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct().sorted().collect(joining());
        System.out.println(result);
    }

    /**
     * 有没有交易员在米兰工作
     */
    @Test
    public void test5() {
        boolean milanBased = transactions.stream().anyMatch(transaction -> "Milan".equals(transaction.getTrader().getCity()));
        System.out.println(milanBased);
    }

    /**
     * 打印在剑桥工作的交易员的所有交易额
     */
    @Test
    public void test6() {
        Integer sum = transactions.stream().filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue).reduce(0, (t1, t2) -> t1 + t2);
        System.out.println(sum);
    }

    /**
     * 所有交易中，最高交易额是多少
     */
    @Test
    public void test7() {
        transactions.stream().map(Transaction::getValue).max(Integer::compareTo).ifPresent(System.out::println);
    }

    /**
     * 找到交易额最小的交易
     */
    @Test
    public void test8() {
        transactions.stream().min(comparing(Transaction::getValue)).ifPresent(System.out::println);
    }

}
