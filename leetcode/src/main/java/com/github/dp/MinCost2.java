package com.github.dp;

import java.util.Arrays;

/**
 * @author hangs.zhang
 * @date 2020/03/25 23:03
 * *****************
 * function:粉刷房子II,leetcode 265
 * 假如有一排房子，共 n 个，每个房子可以被粉刷成 k 种颜色中的一种，你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。
 * 当然，因为市场上不同颜色油漆的价格不同，所以房子粉刷成不同颜色的花费成本也是不同的。每个房子粉刷成不同颜色的花费是以一个 n x k 的矩阵来表示的。
 * 例如，costs[0][0] 表示第 0 号房子粉刷成 0 号颜色的成本花费；costs[1][2] 表示第 1 号房子粉刷成 2 号颜色的成本花费，以此类推。请你计算出粉刷完所有房子最少的花费成本。
 */
public class MinCost2 {

    // 二维dp,dp[i][k]存储了i位置的房子使用第k种颜色的最小花费
    // dp[i][k] = Math.min(dp[i-1][l],dp[i-1][r]) + costs[i][k], l!=k,r!=k
    public static int minCost2(int[][] costs) {
        int[][] dp = new int[costs.length][3];
        int colorSize = costs[0].length;
        int result = Integer.MAX_VALUE;

        for (int i = 1; i < costs.length; ++i) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        for (int i = 0; i < colorSize; ++i) {
            dp[0][i] = costs[0][i];
        }

        for (int i = 1; i < costs.length; i++) {
            for (int j = 0; j < colorSize; j++) {
                int min = Integer.MAX_VALUE;
                int minSite = 0;
                int secondMin = Integer.MAX_VALUE;
                int secondMinSite = 0;
                for (int k = 0; k < colorSize; k++) {
                    if (dp[i - 1][k] < secondMin && dp[i - 1][k] > min) {
                        secondMin = dp[i - 1][k];
                        secondMinSite = k;
                    } else if (dp[i - 1][k] < min) {
                        min = dp[i - 1][k];
                        minSite = k;
                    }
                }

                if (j != minSite) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][minSite] + costs[i][j]);
                } else {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][secondMinSite] + costs[i][j]);
                }
            }
        }

        for (int i = 0; i < colorSize; i++) {
            result = Math.min(dp[dp.length - 1][i], result);
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] costs = {{17, 2, 17}, {16, 16, 5}, {14, 3, 19}};
        int[][] costs2 = {{1,5,3}, {2, 9, 4}};
        System.out.println(minCost2(costs));
        System.out.println(minCost2(costs2));
    }

}
