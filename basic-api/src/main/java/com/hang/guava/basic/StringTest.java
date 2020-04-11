package com.hang.guava.basic;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午9:21
 * *********************
 * function:
 */
public class StringTest {

    @Test
    public void stringBuilderAndBuffer() {
        // StringBuilder 更快 线程不安全
        StringBuilder sb = new StringBuilder();
        String str = null;
        sb.append("test.").append(str);
        System.out.println(sb.toString());
    }

    @Test
    public void strings() {
        // null
        String str = null;
        System.out.println(Strings.isNullOrEmpty(str));
        System.out.println(str);
        System.out.println(Strings.nullToEmpty(str));

        // common
        String a = "12abc";
        String b = "123abc";
        System.out.println(Strings.commonPrefix(a, b));
        System.out.println(Strings.commonSuffix(a, b));

        // pad
        String s = "1";
        System.out.println(Strings.padStart(s, 5, '*'));
        System.out.println(Strings.padEnd(s, 5, '*'));
        System.out.println(Strings.repeat(s, 10));
    }

    // skipNull
    private static final Joiner JOINER = Joiner.on("|").skipNulls();

    private static final Joiner joiner2 = Joiner.on("|").useForNull("null");

    private static final Joiner.MapJoiner joiner3 = Joiner
            .on("&")
            .useForNull("null")
            .withKeyValueSeparator("=");

    @Test
    public void joinerTest() {
        List<Integer> list = Lists.newArrayList(1, 3, null, 5);
        System.out.println(JOINER.join(list));
        System.out.println(joiner2.join(list));

        String host = "www.baidu.com";
        Map<String, String> params = Maps.newHashMap();
        params.put("name", "hangs.zhang");
        params.put("id", "15");
        String queryString = joiner3.join(params);
        System.out.println(host + "?" + queryString);
    }

    private static Splitter splitter = Splitter.on(",");

    @Test
    public void splitterTest() {
        String str = ",a, b,";
        // 最后一个空串没有放进去
        System.out.println(Arrays.toString(str.split(",")));
        System.out.println(splitter.splitToList(str));
        System.out.println(splitter.omitEmptyStrings().trimResults().splitToList(str));

        String queryStr = "a=1&b=2";
        Map<String, String> queryMap = Splitter.on("&")
                .withKeyValueSeparator("=")
                .split(queryStr);
        System.out.println(queryMap);
    }

    @Test
    public void charMatcherTest() {
        CharMatcher charMatcher = CharMatcher.anyOf("abcd").or(CharMatcher.is('2'));
        String str = "acbd1234abcd5678acbd";
        System.out.println(charMatcher.removeFrom(str));
        System.out.println(charMatcher.retainFrom(str));
        System.out.println(charMatcher.trimFrom(str));

        System.out.println(charMatcher.matches('a'));
        System.out.println(charMatcher.matches('e'));
    }

    @Test
    public void charMatcherWithSplitter() {
        String str = "1abc1,a123a";
        System.out.println(Splitter.on(",")
                .trimResults(CharMatcher.anyOf("1a3")).splitToList(str));
    }

}
