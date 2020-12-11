package com.github.queue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hangs.zhang
 * @date 2020/05/01 21:34
 * *****************
 * function: 有界阻塞队列
 */
public class ArrayBlockingQueue<E> extends LinkedList<E> {

    private final ReentrantLock lock = new ReentrantLock();

    private final transient Condition notFull;

    private final transient Condition notEmpty;

    private final int capacity;

    public ArrayBlockingQueue(int capacity) {
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
        this.capacity = capacity;
    }

    public void put(E e) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (size() == capacity) {
                notFull.await();
            }
            notEmpty.signal();
            offer(e);
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (size() == 0) {
                notEmpty.await();
            }
            notFull.signal();
            return poll();
        } finally {
            lock.unlock();
        }
    }

}
