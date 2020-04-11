package com.hang.algorithm.structure.sort;

import com.hang.algorithm.utils.PrintUtils;
import com.hang.algorithm.utils.SortTestHelper;

/**
 * @author hangs.zhang
 * @date 2020/03/23 23:15
 * *****************
 * function: 选择排序算法,n^2级别算法
 */
public class SelectionSort {

    public static int[] sort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            int index = i;
            for(int j = i; j < arr.length; j++) {
                if(arr[j] < arr[index]) {
                    index = j;
                }
            }
            SortTestHelper.swap(arr, i, index);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = SortTestHelper.generateRandomArray2(10, 0, 10);
        PrintUtils.print(arr);
        int[] sortedArr = sort(arr);
        PrintUtils.print(sortedArr);
    }

}
