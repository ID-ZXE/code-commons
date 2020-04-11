package com.hang.zookeeper.discovery;

import com.hang.zookeeper.Config;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author hangs.zhang
 * @date 2020/04/06 11:13
 * *****************
 * function:
 */
public class Listen {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(Config.ZK_ADDRESS)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        treeCache(client);
        Thread.sleep(Integer.MAX_VALUE);
    }

    // 这里会监听初始值,需要做处理
    public static void treeCache(CuratorFramework client) throws Exception {
        TreeCache treeCache = new TreeCache(client, "/job-schedule/SOLDIER_INSTANCE_SERVICE");
        treeCache.start();
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) {
                switch (event.getType()) {
                    case NODE_ADDED:
                        ChildData data = event.getData();
                        System.out.println("tree:发生节点添加,data:" + new String(data.getData()));
                        break;
                    case NODE_UPDATED:
                        System.out.println("tree:发生节点更新");
                        break;
                    case NODE_REMOVED:
                        ChildData data2 = event.getData();
                        System.out.println("tree:发生节点删除,data:" + new String(data2.getData()));
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
