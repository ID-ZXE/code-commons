package com.hang.algorithm.dp;

import java.util.Arrays;

/**
 * @author hangs.zhang
 * @date 2020/3/25 上午10:10
 * *********************
 * function:
 * leetcode 300
 * 求无序数组中最长递增子序列的长度
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 */
public class LengthOfLIS {

    public static int lengthOfLIS(int[] arr) {
        int[] dp = new int[arr.length];
        // 初始化 每个单独的位置都是一个子串,长度为1
        // dp中存放的是每一个位置往前算的值(0,当前位置)的最长递增子序列
        Arrays.fill(dp, 1);
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                // 只要当前位置比前面的大,递增子序列就成立
                // 从0往后到当前位置,求最大自增子序列的长度
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = {10,9,2,5,3,7,101,18};
        System.out.println(lengthOfLIS(arr));
    }

}
