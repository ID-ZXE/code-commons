package com.github.algorithm.leetcode.medium.dp;

/**
 * @author hangs.zhang
 * @date 2020/07/01 22:34
 * *****************
 * function:1143. 最长公共子序列
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度。
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
 * 若这两个字符串没有公共子序列，则返回 0。
 */
public class LongestCommonSubsequence {

    public static int longestCommonSubsequence(String text1, String text2) {
        char[] t1 = text1.toCharArray();
        char[] t2 = text2.toCharArray();
        int length1 = t1.length;
        int length2 = t2.length;
        int[][] dp = new int[length1 + 1][length2 + 1];
        // 让索引为0的行和列表示空串, dp[0][..]和dp[..][0]都应该初始化为0, 这就是 base case
        for (int i = 1; i < length1 + 1; i++) {
            for (int j = 1; j < length2 + 1; j++) {
                if (t1[i - 1] == t2[j - 1]) {
                    // 这边找到一个lcs的元素, 继续往前找
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 谁能让lcs最长, 就听谁的
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[length1][length2];
    }

    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence("abcde", "ace"));
    }

}
