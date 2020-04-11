package com.hang.guava.exception;

import org.junit.Test;

/**
 * @author hangs.zhang
 * @date 19-7-28 下午4:45
 * *********************
 * function:
 */
public class ThrowablesTest {

    @Test
    public void test1() {
        // 得到异常链路
        // Throwables.getCausalChain()

        // 得到最原始的异常
        // Throwables.getRootCause()

        // 将Exception包装为RuntimeException
        // Throwables.propagateIfPossible();

        // 拿到异常堆栈信息
        //Throwables.getStackTraceAsString()
    }

}
