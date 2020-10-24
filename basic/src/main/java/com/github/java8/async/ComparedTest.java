package com.github.java8.async;

import org.junit.Test;

/**
 * @author hangs.zhang
 * @date 2019/3/22.
 * *****************
 * function:
 */
public class ComparedTest {

    @Test
    public void findPrices(){
        BestFinder bestFinder = new BestFinder();
        long st = System.currentTimeMillis();
        System.out.println(bestFinder.findPrices("iPhoneX"));
        System.out.println("done : " + (System.currentTimeMillis() - st) + "msecs");
        // done : 10365msecs
        // done : 10332msecs
    }

    // 并行流可以调用的系统核数相关，我的计算机是2核，最多2个线程同时运行
    // 而商店有10个，也就是说，线程会一直等待前面的某一个线程释放出空闲才能继续运行。
    @Test
    public void findPricesParallel(){
        BestFinder bestFinder = new BestFinder();
        long st = System.currentTimeMillis();
        System.out.println(bestFinder.findPricesParallel("iPhoneX"));
        System.out.println("done : " + (System.currentTimeMillis() - st) + "msecs");
        // done : 3374msecs
        // done : 3382msecs
        // done : 3274msecs
        // done : 3279msecs
    }

    @Test
    public void findPricesAsync(){
        BestFinder bestFinder = new BestFinder();
        long st = System.currentTimeMillis();
        System.out.println(bestFinder.findPricesAsync("iPhoneX"));
        System.out.println("done : " + (System.currentTimeMillis() - st) + "msecs");
        // done : 1528msecs
        // done : 1297msecs
        // done : 1330msecs
        // done : 1214msecs
        // done : 1359msecs
    }

}
