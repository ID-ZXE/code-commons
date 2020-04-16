package com.github.proxy.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/04/16 21:27
 * *****************
 * function:
 */
public class Car implements MoveAble {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void move(long time) {
        try {
            LOGGER.info("car moving");
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
