package com.github.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/**
 * @author hangs.zhang
 * @date 2020/05/02 22:06
 * *****************
 * function: 自定义线程工厂
 */
public class CustomThreadFactory implements ThreadFactory {

    private final ThreadFactory threadFactory;

    public CustomThreadFactory(String prefix, boolean daemon) {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        this.threadFactory = threadFactoryBuilder
                .setDaemon(daemon)
                .setNameFormat(StringUtils.isNotEmpty(prefix) ? prefix + "-thread-%d" : "")
                .setPriority(10)
                .build();

    }

    @Override
    public Thread newThread(Runnable task) {
        if (Objects.isNull(task)) {
            throw new RuntimeException();
        }
        return threadFactory.newThread(task);
    }

}
