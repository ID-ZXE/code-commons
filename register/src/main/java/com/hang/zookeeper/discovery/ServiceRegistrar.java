package com.hang.zookeeper.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;

/**
 * @author hangs.zhang
 * @date 2020/04/05 22:58
 * *****************
 * function:
 */
public class ServiceRegistrar {

    private ServiceDiscovery<InstanceDetails> serviceDiscovery;

    public ServiceRegistrar(CuratorFramework client, String basePath) throws Exception {
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .serializer(serializer)
                .basePath(basePath)
                .build();
        serviceDiscovery.start();
    }

    public void registerService(ServiceInstance<InstanceDetails> serviceInstance) throws Exception {
        serviceDiscovery.registerService(serviceInstance);
    }

    public void unregisterService(ServiceInstance<InstanceDetails> serviceInstance) throws Exception {
        serviceDiscovery.unregisterService(serviceInstance);

    }

    public void updateService(ServiceInstance<InstanceDetails> serviceInstance) throws Exception {
        serviceDiscovery.updateService(serviceInstance);

    }

    public void close() throws IOException {
        serviceDiscovery.close();
    }

}
