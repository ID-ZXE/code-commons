package com.github.lang;

/**
 * @author hangs.zhang
 * @date 2020/05/04 15:32
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class StringIntern {

    public static void main(String[] args) {
        // 生成了常量池中的a,和堆空间中的字符串对象
        String s1 = new String("a");
        // 将堆中的引用添加到常量池中
        s1.intern();
        // 获取常量池中的对象引用
        String s2 = "a";
        // false
        System.out.println(s1 == s2);

        // 此时常量池中没有aa对象, 堆中才有
        String s3 = new String("a") + new String("a");
        // 将aa的引用放入常量池
        s3.intern();
        // 获取常量池中存储的是aa在堆中的引用, 也就是s3
        String s4 = "aa";
        // true
        System.out.println(s3 == s4);
    }

}
