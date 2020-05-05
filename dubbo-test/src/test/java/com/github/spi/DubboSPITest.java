package com.github.spi;

import com.github.common.extension.ExtensionLoader;
import com.github.spi.service.Robot;
import org.junit.Test;

/**
 * @author hangs.zhang
 * @date 2020/05/05 22:31
 * *****************
 * function:
 */
public class DubboSPITest {

    @Test
    public void sayHello() throws Exception {
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }

}
