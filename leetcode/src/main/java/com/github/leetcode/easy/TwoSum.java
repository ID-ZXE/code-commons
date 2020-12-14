package com.github.leetcode.easy;

import com.github.utils.PrintUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2019/12/16 下午8:13
 * *********************
 * function:
 * <p>
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * <p>
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            } else {
                map.put(nums[i], i);
            }
        }
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 15};
        int[] result = twoSum(arr, 13);
        PrintUtils.print(result);
    }

}
