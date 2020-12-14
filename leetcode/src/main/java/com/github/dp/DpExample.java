package com.github.dp;

import org.junit.Test;

import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/03/24 22:19
 * *****************
 * function:
 */
public class DpExample {

    // n个1相加
    public static int dpExample1(int n) {
        // 多一个,使用dp[0]来存储0位置的数据
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            dp[i] = dp[i - 1] + 1;
        }
        return dp[n];
    }

    @Test
    public void test1() {
        System.out.println(dpExample1(10));
    }

    //爬楼梯问题 leetcode 70
    //假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
    //每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    //注意：给定 n 是一个正整数。
    //dp[i] = dp[i-1] + dp[i-2]
    public static int doExample2(int n) {
        if (n == 1) {
            return 1;
        }
        // 多开一位，考虑起始位置
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    @Test
    public void test2() {
        System.out.println(doExample2(1));
        System.out.println(doExample2(2));
        System.out.println(doExample2(3));
        System.out.println(doExample2(4));
        System.out.println(doExample2(5));
        System.out.println(doExample2(6));
    }

    // 递归也可以解决问题
    // 三角形最小路径和 leetcode 120
    //    [
    //        [2],
    //       [3,4],
    //      [6,5,7],
    //     [4,1,8,3]
    //    ]
    // 2 + 3 + 5 + 1 = 11
    public static int doExample3(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];
        List<Integer> lastRow = triangle.get(n - 1);
        // [i][j] 位置的元素，经过这个元素的路径肯定也会经过 [i - 1][j] 或者 [i - 1][j - 1]
        // 获取到最下层的数据 从最下层递推 下往上走
        // 最下层到当前位置的最小路径,一直到dp[0][0]
        for (int i = 0; i < n; ++i) {
            dp[n - 1][i] = lastRow.get(i);
        }

        for (int i = n - 2; i >= 0; --i) {
            List<Integer> row = triangle.get(i);
            for (int j = 0; j < i + 1; ++j) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + row.get(j);
            }
        }
        return dp[0][0];
    }

    // 最大子序和 leetcode 53
    // dp问题就是记住之前的状态
    // 在这个问题中,之前的状态就是当前位置之前的子序列最大和与当前位置的数据之和是否大于0
    // 如果大于0,那么就继续累加,如果小于0,就从当前位置开始计算
    public static int doExample4(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = 0;
        for (int i = 1; i < arr.length; i++) {
            // dp[i] = Math.max(dp[i - 1] + array[i], array[i])
            // 简化
            dp[i] = Math.max(dp[i - 1], 0) + arr[i];
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }

    @Test
    public void test4() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(doExample4(arr));
    }

}
