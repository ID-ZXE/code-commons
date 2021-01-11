package com.github.redis.concurrent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author zhanghang
 * @date 2021/1/10 3:04 下午
 * *****************
 * function:
 */
@SuppressWarnings("all")
public abstract class RateLimiter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * redis key中附带的唯一标记
     */
    protected String name;

    protected String prefix;

    /**
     * 支持修改速率
     * 加上volatile是为了修改速率之后, 所有的线程都能感知到结果修改
     * 分布式环境下这个变量可以存储在分布式的配置中心里面
     */
    protected volatile int permitsPerSecond;

    public void setRate(int permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    public String getName() {
        return name;
    }

    public boolean acquire() throws InterruptedException {
        return acquire(1);
    }

    /**
     * 当获取不到permit(许可)时, 陷入睡眠, 睡眠时间由子类tryAcquire定义
     * 返回0时表示获取到了permit
     */
    public boolean acquire(int permits) throws InterruptedException {
        while (true) {
            long sleepTime = tryAcquire(permits);
            LOGGER.info("{} sleep {} ms", Thread.currentThread().getName(), sleepTime);
            if (sleepTime == 0) {
                return true;
            }
            // 异常抛出, 响应中断
            Thread.sleep(sleepTime);
        }
    }

    public abstract long tryAcquire();

    /**
     * 由子类实现, 方法逻辑为尝试获取许可, 获取不到则返回需要睡眠的时间
     */
    public abstract long tryAcquire(int permits);

}
