package com.github.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午8:51
 * *********************
 * function:
 */
@SuppressWarnings("all")
public class ThreadControlToolsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    ExecutorService executors = Executors.newCachedThreadPool();

    /**
     * CountDownLatch不能被重用, 如果需要重新计数, 可以考虑CyclicBarrier
     * all wait for onw, 如多个运动员等待裁判员鸣枪, 比赛开始
     * one wait for all, 如等待所有运动员到达终点, 比赛结束
     */
    @Test
    public void testCountDownLatch() throws InterruptedException {
        int size = 10;
        final CountDownLatch end = new CountDownLatch(size);
        final CountDownLatch begin = new CountDownLatch(1);
        for (int i = 0; i < size; i++) {
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("{} 准备起跑", Thread.currentThread().getName());
                    try {
                        begin.await();
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                    LOGGER.info("{} 起跑", Thread.currentThread().getName());

                    try {
                        // 随机的跑步时间
                        long time = Math.abs(new Random().nextInt(20));
                        Thread.sleep(time);
                        LOGGER.info("{} 到达终点", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                    end.countDown();
                }
            });
        }
        Thread.sleep(100L);
        LOGGER.info("裁判员鸣枪, 比赛开始");
        begin.countDown();
        end.await();
        LOGGER.info("所有运动员到达终点, 比赛结束");
    }

    /**
     * 等待固定线程到达了珊栏位置, 所有线程才能继续向下执行
     * 类似于在等待所有人都到齐后, 大家才开始讨论工作
     * <p>
     * CyclicBarrier的对象是一个一个的线程
     * CountDownLatch则是一个又一个的事件
     */
    @Test
    public void testCyclicBarrier() throws InterruptedException {
        int num = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);
        LOGGER.info("等待第一次讨论");
        for (int i = 0; i < num; i++) {
            Thread.sleep(100L);
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                    LOGGER.info("{} 到达", Thread.currentThread().getName());
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        // do nothing
                    } catch (BrokenBarrierException e) {
                        // do nothing
                    }
                    // 三个线程统一在此处, 一起讨论工作
                    LOGGER.info("{} 一起讨论工作", Thread.currentThread().getName());
                }
            });
        }
        Thread.sleep(1000L);
        // 在使用完一次之后, 自动重用
        LOGGER.info("等待第二次讨论");
        for (int i = 0; i < num; i++) {
            Thread.sleep(100L);
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                    LOGGER.info("{} 到达", Thread.currentThread().getName());
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        // do nothing
                    } catch (BrokenBarrierException e) {
                        // do nothing
                    }
                    // 三个线程统一在此处, 一起讨论工作
                    LOGGER.info("{} 一起讨论工作", Thread.currentThread().getName());
                }
            });
        }
        Thread.sleep(1000L);
        // 除了自动重用之外, 还可以手动的reset
        // cyclicBarrier.reset();
        LOGGER.info("{} end", Thread.currentThread().getName());
    }

    @Test
    public void testSemaphore() throws InterruptedException {
        // true参数表示是否公平, 默认是非公平
        final Semaphore semaphore = new Semaphore(3, true);
        for (int i = 0; i < 10; i++) {
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // acquire必须响应中断, 还有一种不需要响应中断的api
                        // semaphore.acquireUninterruptibly();
                        // acquire还支持传入int参数, 线程或者说任务可以根据权重拿到多个许可证
                        semaphore.acquire();
                        // 还可以try
                        // semaphore.tryAcquire();
                        LOGGER.info("{} 获取到许可证", Thread.currentThread().getName());
                        Thread.sleep(300L);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                    semaphore.release();
                }
            });
        }
        Thread.sleep(2000L);
        LOGGER.info("end");
    }

    /**
     * Condition的signal方法必须在持有锁的情况下才能执行
     */
    @Test
    public void testCondition() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        for (int i = 0; i < 10; i++) {
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        LOGGER.info("{} 开始等待, 释放锁", Thread.currentThread().getName());
                        // await的时候释放锁, 醒来的时候则去争抢锁
                        condition.await();
                        LOGGER.info("{} 醒来, 获取到锁, 执行任务", Thread.currentThread().getName());
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        // do nothing
                    } finally {
                        LOGGER.info("{} 执行任务完毕, 释放锁", Thread.currentThread().getName());
                        lock.unlock();
                    }
                }
            });
        }
        Thread.sleep(200L);

        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        Thread.sleep(2000L);
    }

    @Test
    public void threadLocalTest() {
        ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "initial");
        LOGGER.info("value1:{}", threadLocal.get());
        threadLocal.set("value");
        LOGGER.info("value2:{}", threadLocal.get());
        threadLocal.remove();
        LOGGER.info("value3:{}", threadLocal.get());
    }

    /**
     * 响应sleep()方法导致中断标志被清除, 但是LockSupport.park()方法还是不会阻塞
     */
    @Test
    public void testLockSupport() throws InterruptedException {
        final String mark = "interrupt state:{}";
        Thread thread = new Thread(() -> {
            LOGGER.info("i`m start");
            LOGGER.info(mark, Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // sleep()方法响应中断会清除标志位, 但是不会更改_counter
                LOGGER.info(mark, Thread.currentThread().isInterrupted());
            }
            LOGGER.info("i`m work");
            LockSupport.park();
            LOGGER.info(mark, Thread.currentThread().isInterrupted());
            LOGGER.info("i`m end");
        }, "lockSupport");
        thread.start();
        thread.interrupt();
        Thread.sleep(1000);
        LOGGER.info("thread end");
    }

    /**
     * 在park之前执行unpark()方法, 线程不会阻塞
     */
    @Test
    public void testLockSupport2() {
        LOGGER.info("start");
        LockSupport.unpark(Thread.currentThread());
        /*不会阻塞*/
        LockSupport.park();
        LOGGER.info("end");
    }

    /**
     * 线程中断标志位true, LockSupport.park()不会阻塞
     */
    @Test
    public void testLockSupport3() throws InterruptedException {
        LOGGER.info("start");
        Thread thread = new Thread(() -> {
            LOGGER.info("thread start");
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 3L) {
            }
            LockSupport.park();
            LOGGER.info("thread end");
        });
        thread.start();
        // 中断线程
        thread.interrupt();
        Thread.sleep(5L);
        LOGGER.info("end");
    }

}
