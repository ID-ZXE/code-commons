package com.github.leetcode.easy.dp;

/**
 * @author hangs.zhang
 * @date 2020/07/01 21:34
 * *****************
 * function:674. 最长连续递增序列
 * 给定一个未经排序的整数数组，找到最长且连续的的递增序列，并返回该序列的长度。
 * 示例 1:
 * 输入: [1,3,5,4,7]
 * 输出: 3
 * 解释: 最长连续递增序列是 [1,3,5], 长度为3。
 * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。
 */
public class FindLengthOfLCIS {

    // dp[i]存储从j(j < i)到当前位置的递增序列的最长长度
    public static int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int result = dp[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }
            result = Math.max(dp[i], result);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 4, 7};
        System.out.println(findLengthOfLCIS(arr));
    }

}
