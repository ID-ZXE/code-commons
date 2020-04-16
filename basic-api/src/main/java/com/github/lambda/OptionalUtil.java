package com.github.lambda;

import java.util.Optional;

/**
 * @author hangs.zhang
 * @date 2018/11/2
 * *****************
 * function:
 */
public class OptionalUtil {

    public static Optional<Integer> str2Int(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Long> str2Long(String str) {
        try {
            return Optional.of(Long.parseLong(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        Optional<Integer> num = OptionalUtil.str2Int("xx");
        System.out.println(num.orElse(0));
    }

}
