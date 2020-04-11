package com.hang.algorithm.leetcode.easy;

/**
 * @author hangs.zhang
 * @date 2020/03/20 22:02
 * *****************
 * function:
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * <p>
 * 示例 1:
 * 输入: 123
 * 输出: 321
 * <p>
 * 示例 2:
 * 输入: -123
 * 输出: -321
 * <p>
 * 示例 3:
 * 输入: 120
 * 输出: 21
 * <p>
 * 注意: 假设我们的环境只能存储得下32位的有符号整数，则其数值范围为 [−2~31,2~31−1]。请根据这个假设，如果反转后整数溢出那么就返回 0
 */
public class Reverse {

    public static int reverse(int x) {
        boolean flag = x > 0;

        x = Math.abs(x);
        int result = 0;
        int threshold = Integer.MAX_VALUE / 10;
        int i = 0;
        while (x > 0) {
            int v = x % 10;
            if (i == 9 && result > threshold) {
                return 0;
            }
            result = result * 10 + v;
            x = x / 10;
            i++;
        }
        return flag ? result : -result;
    }

    public static void main(String[] args) {
        System.out.println(reverse(120));
        System.out.println(reverse(-120));
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(1534236469));
        System.out.println(reverse(-2147483412));
        System.out.println(reverse(-2147483648));
        System.out.println(reverse(2147483647));
    }

}
