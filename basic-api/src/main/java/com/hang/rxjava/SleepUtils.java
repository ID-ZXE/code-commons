package com.hang.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 19-8-5 上午10:41
 * *********************
 * function:
 */
public class SleepUtils {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private SleepUtils() {
    }

    public static void sleep(TimeUnit timeUnit, long time) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            logger.info("sleep exception", e);
        }
    }

}
