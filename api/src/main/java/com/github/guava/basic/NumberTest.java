package com.github.guava.basic;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午9:06
 * *********************
 * function:
 */
public class NumberTest {

    @Test
    public void intOverFlow() {
        long millisOfYears = 5000 * 365 * 24 * 3600 * 1000;
        long millisOfYears2 = 5000 * 365 * 24 * 3600 * 1000L;
        long millisOfYears3 = 5000L * 365 * 24 * 3600 * 1000;

        System.out.println(millisOfYears);
        System.out.println(millisOfYears2);
        System.out.println(millisOfYears3);
    }

    @Test
    public void bigDecimal() {
        // 禁止使用bigDecimal的构造函数 new BigDecimal(0.580);
        BigDecimal a = BigDecimal.valueOf(0.580);
        // 这里因为0.58本身就是不准的
        BigDecimal b = BigDecimal.valueOf(0.580);
        BigDecimal c = new BigDecimal("0.580");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        // 比较精度,同时也比较数值
        System.out.println(b.equals(c));
        // 比较数值
        System.out.println(b.compareTo(c));
    }

    @Test
    public void divideTest() {
        BigDecimal a = new BigDecimal(1L);
        BigDecimal b = new BigDecimal(3L);
        // 此处报错 需要指明精度
        try {
            System.out.println(a.divide(b));
        } catch (Exception e) {
            System.out.println("未指明精度");
        }

        // 16位小数
        System.out.println(a.divide(b, MathContext.DECIMAL64));
    }

    @Test
    public void ints() {
        int a = 2000000000;
        int b = a * -1;
        boolean grater = (a-b > 0);

        // 不能简单的比较大小
        System.out.println(grater);
        System.out.println(Ints.compare(a, b) > 0);

        System.out.println(Ints.contains(new int[]{1, 2, 3}, 2));
        System.out.println(Ints.max(1, 2, 3));
        System.out.println(Ints.min(1, 2, 3));


        // asList
        int[] arr = new int[]{1, 2, 3};
        // return List<int[]>
        System.out.println(Arrays.asList(arr));
        // return List<Integer  >
        System.out.println(Ints.asList(arr));
    }

}
