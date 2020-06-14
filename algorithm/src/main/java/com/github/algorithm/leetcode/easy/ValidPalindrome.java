package com.github.algorithm.leetcode.easy;

/**
 * @author hangs.zhang
 * @date 2020/05/26 23:05
 * *****************
 * function: leetcode 125
 */
public class ValidPalindrome {

    private static final int[] arrs = new int[256];

    private static final int a = 'a' - 'A';

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            arrs[i] = 1;
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            arrs[i] = 1;
        }
        for (int i = '0'; i <= '9'; i++) {
            arrs[i] = 1;
        }
    }

    public boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        int i = 0, j = chars.length - 1;
        while (i < j) {
            while (i < j && arrs[chars[i]] != 1) i++;
            while (i < j && arrs[chars[j]] != 1) j--;

            if ((int) Character.toLowerCase(chars[i]) != (int) Character.toLowerCase(chars[j])) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
//        System.out.println(new IsPalindrome2().isPalindrome("race a car"));
//        System.out.println(new IsPalindrome2().isPalindrome("asddsa"));
//        System.out.println(new IsPalindrome2().isPalindrome("A man, a plan, a canal: Panama"));
//        System.out.println(new IsPalindrome2().isPalindrome("A"));
//        System.out.println(new IsPalindrome2().isPalindrome(" "));
        System.out.println(new ValidPalindrome().isPalindrome("0P"));
    }

}
