package com.github.redis.token.bucket.test;

import com.github.redis.token.bucket.TokenBucket;
import com.github.redis.token.bucket.utils.RedisUtils;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhanghang
 * @date 2021/1/7 12:37 上午
 * *****************
 * function:
 */
public class TestCase {

    private static final ExecutorService executors = Executors.newFixedThreadPool(100);

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TokenBucket build() {
        return new TokenBucket(10, 1000, "FIRST");
    }

    @Test
    public void tryAcquire() {
        TokenBucket tokenBucket = build();
        StatefulRedisConnection<String, String> connection = RedisUtils.getConnection();

        RedisCommands<String, String> command = connection.sync();
        command.multi();
        command.watch("TOKEN_BUCKET-FIRST");
        tokenBucket.store(connection);
        tokenBucket.load(connection);
        command.exec();
        LOGGER.info("nextFreeTicketMicros {}", tokenBucket.getNextFreeTicketMicros());
    }

}
