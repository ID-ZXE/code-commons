package com.github.spi.service;

import com.github.common.extension.Adaptive;
import com.github.common.extension.SPI;

/**
 * @author hangs.zhang
 * @date 2020/05/05 22:25
 * *****************
 * function:
 */
@SPI("robot")
public interface Robot {

    @Adaptive("sayHello")
    void sayHello();

}
