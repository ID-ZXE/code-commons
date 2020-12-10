package com.github.algorithm.leetcode.easy.dp;

/**
 * @author hangs.zhang
 * @date 2020/03/26 22:18
 * *****************
 * function:leetcode 53. 最大子序和
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 */
public class MaxSubArray {

    // dp[i]中存储的是到i的最大子数组的和
    // dp[i] = Math.max(dp[i-1]+nums[i], nums[i])
    public static int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        int result = nums[0];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
            result = Math.max(dp[i], result);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(nums));

        int[] nums2 = {-1,-2};
        System.out.println(maxSubArray(nums2));
    }

}
