package com.github.redis.token.bucket.utils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author zhanghang
 * @date 2021/1/6 7:45 下午
 * *****************
 * function:
 */
public final class RedisUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final RedisURI REDIS_URI = RedisURI.builder().withHost("127.0.0.1").withPassword("redis").withPort(6379).build();

    private static final RedisClient REDIS_CLIENT = RedisClient.create(REDIS_URI);

    private static final StatefulRedisConnection<String, String> REDIS_CONNECTION = REDIS_CLIENT.connect();

    private RedisUtils() {
    }

    public static void set(String key, String value) {
        RedisCommands<String, String> sync = REDIS_CONNECTION.sync();
        String result = sync.set(key, value);
        LOGGER.trace("result:{}", result);
    }

    public static String get(String key) {
        RedisCommands<String, String> sync = REDIS_CONNECTION.sync();
        return sync.get(key);
    }

    public static void main(String[] args) {
        set("test-key", "test-value");
    }

}
