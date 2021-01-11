package com.github.redis.concurrent;

import com.github.redis.utils.RedisUtils;

import java.time.*;
import java.util.UUID;

/**
 * @author zhanghang
 * @date 2021/1/10 4:23 下午
 * *****************
 * function:
 */
public class FixedWindowRedisRateLimiter extends RateLimiter {

    public FixedWindowRedisRateLimiter(int permitsPerSecond) {
        this(UUID.randomUUID().toString(), permitsPerSecond);
    }

    public FixedWindowRedisRateLimiter(String name, int permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
        this.name = name;
        this.prefix = "F:RATE_LIMIT:";
    }

    private String getRedisKey(long currentSeconds) {
        return String.format("%s:%s:%s", prefix, name, currentSeconds);
    }

    @Override
    public long tryAcquire() {
        return tryAcquire(1);
    }

    @Override
    public long tryAcquire(int permits) {
        long epochSecond = Instant.now().getEpochSecond();
        // 睡眠到下一个时间窗口
        long sleepTime = 1000 * (epochSecond + 1) - System.currentTimeMillis();
        String redisKey = getRedisKey(epochSecond);
        // 核心逻辑
        RedisUtils.setNx(redisKey, String.valueOf(permitsPerSecond), 1000);
        long val = RedisUtils.decr(redisKey);
        // val>=0时, 表示获取到了permit
        if (val >= 0) return 0;
        return sleepTime;
    }

}
