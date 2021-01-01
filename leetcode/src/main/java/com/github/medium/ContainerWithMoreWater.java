package com.github.medium;

/**
 * @author hangs.zhang
 * @date 2020/05/31 22:38
 * *****************
 * function:leetcode 11
 */
public class ContainerWithMoreWater {

    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int max = Integer.MIN_VALUE;
        while(l < r) {
            max = Math.max(max, Math.min(height[r], height[l]) * (r - l));
            if(height[l] > height[r]) r--;
            else l++;
        }

        return max;
    }

}
