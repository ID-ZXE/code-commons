package com.github.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhanghang
 * @date 2022/3/19 5:18 下午
 * *****************
 * function:
 */
@Slf4j
public class Base62 {

    public static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        // 七位的62进制的第一位转换为10进制
        long start = 56800235584L;
        // 七位的62进制的最后一位转换为10进制
        long end = 519582482883L;
        for (int i = 0; i < 100; i++) {
            start += 100;

            String str = encodeBase10(start);
            log.info("encodeBase10:{}", str);
            log.info("decodeBase62:{}", decodeBase62(str));
        }
    }

    public static String encodeBase10(long b10) {
        if (b10 < 0) {
            throw new IllegalArgumentException("b10 must be non negative");
        }
        StringBuilder ret = new StringBuilder();
        while (b10 > 0) {
            ret.append(CHARACTERS.charAt((int) (b10 % 62)));
            b10 /= 62;
        }
        return ret.toString();

    }

    public static long decodeBase62(String b62) {
        for (char character : b62.toCharArray()) {
            if (!CHARACTERS.contains(String.valueOf(character))) {
                throw new IllegalArgumentException("Invalid character(s) in string: " + character);
            }
        }
        long ret = 0;
        b62 = new StringBuffer(b62).reverse().toString();
        long count = 1;
        for (char character : b62.toCharArray()) {
            ret += CHARACTERS.indexOf(character) * count;
            count *= 62;
        }
        return ret;
    }


}
