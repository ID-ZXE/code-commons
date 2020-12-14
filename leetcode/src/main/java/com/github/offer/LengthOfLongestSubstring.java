package com.github.offer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/6/17 上午10:06
 * *********************
 * function:
 * 面试题48. 最长不含重复字符的子字符串
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 */
public class LengthOfLongestSubstring {

    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> dic = new HashMap<>();
        int i = -1;
        int res = 0;
        for (int j = 0; j < s.length(); j++) {
            if (dic.containsKey(s.charAt(j))) {
                // 更新左指针 i
                i = Math.max(i, dic.get(s.charAt(j)));
            }
            // 记录位置
            dic.put(s.charAt(j), j);
            res = Math.max(res, j - i);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

}
