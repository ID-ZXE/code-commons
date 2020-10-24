package com.github.lang.system;

import org.junit.Test;

/**
 * @author hangs.zhang
 * @date 2020/3/30 上午11:45
 * *********************
 * function:
 */
public class SystemTest {

    @Test
    public void systemProperties() {
        // 默认是/tmp 可以通过-D注入 注入的优先级最高
        String tmpDir = System.getProperty("java.io.tmpdir");
        System.out.println(tmpDir);

        String catalinaBaseDir = System.getProperty("catalina.base");
        System.out.println(catalinaBaseDir);
    }

}
