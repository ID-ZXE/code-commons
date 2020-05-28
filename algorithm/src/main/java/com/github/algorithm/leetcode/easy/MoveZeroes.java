package com.github.algorithm.leetcode.easy;

/**
 * @author hangs.zhang
 * @date 2020/05/27 16:02
 * *****************
 * function:
 */
public class MoveZeroes {

    /**
     * k为非0元素移动的索引位置
     */
    public void moveZeroes(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[k++] = nums[i];
            }
        }
        for (int i = k; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

}
