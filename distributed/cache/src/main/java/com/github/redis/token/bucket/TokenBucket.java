package com.github.redis.token.bucket;

import com.github.redis.token.bucket.utils.JsonUtils;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

/**
 * @author zhanghang
 * @date 2021/1/6 7:31 下午
 * *****************
 * function:
 */
@Getter
public class TokenBucket {

    /**
     * 当前存储令牌数
     */
    private int storedPermits;

    /**
     * 最大存储令牌数
     */
    private int maxPermits;

    /**
     * 添加令牌时间间隔, 单位ms
     */
    private int stableIntervalMicros;

    /**
     * 下一次请求可以获取令牌的起始时间
     * 由于RateLimiter允许预消费, 上次请求预消费令牌后
     * 下次请求需要等待相应的时间到nextFreeTicketMicros时刻才可以获取令牌
     */
    private long nextFreeTicketMicros = 0L;

    private final transient String tokenBucketUniqMark;

    private final transient StatefulRedisConnection<String, String> redisConnection;

    private static final String REDIS_KEY = "TOKEN_BUCKET-%s";

    private static final ThreadLocal<RedisCommands<String, String>> COMMANDS_THREAD_LOCAL = new ThreadLocal<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TokenBucket(int maxPermits, int stableIntervalMicros, String tokenBucketUniqMark,
                       StatefulRedisConnection<String, String> connection) {
        this.maxPermits = maxPermits;
        this.stableIntervalMicros = stableIntervalMicros;
        this.tokenBucketUniqMark = tokenBucketUniqMark;
        this.redisConnection = connection;
        this.nextFreeTicketMicros = System.currentTimeMillis();

        RedisCommands<String, String> command = redisConnection.sync();
        COMMANDS_THREAD_LOCAL.set(command);
        store();
    }

    private void resync(long nowMicros) {
        load();
        if (nowMicros > nextFreeTicketMicros) {
            int newPermits = (int) (nowMicros - nextFreeTicketMicros) / stableIntervalMicros;
            newPermits = newPermits * maxPermits;
            // 不能超过令牌桶的size
            storedPermits = Math.min(maxPermits, storedPermits + newPermits);
            nextFreeTicketMicros = nowMicros;
        }
    }

    private void store() {
        TokenBucketData tokenBucketData = new TokenBucketData(storedPermits, maxPermits, stableIntervalMicros, nextFreeTicketMicros);
        RedisCommands<String, String> command = COMMANDS_THREAD_LOCAL.get();
        command.set(getTokenBucketKey(), JsonUtils.toJson(tokenBucketData));
    }

    private void load() {
        RedisCommands<String, String> command = COMMANDS_THREAD_LOCAL.get();
        TokenBucketData tokenBucketData = JsonUtils.fromJson(command.get(getTokenBucketKey()), TokenBucketData.class);
        tokenBucketData.copyProperties(this);
    }

    private String getTokenBucketKey() {
        return String.format(REDIS_KEY, tokenBucketUniqMark);
    }

    public boolean tryAcquire(int permit) {
        RedisCommands<String, String> command = COMMANDS_THREAD_LOCAL.get();
        // redis 自带的cas
        while (true) {
            LOGGER.info("tryAcquire");
            command.watch(getTokenBucketKey());
            command.multi();
            if (!doAcquire(permit)) {
                COMMANDS_THREAD_LOCAL.remove();
                return false;
            }

            TransactionResult transactionResult = command.exec();
            Object result = transactionResult.get(0);
            LOGGER.info("result:{}", result);
            if (Objects.equals(result.toString(), "OK")) {
                COMMANDS_THREAD_LOCAL.remove();
                return true;
            }
        }
    }

    public boolean doAcquire(int permit) {
        resync(System.currentTimeMillis());
        int storedPermitsToSpend = Math.min(permit, this.storedPermits);
        int freshPermits = permit - storedPermitsToSpend;
        if (freshPermits < 0) return false;
        this.storedPermits -= storedPermitsToSpend;
        store();
        return true;
    }

    @Data
    static class TokenBucketData {
        private int storedPermits;
        private int maxPermits;
        private int stableIntervalMicros;
        private long nextFreeTicketMicros;

        public void copyProperties(TokenBucket tokenBucket) {
            tokenBucket.maxPermits = maxPermits;
            tokenBucket.nextFreeTicketMicros = nextFreeTicketMicros;
            tokenBucket.storedPermits = storedPermits;
            tokenBucket.stableIntervalMicros = stableIntervalMicros;
        }

        public TokenBucketData(int storedPermits, int maxPermits, int stableIntervalMicros, long nextFreeTicketMicros) {
            this.storedPermits = storedPermits;
            this.maxPermits = maxPermits;
            this.stableIntervalMicros = stableIntervalMicros;
            this.nextFreeTicketMicros = nextFreeTicketMicros;
        }
    }

}
