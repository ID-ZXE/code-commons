package com.hang.algorithm.utils;

/**
 * @author hangs.zhang
 * @date 2019/12/16 下午8:22
 * *********************
 * function:
 */
public final class PrintUtils {

    private PrintUtils() {
    }

    public static void print(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println(arr[arr.length - 1]);
    }

}
