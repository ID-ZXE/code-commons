package com.github.algorithm.structure.sort;

import com.github.algorithm.utils.PrintUtils;
import com.github.algorithm.utils.SortTestHelper;

/**
 * @author hangs.zhang
 * @date 2020/03/23 23:15
 * *****************
 * function: 选择排序算法,n^2级别算法
 */
public class SelectionSort {

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }
            SortTestHelper.swap(arr, i, index);
        }
    }

    public static void main(String[] args) {
        int[] arr = SortTestHelper.generateRandomArray2(10, 0, 10);
        PrintUtils.print(arr);
        sort(arr);
        PrintUtils.print(arr);
    }

}
