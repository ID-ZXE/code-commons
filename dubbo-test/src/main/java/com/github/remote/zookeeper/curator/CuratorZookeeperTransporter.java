package com.github.remote.zookeeper.curator;

import com.github.common.URL;
import com.github.remote.zookeeper.ZookeeperClient;
import com.github.remote.zookeeper.ZookeeperTransporter;

public class CuratorZookeeperTransporter implements ZookeeperTransporter {

    @Override
    public ZookeeperClient connect(URL url) {
        return new CuratorZookeeperClient(url);
    }

}
