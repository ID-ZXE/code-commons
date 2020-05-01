package com.github.thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hangs.zhang
 * @date 2020/04/30 23:20
 * *****************
 * function:
 */
public class LockTest {

    @Test
    public void testNewCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        System.out.println(condition1 == condition2);
    }

}
