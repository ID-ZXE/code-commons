package com.hang.zookeeper.lock;

import lombok.Data;

/**
 * @author hangs.zhang
 * @date 2019/11/10 13:58
 * *****************
 * function:
 */
@Data
public class LockInstance {

    private String lockId;

    private String path;

    private boolean active;

    public LockInstance() {
    }

    public LockInstance(String lockId, String path) {
        this.lockId = lockId;
        this.path = path;
    }
}
