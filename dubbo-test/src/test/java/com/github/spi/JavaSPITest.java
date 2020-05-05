package com.github.spi;

import com.github.spi.service.Robot;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * @author hangs.zhang
 * @date 2020/05/05 22:27
 * *****************
 * function:
 */
public class JavaSPITest {

    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}