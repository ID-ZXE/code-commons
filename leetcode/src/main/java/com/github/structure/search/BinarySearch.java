package com.github.structure.search;

/**
 * @author hangs.zhang
 * @date 2020/05/17 22:16
 * *****************
 * function:
 */
public class BinarySearch {

    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int mid = (right - left) / 2;

        while (right >= left) {
            if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
            mid = left + (right - left) / 2;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(binarySearch(arr, 1));
        System.out.println(binarySearch(arr, 3));
        System.out.println(binarySearch(arr, 5));
        System.out.println(binarySearch(arr, 7));
        System.out.println(binarySearch(arr, 8));
        System.out.println(binarySearch(arr, 0));
    }

}