package com.github.greedy.easy;

/**
 * @author zhanghang
 * @date 2020/12/13 11:33 上午
 * *****************
 * function:
 */
public class CanPlaceFlowers {

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (flowerbed.length == 0 && n == 0) return true;
        if (flowerbed.length == 1 && flowerbed[0] == 0 && n == 1) return true;
        if (flowerbed.length == 1 && flowerbed[0] == 0 && n == 0) return true;

        int result = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                continue;
            }
            if (i == 0 && flowerbed.length > 1) {
                if (flowerbed[i + 1] != 1) {
                    result++;
                    flowerbed[i] = 1;
                }
                continue;
            }
            if (i == flowerbed.length - 1) {
                if (flowerbed[i - 1] != 1) {
                    result++;
                    flowerbed[i] = 1;
                }
                continue;
            }

            if (flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                result++;
                flowerbed[i] = 1;
            }
        }
        return result >= n;
    }

    public static void main(String[] args) {
        int[] arr = {1, 0, 0, 0, 1};
        System.out.println(canPlaceFlowers(arr, 1));
        System.out.println(canPlaceFlowers(arr, 2));

        int[] arr2 = {1, 0, 0, 0, 0, 0, 1};
        System.out.println(canPlaceFlowers(arr2, 3));

        int[] arr3 = {0, 1, 0};
        System.out.println(canPlaceFlowers(arr3, 1));

        int[] arr4 = {0};
        System.out.println(canPlaceFlowers(arr4, 1));

        int[] arr5 = {1};
        System.out.println(canPlaceFlowers(arr5, 1));
    }

}
