package com.github.algorithm.utils;

import java.util.Random;

/**
 * @author hangs.zhang
 * @date 2020/03/23 22:45
 * *****************
 * function:
 */
public class SortTestHelper {

    /**
     * 生成n个随机数,区间在[rangeL,rangeR]
     * @param n
     * @param rangeL
     * @param rangeR
     * @return
     */
    public static int[] generateRandomArray(int n, int rangeL, int rangeR) {
        if(rangeL > rangeR) throw new IllegalArgumentException("rangeL > rangeR");

        int[] arr = new int[n];
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < n; i++) arr[i] = Math.abs(random.nextInt()) % (rangeR - rangeL + 1) + rangeL;

        return arr;
    }

    public static int[] generateRandomArray2(int n, int rangeL, int rangeR) {
        if(rangeL > rangeR) throw new IllegalArgumentException("rangeL > rangeR");

        int[] arr = new int[n];
        Random random = new Random(System.currentTimeMillis());
        // Random.nextInt(100),0~参数之间随机取值的整数,左闭右开
        for(int i = 0; i < n; i++) arr[i] = random.nextInt(rangeR - rangeL + 1) + rangeL;
        return arr;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        PrintUtils.print(generateRandomArray(10, -10, 10));
        PrintUtils.print(generateRandomArray2(10, -10, 10));
    }

}
