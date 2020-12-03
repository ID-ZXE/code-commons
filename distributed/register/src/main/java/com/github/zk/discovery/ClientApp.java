package com.github.zk.discovery;

import com.github.zk.Config;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;

/**
 * @author hangs.zhang
 * @date 2020/04/05 23:02
 * *****************
 * function:
 */
public class ClientApp {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
        client.start();
        ServiceDiscoverer serviceDiscoverer = new ServiceDiscoverer(client, "/hang-commons");
        ServiceInstance<InstanceDetails> instance1 = serviceDiscoverer.getInstanceByName("SERVICE");
        System.out.println(instance1.buildUriSpec());
        System.out.println(instance1.getPayload());

        ServiceInstance<InstanceDetails> instance2 = serviceDiscoverer.getInstanceByName("SERVICE");
        System.out.println(instance2.buildUriSpec());
        System.out.println(instance2.getPayload());

        serviceDiscoverer.getAllInstance();

        serviceDiscoverer.close();
        CloseableUtils.closeQuietly(client);


    }

}
