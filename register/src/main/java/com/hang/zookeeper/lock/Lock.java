package com.hang.zookeeper.lock;

/**
 * @author hangs.zhang
 * @date 2019/11/10 14:01
 * *****************
 * function:
 */
public class Lock {

    private String lockRootPath = "/lock/";

    public Lock() {
        // init zk client
    }

    public LockInstance lock(String lockId, long timeout) {
        return null;
    }

    public LockInstance tryActiveLock(LockInstance lockNode) {
        return null;
    }

    public void unlock(LockInstance lockNode) {

    }

    public LockInstance createLockNode(String lockId) {
        return null;
    }

}
