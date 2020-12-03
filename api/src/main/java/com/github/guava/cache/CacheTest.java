package com.github.guava.cache;

import com.google.common.cache.*;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 19-7-28 下午7:03
 * *********************
 * function:
 */
public class CacheTest {

    private static final LoadingCache<String, String> CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            // 过期
            .expireAfterAccess(30, TimeUnit.MINUTES)
            // 刷新缓存
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            // 删除监听
            .removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    System.out.println("remove");
                }
            })
            // 缓存使用情况
            .recordStats()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return "null";
                }
            });

    @Test
    public void test() throws ExecutionException {
        // 找不到之后 会走load方法
        String result = CACHE.get("null");
        System.out.println(result);

        // 报错
        CACHE.put("null", null);
    }

}
