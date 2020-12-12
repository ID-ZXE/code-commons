package com.github.thread;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhanghang
 * @date 2020/12/12 5:49 下午
 * *****************
 * function:
 * BenchmarkMode注解
 * 1. Throughput: 整体吞吐量, 例如”1秒内可以执行多少次调用”
 * 2. AverageTime: 调用的平均时间, 例如”每次调用平均耗时xxx毫秒”
 * 3. SampleTime: 随机取样, 最后输出取样结果的分布, 例如”99%的调用在xxx毫秒以内, 99.99%的调用在xxx毫秒以内”
 * 4. SingleShotTime: 以上模式都是默认一次 iteration 是 1s, 唯有 SingleShotTime 是只运行一次.往往同时把 warmup 次数设为0，用于测试冷启动时的性能
 * <p>
 * Warmup注解
 * 进行基准测试前需要进行预热. 一般我们前几次进行程序测试的时候都会比较慢，, 所以要让程序进行几轮预热, 保证测试的准确性. 其中的参数iterations也就非常好理解了, 就是预热轮数
 * <p>
 * Measurement注解
 * 1. iterations 进行测试的轮次
 * 2. time 每轮进行的时长
 * 3. timeUnit 时长单位
 * <p>
 * Threads注解
 * 每个进程中的测试线程
 * <p>
 * Fork
 * 进行fork的次数, 如果fork数是2的话, 则JMH会fork出两个进程来进行测试.
 * <p>
 * OutputTimeUnit注解
 * 基准测试结果的时间类型. 一般选择秒、毫秒、微秒
 * <p>
 * Benchmark注解
 * 表示该方法是需要进行benchmark的对象, 用法和JUnit的@Test类似.
 * <p>
 * State注解
 * 共享参数的范围
 * <p>
 * Param注解
 * 注入的共享参数
 */
@SuppressWarnings("all")
@Fork(1)
@Threads(1)
@Warmup(iterations = 1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
public class BenchmarkFairAndNonFair {

    @Param({"10", "50", "100", "300", "500"})
    private int threadNum;

    private static final int FOREACH_COUNT = 10000;

    @Benchmark
    public void testFairLock() throws InterruptedException {
        final Lock fairLock = new ReentrantLock(true);
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < FOREACH_COUNT; j++) {
                    fairLock.lock();
                    fairLock.unlock();
                }
                countDownLatch.countDown();
            }));
        }
        threads.forEach(thread -> thread.start());
        // main wait all thread
        countDownLatch.await();
    }

    @Benchmark
    public void testNonFairLock() throws InterruptedException {
        final Lock nonFairLock = new ReentrantLock(false);
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < FOREACH_COUNT; j++) {
                    nonFairLock.lock();
                    nonFairLock.unlock();
                }
                countDownLatch.countDown();
            }));
        }
        threads.forEach(thread -> thread.start());
        // main wait all thread
        countDownLatch.await();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchmarkFairAndNonFair.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

}
