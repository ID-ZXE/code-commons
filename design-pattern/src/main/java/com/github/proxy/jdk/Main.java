package com.github.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author hangs.zhang
 * @date 2020/04/16 21:31
 * *****************
 * function:
 */
public class Main {

    public static void main(String[] args) {
        MoveAble moveable = new Car();
        InvocationHandler invocationHandler = new TimeHandler(moveable);
        MoveAble proxyInstance = (MoveAble) Proxy.newProxyInstance(moveable.getClass().getClassLoader(), moveable.getClass().getInterfaces(), invocationHandler);
        proxyInstance.move(2000);
    }

}
