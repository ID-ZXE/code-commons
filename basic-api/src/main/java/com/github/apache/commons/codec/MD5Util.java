package com.github.apache.commons.codec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author plum-wine
 * @date 2018-01-30 13:18
 * *****************
 * function:
 * 加密工具
 */
public class MD5Util {

    /**
     * 第一次对inputPass加密时使用
     */
    public static String salt = "aBcDeFgH";

    public static String getMd5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 加密
     *
     * @param pass
     * @param salt
     * @return
     */
    public static String encrypt(String pass, String salt) {
        String formPass = inputPass2FormPass(pass);
        return formPass2DataPass(formPass, salt);
    }

    /**
     * 加密从表单来的密码
     * 这一步是客户端做
     *
     * @param inputPass
     * @return
     */
    public static String inputPass2FormPass(String inputPass) {
        String formPass = "" + salt.charAt(0) + inputPass.charAt(1) + inputPass + salt.charAt(2) + inputPass.charAt(3);
        return getMd5(formPass);
    }

    /**
     * 根据随机的salt
     * 加密已经第一次加密后的formPass
     * 生成dataPass（dataPass是存入数据库的密码）
     *
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPass2DataPass(String formPass, String salt) {
        String dataPass = "" + formPass.charAt(3) + salt.charAt(2) + formPass + formPass.charAt(1) + salt.charAt(0);
        return getMd5(dataPass);
    }

}
