package com.github.algorithm.leetcode.easy;

/**
 * @author hangs.zhang
 * @date 2020/03/21 20:11
 * *****************
 * function:
 */
public class LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {
        String shortStr = "";
        int shortLength = Integer.MAX_VALUE;
        for (String str : strs) {
            if (str.length() < shortLength) {
                shortLength = str.length();
                shortStr = str;
            }
        }

        while (!"".equals(shortStr)) {
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                if (!str.startsWith(shortStr)) {
                    break;
                }

                if(i == strs.length - 1) {
                    return shortStr;
                }
            }
            shortStr = shortStr.substring(0, shortStr.length() - 1);
        }
        return "";
    }

    public static void main(String[] args) {
        String[] strs = {"flower","flow","flight"};
        System.out.println(longestCommonPrefix(strs));


        String[] strs2 = {"ca","a"};
        System.out.println(longestCommonPrefix(strs2));
    }

}
