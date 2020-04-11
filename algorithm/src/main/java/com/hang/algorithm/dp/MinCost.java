package com.hang.algorithm.dp;

/**
 * @author hangs.zhang
 * @date 2020/03/25 22:22
 * *****************
 * function:粉刷房子,leetcode 256
 * <p>
 * 假如有一排房子，共 n 个，每个房子可以被粉刷成红色、蓝色或者绿色这三种颜色中的一种，你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。
 * 当然，因为市场上不同颜色油漆的价格不同，所以房子粉刷成不同颜色的花费成本也是不同的。每个房子粉刷成不同颜色的花费是以一个 n x 3 的矩阵来表示的。
 * 例如，costs[0][0]表示第 0 号房子粉刷成红色的成本花费；costs[1][2]表示第 1 号房子粉刷成绿色的花费，以此类推。请你计算出粉刷完所有房子最少的花费成本。
 * 注意: 所有花费均为正整数。
 * <p>
 * 输入: [[17,2,17],[16,16,5],[14,3,19]]
 * 输出: 10
 * 解释: 将 0 号房子粉刷成蓝色，1 号房子粉刷成绿色，2 号房子粉刷成蓝色。最少花费: 2 + 5 + 3 = 10。
 */
public class MinCost {

    // 二维dp,dp[i][k]存储了i位置的房子使用第k种颜色的最小花费
    // dp[i][k] = Math.min(dp[i-1][0],dp[i-1][1],dp[i-1][2]) + costs[i][k]
    public static int minCost(int[][] costs) {
        int[][] dp = new int[costs.length][3];
        int red = 0;
        int blue = 1;
        int green = 2;
        dp[0][red] = costs[0][red];
        dp[0][blue] = costs[0][blue];
        dp[0][green] = costs[0][green];

        for (int i = 1; i < costs.length; i++) {
            dp[i][red] = Math.min(dp[i - 1][blue], dp[i - 1][green]) + costs[i][red];
            dp[i][blue] = Math.min(dp[i - 1][red], dp[i - 1][green]) + costs[i][blue];
            dp[i][green] = Math.min(dp[i - 1][red], dp[i - 1][blue]) + costs[i][green];
        }
        return Math.min(dp[costs.length - 1][red], Math.min(dp[costs.length - 1][blue], dp[costs.length - 1][green]));
    }

    public static void main(String[] args) {
        int[][] costs = {{17, 2, 17}, {16, 16, 5}, {14, 3, 19}};
        System.out.println(minCost(costs));
    }

}
