package com.github.leetcode.medium.dp;

import java.util.Arrays;

/**
 * @author hangs.zhang
 * @date 2020/07/01 21:54
 * *****************
 * function:673. 最长递增子序列的个数
 * 给定一个未排序的整数数组，找到最长递增子序列的个数。
 * 示例 1:
 * 输入: [1,3,5,4,7]
 * 输出: 2
 * 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
 */
public class FindNumberOfLIS {

    public static int findNumberOfLIS(int[] nums) {
        if (nums.length <= 1) return nums.length;
        int[] lengths = new int[nums.length];
        int[] counts = new int[nums.length];
        Arrays.fill(counts, 1);

        for (int i = 0; i < nums.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (nums[j] < nums[i]) {
                    if (lengths[j] >= lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }
                }
            }
        }

        int longest = 0, ans = 0;
        for (int length : lengths) {
            longest = Math.max(longest, length);
        }
        for (int i = 0; i < nums.length; ++i) {
            if (lengths[i] == longest) {
                ans += counts[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 4, 7};
        System.out.println(findNumberOfLIS(arr));
    }

}
