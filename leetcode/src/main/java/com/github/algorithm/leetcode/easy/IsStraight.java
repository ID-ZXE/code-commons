package com.github.algorithm.leetcode.easy;

import java.util.Arrays;

/**
 * @author hangs.zhang
 * @date 2020/03/27 22:01
 * *****************
 * function:leetcode 61. 扑克牌中的顺子
 * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
 *
 * 示例 1:
 * 输入: [1,2,3,4,5]
 * 输出: True
 *
 * 示例 2:
 * 输入: [0,0,1,2,5]
 * 输出: True
 */
public class IsStraight {

    public static boolean IsStraight(int[] nums) {
        // 排序
        Arrays.sort(nums);
        int zeroSize = 0;
        // 计算0的数量
        for (int i = 0; i < 5; i++) {
            if (nums[i] == 0) {
                zeroSize++;
            } else {
                break;
            }
        }

        for (int i = zeroSize; i < 4; i++) {
            if(nums[i+1] == nums[i]) return false;
            // 差值大于1,就用zeroSize去减
            zeroSize -= nums[i+1] - nums[i] - 1;
            // zeroSize<0 说明大小王用完了
            if(zeroSize < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] nums1 = {3, 8, 0, 0, 1};
        int[] nums2 = {8, 10, 0, 6, 0};
        int[] nums3 = {0, 0, 4, 6, 8};
        int[] nums4 = {0, 0, 4, 6, 9};

        System.out.println(IsStraight(nums1));
        System.out.println(IsStraight(nums2));
        System.out.println(IsStraight(nums3));
        System.out.println(IsStraight(nums4));
    }

}
