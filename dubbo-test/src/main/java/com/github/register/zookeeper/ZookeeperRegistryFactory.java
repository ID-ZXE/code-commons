package com.github.register.zookeeper;

import com.github.common.URL;
import com.github.register.api.Registry;
import com.github.register.api.support.AbstractRegistryFactory;
import com.github.remote.zookeeper.ZookeeperTransporter;

/**
 * ZookeeperRegistryFactory.
 */
public class ZookeeperRegistryFactory extends AbstractRegistryFactory {

    private ZookeeperTransporter zookeeperTransporter;

    public void setZookeeperTransporter(ZookeeperTransporter zookeeperTransporter) {
        this.zookeeperTransporter = zookeeperTransporter;
    }

    @Override
    public Registry createRegistry(URL url) {
        return new ZookeeperRegistry(url, zookeeperTransporter);
    }

}