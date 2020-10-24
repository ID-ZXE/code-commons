package com.github.thread;

import java.util.concurrent.Semaphore;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午8:47
 * *********************
 * function:
 */
public class AtomicTest {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        semaphore.acquire();
        semaphore.release();
    }

}
