package com.github.redis.token.bucket.utils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author zhanghang
 * @date 2021/1/6 7:45 下午
 * *****************
 * function:
 */
@SuppressWarnings("all")
public final class RedisUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final RedisURI REDIS_URI = RedisURI.builder().withHost("127.0.0.1").withPassword("redis").withPort(6379).build();

    private static final RedisClient REDIS_CLIENT = RedisClient.create(REDIS_URI);

    private static final GenericObjectPool<StatefulRedisConnection<String, String>> POOL;

    private static final StatefulRedisConnection<String, String> REDIS_CONNECTION = REDIS_CLIENT.connect();

    private RedisUtils() {

    }

    static {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(10);
        POOL = ConnectionPoolSupport.createGenericObjectPool(REDIS_CLIENT::connect, poolConfig);
    }

    public static StatefulRedisConnection<String, String> getConnection() {
        StatefulRedisConnection<String, String> connection = null;
        try {
            connection = POOL.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("get redis connection fail", e);
        }
        return connection;
    }

    public static void close(StatefulRedisConnection<String, String> connection) {
        connection.close();
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
