package com.github.algorithm.leetcode.easy;

/**
 * @author hangs.zhang
 * @date 2020/05/27 16:02
 * *****************
 * function: leetcode 283 move zeros
 */
public class MoveZeroes {

    /**
     * k为非0元素移动的索引位置
     */
    public void moveZeroes(int[] nums) {
        int k = 0;
        // 将不为0的元素按顺序放置到数组[0...k)中
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[k++] = nums[i];
            }
        }
        // 将[k...length-1]置为0
        for (int i = k; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

}
