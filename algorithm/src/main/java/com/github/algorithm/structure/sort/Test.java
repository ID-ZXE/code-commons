package com.github.algorithm.structure.sort;

import com.github.algorithm.utils.SortTestHelper;

/**
 * @author hangs.zhang
 * @date 2020/05/27 22:18
 * *****************
 * function:
 */
public class Test {

    public static void main(String[] args) {
        int[] arr1 = SortTestHelper.generateRandomArray(100000, 10, 10000000);
        int[] arr2 = SortTestHelper.copyArr(arr1);
        int[] arr3 = SortTestHelper.copyArr(arr1);

        long start1 = System.currentTimeMillis();
        SelectionSort.sort(arr1);
        System.out.println(System.currentTimeMillis() - start1);

        long start2 = System.currentTimeMillis();
        InsertionSort.sort(arr2);
        System.out.println(System.currentTimeMillis() - start2);

        long start3 = System.currentTimeMillis();
        MergeSort.sort(arr3);
        System.out.println(System.currentTimeMillis() - start3);
    }

}
