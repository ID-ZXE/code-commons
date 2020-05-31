package com.github.algorithm.structure.sort;

import com.github.algorithm.utils.PrintUtils;
import com.github.algorithm.utils.SortTestHelper;

/**
 * @author hangs.zhang
 * @date 2020/05/28 08:36
 * *****************
 * function:
 */
public class QuickSort {

    /**
     * 时间复杂度 O(nlogn)
     * 不断的将数组一分为二, 与归并不同的是, 快速排序无法一分为二, 所以性能不稳定
     * 最差情况为O(n^2),最侧元素为最小元素
     */
    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int partition = partition(arr, left, right);
        quickSort(arr, left, partition - 1);
        quickSort(arr, partition + 1, right);
    }

    /**
     * 返回partition
     * arr[left...partition-1] < arr[partition]
     * arr[partition+1...right] > arr[partition]
     */
    private static int partition(int[] arr, int left, int right) {
        // SortTestHelper.swap(arr, left, left + Math.abs(new Random().nextInt((right - left + 1))));
        int v = arr[left];
        int k = left;
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] < v) {
                SortTestHelper.swap(arr, k + 1, i);
                k++;
            }
        }
        SortTestHelper.swap(arr, left, k);
        return k;
    }

    public static void findTarget(int[] arr, int target) {
        findTarget(arr, 0, arr.length - 1, target);
    }

    private static void findTarget(int[] arr, int left, int right, int target) {
        if (left >= right) return;

        int partition = partition(arr, left, right);
        if (target == partition) {
            return;
        } else if (target > partition) {
            quickSort(arr, partition + 1, right);
        } else {
            quickSort(arr, left, partition - 1);
        }
    }

    public static void main(String[] args) {
        int[] arr = SortTestHelper.generateRandomArray(100, -100, 100);
        sort(arr);
        PrintUtils.print(arr);

        int[] arr2 = {123, 1, 3, 7, 5, 100, 2, 60};
        findTarget(arr2, 3);
        System.out.println(arr2[3]);
    }

}
