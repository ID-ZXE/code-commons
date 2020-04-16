package com.github.proxy.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author hangs.zhang
 * @date 2020/04/16 21:29
 * *****************
 * function:
 */
public class TimeHandler implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Object target;

    public TimeHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        LOGGER.info("start move");
        Object obj = method.invoke(target, args);
        LOGGER.info("end move,cost time:{}", (System.currentTimeMillis() - startTime));
        return obj;
    }

}
