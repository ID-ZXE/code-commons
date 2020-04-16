package com.github.zk.discovery;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.*;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

import java.io.Closeable;
import java.util.Collection;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/04/05 23:01
 * *****************
 * function:
 */
public class ServiceDiscoverer {

    private ServiceDiscovery<InstanceDetails> serviceDiscovery;

    private List<Closeable> closeableList = Lists.newArrayList();

    public ServiceDiscoverer(CuratorFramework client, String basePath) throws Exception {
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .basePath(basePath)
                .serializer(serializer)
                .build();

        serviceDiscovery.start();
    }

    public ServiceInstance<InstanceDetails> getInstanceByName(String serviceName) throws Exception {
        ServiceProvider<InstanceDetails> provider = serviceDiscovery.serviceProviderBuilder().
                serviceName(serviceName).
                providerStrategy(new RandomStrategy<>())
                .build();
        provider.start();
        closeableList.add(provider);
        return provider.getInstance();
    }


    public void getAllInstance() throws Exception {
        serviceDiscovery.serviceProviderBuilder().providerStrategy(new RandomStrategy<>()).build();
        Collection<String> names = serviceDiscovery.queryForNames();

    }

    public synchronized void close() {
        for (Closeable closeable : closeableList) {
            CloseableUtils.closeQuietly(closeable);
        }
    }

}
