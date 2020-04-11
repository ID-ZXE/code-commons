package com.hang.algorithm.leetcode.easy.dp;

import java.util.Objects;

/**
 * @author hangs.zhang
 * @date 2020/03/27 23:04
 * *****************
 * function:leetcode 392. 判断子序列
 * <p>
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 * 你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 * <p>
 * 示例 1:
 * s = "abc", t = "ahbgdc"
 * 返回 true.
 * <p>
 * 示例 2:
 * s = "axc", t = "ahbgdc"
 * 返回 false.
 */
public class IsSubsequence {

    // 暴力解法
    public static boolean isSubsequence(String s, String t) {
        int position = 0;
        boolean flag;
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            flag = false;
            for (int j = position; j < t.length(); j++) {
                if (Objects.equals(a, t.charAt(j))) {
                    position = j + 1;
                    flag = true;
                    break;
                }
                position++;
            }
            if(!flag) return false;
        }
        return true;
    }

    // 双指针
    public static boolean isSubsequence2(String s, String t) {
        int i = 0, j = 0;
        while(i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                ++i;
            }
            ++j;
        }
        if (i == s.length()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isSubsequence("", "ahbgdc"));
        System.out.println(isSubsequence("abc", "ahbgdc"));
        System.out.println(isSubsequence("axc", "ahbgdc"));
    }

}
