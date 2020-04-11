package com.hang.guava.collection;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hangs.zhang
 * @date 19-7-25 上午11:41
 * *********************
 * function:
 */
public class CollectionsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void transformTest() {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        List<String> transformList = list.stream().map(e -> "[" + e + "]").collect(Collectors.toList());
        System.out.println(transformList);
        // 异常
        try {
            transformList.add("[6]");
        } catch (UnsupportedOperationException e) {
            LOGGER.info("expected error", e);
        }
    }

    @Test
    public void iterableTest() {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Optional<Integer> optional = Iterables.tryFind(list, e -> e > 3);
        System.out.println(optional.get());
    }

    @Test
    public void sort() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        Collections.shuffle(list);
        System.out.println(list);

        Collections.sort(list, Ordering.natural().reversed());
        System.out.println(list);

        Collections.sort(list, Ordering.natural().reverse().onResultOf(new Function<Integer, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable Integer input) {
                return input > 3 ? -1 : 1;
            }
            // compound 表示两个值相等之后 后续的比较器
        }).compound(Ordering.natural()));
        System.out.println(list);

    }

}
