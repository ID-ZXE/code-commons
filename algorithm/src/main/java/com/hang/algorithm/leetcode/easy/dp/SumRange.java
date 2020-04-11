package com.hang.algorithm.leetcode.easy.dp;

/**
 * @author hangs.zhang
 * @date 2020/03/26 23:26
 * *****************
 * function:leetcode 303. 区域和检索 - 数组不可变
 * 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。
 * <p>
 * 示例:
 * 给定 nums = [-2, 0, 3, -5, 2, -1]，求和函数为 sumRange()
 * sumRange(0, 2) -> 1
 * sumRange(2, 5) -> -1
 * sumRange(0, 5) -> -3
 * <p>
 * 说明:
 * 你可以假设数组不可变。
 * 会多次调用 sumRange 方法。
 */
public class SumRange {

    private int[] sum;

    // sum[i]中存储的是nums[0]+...+nums[i-1]
    public SumRange(int[] nums) {
        sum = new int[nums.length+1];
        for (int i = 0; i < nums.length; i++) {
            sum[i+1] = sum[i] + nums[i];
        }
    }

    // sum[j+1]是nums[0]+...+nums[j]
    // sum[i]是nums[0]+...+nums[i-1]
    // sum[j+1]-sum[i] = nums[i]+...+num[j]
    public int sumRange(int i, int j) {
        return sum[j+1] - sum[i];
    }

    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};
        SumRange sumRange = new SumRange(nums);
        System.out.println(sumRange.sumRange(0,2));
        System.out.println(sumRange.sumRange(2,5));
        System.out.println(sumRange.sumRange(0,5));
    }

}
