package com.github.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

/**
 * @author hangs.zhang
 * @date 2020/05/02 09:26
 * *****************
 * function:
 */
public class ProcessMan {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        try {
            // 此处使用 >> file 2>&1
            // String cmd = "/Users/zhanghang/file/script/doecho.sh >> /Users/zhanghang/file/script/count.log 2>&1";
            // 此处没有使用 >> file 2>&1 导致输出进入父进程,父进程如何不去读的话,waitFor方法会一直死锁
            String cmd = "/Users/zhanghang/file/script/doecho.sh";
            String[] params = {"sh", "-c", cmd};
            Process process = Runtime.getRuntime().exec(params);
            // 将错误输出与标准输出导流到父进程
            // Process process = new ProcessBuilder(params).inheritIO().start();
            Field pidField = process.getClass().getDeclaredField("pid");
            pidField.setAccessible(true);
            int pid = pidField.getInt(process);
            LOGGER.info("子进程pid:{}", pid);

            LOGGER.info("start run cmd={}", cmd);
            process.waitFor();
            LOGGER.info("finish run cmd={}", cmd);
        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

}
