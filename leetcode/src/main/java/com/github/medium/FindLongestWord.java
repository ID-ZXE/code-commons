package com.github.medium;

import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/1/31 下午9:17
 * *********************
 * function:
 * 给定一个字符串和一个字符串字典，找到字典里面最长的字符串，该字符串可以通过删除给定字符串的某些字符来得到。如果答案不止一个，返回长度最长且字典顺序最小的字符串。如果答案不存在，则返回空字符串。
 * 输入:
 * s = "abpcplea", d = ["ale","apple","monkey","plea"]
 * 输出:
 * "apple"
 */
public class FindLongestWord {

    public String findLongestWord(String s, List<String> d) {
        String longsWord = "";
        for (String target : d) {
            int length1 = longsWord.length();
            int length2 = target.length();
            if (length1 > length2 || (length1 == length2 && longsWord.compareTo(target) < 0)) {
                continue;
            }

            if (isSubstr(s, target)) {
                longsWord = target;
            }
        }
        return longsWord;
    }

    private boolean isSubstr(String s, String target) {
        int i = 0;
        int j = 0;
        while (i < s.length() && j < target.length()) {
            if (s.charAt(i) == target.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == target.length();
    }

    public static void main(String[] args) {

    }

}
