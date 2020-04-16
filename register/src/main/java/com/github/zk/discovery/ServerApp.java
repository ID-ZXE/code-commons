package com.github.zk.discovery;

import com.github.zk.Config;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

/**
 * @author hangs.zhang
 * @date 2020/04/05 23:00
 * *****************
 * function:
 */
public class ServerApp {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
        client.start();
        ServiceRegistrar serviceRegistrar = new ServiceRegistrar(client, "hang-commons");
        ServiceInstance<InstanceDetails> instance1 = ServiceInstance.<InstanceDetails>builder()
                .name("SERVICE")
                .port(8090)
                .address("server1")
                .payload(new InstanceDetails("server1", 8090, "Test.Service1"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        ServiceInstance<InstanceDetails> instance2 = ServiceInstance.<InstanceDetails>builder()
                .name("SERVICE")
                .port(8090)
                .address("server2")
                .payload(new InstanceDetails("server2", 8090, "Test.Service1"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        serviceRegistrar.registerService(instance1);
        serviceRegistrar.registerService(instance2);

        System.out.println("注册成功");
        Thread.sleep(Integer.MAX_VALUE);
    }

}
