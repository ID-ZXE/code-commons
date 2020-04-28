package com.github.algorithm.leetcode.medium.dp;

/**
 * @author hangs.zhang
 * @date 2020/04/27 22:27
 * *****************
 * function:
 * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
 * <p>
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
 * <p>
 * 示例 1:
 * 输入: "12"
 * 输出: 2
 * 解释: 它可以解码为 "AB"（1 2）或者 "L"（12）。
 * <p>
 * 示例 2:
 * 输入: "226"
 * 输出: 3
 * 解释: 它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
 */
public class NumDecodings {

    // dp[i]中存储当前位置的解码总数
    public int numDecodings(String s) {
        char[] arr = s.toCharArray();
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = arr[0] == '0' ? 0 : 1;
        if (s.length() <= 1) return dp[1];
        for (int i = 2; i <= s.length(); i++) {
            // 两位数的大小
            int n = (arr[i - 2] - '0') * 10 + (arr[i - 1] - '0');
            // 连续两位的0,无解
            if (arr[i - 1] == '0' && arr[i - 2] == '0') {
                return 0;
            } else if (arr[i - 2] == '0') {
                // i-2位置是0,说明次数不变
                dp[i] = dp[i - 1];
            } else if (arr[i - 1] == '0') {
                if (n > 26) {
                    // i-1位置是0,i-2不是,则说明是30,40这种,也是无解
                    return 0;
                } else {
                    // 次数和dp[i-2]位置一样,因为当前位置是0,所以必须和前一个元素组一起
                    dp[i] = dp[i - 2];
                }
            } else if (n > 26) {
                // 大于26,无法多一种组合,与前一个元素一致
                dp[i] = dp[i - 1];
            } else {
                // 刨除了其他情况之后的dp表达式
                dp[i] = dp[i - 1] + dp[i - 2];
            }
        }
        return dp[dp.length - 1];
    }

    public static void main(String[] args) {
        System.out.println(new NumDecodings().numDecodings("12"));
        System.out.println(new NumDecodings().numDecodings("226"));
        System.out.println(new NumDecodings().numDecodings("0"));
        System.out.println(new NumDecodings().numDecodings("00"));
        System.out.println(new NumDecodings().numDecodings("01"));
        System.out.println(new NumDecodings().numDecodings("10"));
        System.out.println(new NumDecodings().numDecodings("100"));
        System.out.println(new NumDecodings().numDecodings("110"));
        System.out.println(new NumDecodings().numDecodings("230"));
        System.out.println(new NumDecodings().numDecodings("17"));
        System.out.println(new NumDecodings().numDecodings("27"));
        System.out.println(new NumDecodings().numDecodings("1212"));
    }

}
