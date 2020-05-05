package com.github.spi.service.impl;

import com.github.spi.service.Robot;

/**
 * @author hangs.zhang
 * @date 2020/05/05 22:26
 * *****************
 * function:
 */
public class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
