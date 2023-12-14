package com.github.retransform;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class RetransformEntry {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private int id;

    private String className;

    private byte[] bytes;

    /**
     * 被 transform 触发次数
     */
    private int transformCount = 0;

    public RetransformEntry(String className, byte[] bytes) {
        id = counter.incrementAndGet();
        this.className = className;
        this.bytes = bytes;
    }

    public void incTransformCount() {
        transformCount++;
    }

}
