package com.hang.thread;

/**
 * @author hangs.zhang
 * @date 19-7-24 下午6:54
 * *********************
 * function:
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        /// testInterrupt();

        /// testInterrupt2();

        /// testInterrupt3();

        testInterrupt4();
    }

    public static void testInterrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                // 这里的state会输出为false,thread响应之后会将标志位清零
                System.out.println("i was interrupted, current state " + Thread.currentThread().isInterrupted());
            }
        }, "interrupted-thread");
        thread.start();
        Thread.sleep(1000L);
        // 中断
        thread.interrupt();
    }

    /**
     * 线程不会收到任何干扰
     */
    public static void testInterrupt2() {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 10000L) {
                 System.out.println("I`m busy");
            }
            // state true 状态发生改变
            System.out.println("My State is " + Thread.currentThread().isInterrupted());
        }, "interrupted-thread");
        thread.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 中断
        thread.interrupt();
    }

    public static void testInterrupt3() {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            boolean interrupt = false;
            while ((System.currentTimeMillis() - start) < 10000L) {
                // Thread.interrupted()获取标识位,并且将标识位修改
                if(Thread.interrupted()) {
                    System.out.println("somebody interrupt me, ignore it...");
                    interrupt = true;
                } else if(interrupt) {
                    System.out.println("somebody interrupt me once, but Thread.interrupt test false");
                    break;
                } else {
                    System.out.println("I`m busy...");
                }
            }
            System.out.println("I`m done with my job");
        }, "interrupted-thread");
        thread.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 中断
        thread.interrupt();
    }

    public static void testInterrupt4() {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 10000L) {
                // 获取标识位,并且不修改标识位
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("somebody interrupt me, ignore it...");
                } else {
                    System.out.println("I`m busy...");
                }
            }
            System.out.println("I`m done with my job");
        }, "interrupted-thread");
        thread.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 中断
        thread.interrupt();
    }

}
