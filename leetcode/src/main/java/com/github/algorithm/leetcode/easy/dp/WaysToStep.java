package com.github.algorithm.leetcode.easy.dp;

/**
 * @author hangs.zhang
 * @date 2020/05/07 23:24
 * *****************
 * function:
 * 三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。
 * 实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模1000000007。
 */
public class WaysToStep {

    public int waysToStep(int n) {
        int[] dp = new int[n + 1];
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        for (int i = 4; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(new WaysToStep().waysToStep(3));
        System.out.println(new WaysToStep().waysToStep(4));
        System.out.println(new WaysToStep().waysToStep(5));
    }

}
