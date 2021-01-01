package com.github.easy;

import java.util.HashMap;

/**
 * @author hangs.zhang
 * @date 2020/03/21 19:46
 * *****************
 * function:
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 */
public class RomanToInt {

    private static HashMap<String, Integer> map = new HashMap<>(16);

    static {
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);

        map.put("IV", 4);
        map.put("IX", 9);
        map.put("XL", 40);
        map.put("XC", 90);
        map.put("CD", 400);
        map.put("CM", 900);
    }

    public static int romanToInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            String c = String.valueOf(s.charAt(i));
            if (!"I".equals(c) && !"X".equals(c) && !"C".equals(c)) {
                result += map.get(c);
                continue;
            }

            if(i == s.length() - 1) {
                result += map.get(c);
                return result;
            }

            String c2 = String.valueOf(s.charAt(i + 1));

            if ("I".equals(c)) {
                if ("V".equals(c2)) {
                    result += map.get("IV");
                    i++;
                } else if ("X".equals(c2)) {
                    result += map.get("IX");
                    i++;
                } else {
                    result += map.get(c);
                }
            } else if ("X".endsWith(c)) {
                if ("L".equals(c2)) {
                    result += map.get("XL");
                    i++;
                } else if ("C".equals(c2)) {
                    result += map.get("XC");
                    i++;
                } else {
                    result += map.get(c);
                }
            } else if ("C".equals(c)) {
                if ("D".equals(c2)) {
                    result += map.get("CD");
                    i++;
                } else if ("M".equals(c2)) {
                    result += map.get("CM");
                    i++;
                } else {
                    result += map.get(c);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
//        System.out.println(romanToInt("III"));
//        System.out.println(romanToInt("IV"));
//        System.out.println(romanToInt("IX"));
//        System.out.println(romanToInt("LVIII"));
//        System.out.println(romanToInt("MCMXCIV"));

        // 2425 1000+1000+400+10+10+5
        System.out.println(romanToInt("MMCDXXV"));
    }

}
