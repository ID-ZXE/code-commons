package com.github.redis.token.bucket.test;

import com.github.redis.token.bucket.TokenBucket;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
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
        RedisURI redisURI = RedisURI.builder().withHost("127.0.0.1").withPassword("zhanghang").withPort(6379).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        return new TokenBucket(10, 1000, "FIRST", redisClient.connect());
    }

    @Test
    public void tryAcquire() throws InterruptedException {
        TokenBucket tokenBucket = build();
        // 预热
        Thread.sleep(1000);
        tokenBucket.tryAcquire(1);

//        for (int i = 0; i < 5; i++) {
//            executors.submit(() -> {
//                for (int num = 1; num < 10; num++) {
//                    if (tokenBucket.tryAcquire(1)) {
//                        LOGGER.info("acquire permit 1");
//                        // 业务处理
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            // do nothing
//                        }
//                    } else {
//                        LOGGER.info("can`t acquire permit 1");
//                    }
//                }
//            });
//        }
//
//        Thread.sleep(10000);
    }

}
