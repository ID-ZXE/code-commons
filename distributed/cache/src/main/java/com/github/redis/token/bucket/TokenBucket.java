package com.github.redis.token.bucket;

import lombok.Data;

/**
 * @author zhanghang
 * @date 2021/1/6 7:31 下午
 * *****************
 * function:
 */
public class TokenBucket {

    @Data
    private static class Token {

        /**
         * 当前存储令牌数
         */
        private double storedPermits;

        /**
         * 最大存储令牌数
         */
        private double maxPermits;

        /**
         * 添加令牌时间间隔
         */
        private double stableIntervalMicros;

        /**
         * 下一次请求可以获取令牌的起始时间
         * 由于RateLimiter允许预消费, 上次请求预消费令牌后
         * 下次请求需要等待相应的时间到nextFreeTicketMicros时刻才可以获取令牌
         */
        private long nextFreeTicketMicros = 0L;

    }

    public boolean tryAcquire(int permit) {
        return true;
    }

}
