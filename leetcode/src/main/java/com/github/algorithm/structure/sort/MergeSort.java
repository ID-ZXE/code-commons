package com.github.algorithm.structure.sort;

import com.github.algorithm.utils.PrintUtils;
import com.github.algorithm.utils.SortTestHelper;

/**
 * @author hangs.zhang
 * @date 2020/05/27 22:37
 * *****************
 * function:
 */
public class MergeSort {

    /**
     * O(nlogn)的时间复杂度
     * 不断的将数组一分为二个相同长度的数组
     */
    public static void sort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (right + left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        if (arr[mid] > arr[mid + 1]) {
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int len = right - left + 1;
        if (len <= 15) {
            InsertionSort.insertionSort(arr, left, right);
            return;
        }

        int[] aux = new int[len];
        System.arraycopy(arr, left, aux, 0, len);

        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = aux[j - left];
                j++;
            } else if (j > right) {
                arr[k] = aux[i - left];
                i++;
            } else if (aux[i - left] < aux[j - left]) {
                arr[k] = aux[i - left];
                i++;
            } else {
                arr[k] = aux[j - left];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = SortTestHelper.generateRandomArray(100, 100, 10000);
        PrintUtils.print(arr);
        sort(arr);
        PrintUtils.print(arr);
    }

}

