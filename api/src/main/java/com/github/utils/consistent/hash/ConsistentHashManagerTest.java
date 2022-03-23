package com.github.utils.consistent.hash;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghang
 * @date 2022/3/23 10:20 下午
 * *****************
 * function:
 */
@Slf4j
public class ConsistentHashManagerTest {

    @Test
    public void addNodeTest() {
        int vCount = 10;
        ConsistentHashManager consistentHashManager = new ConsistentHashManager(vCount);
        consistentHashManager.addNode(new Node("node1", "192.0.0.1", 8080));
        consistentHashManager.addNode(new Node("node2", "192.0.0.2", 8080));
        consistentHashManager.addNode(new Node("node3", "192.0.0.3", 8080));
        consistentHashManager.addNode(new Node("node4", "192.0.0.4", 8080));
        assert consistentHashManager.nodeCount() == 44;
    }

    @Test
    public void loadBalancingTest() {
        ConsistentHashManager consistentHashManager = new ConsistentHashManager();
        consistentHashManager.addNode(new Node("node1", "192.0.0.1", 8080));
        consistentHashManager.addNode(new Node("node2", "192.0.0.2", 8080));
        consistentHashManager.addNode(new Node("node3", "192.0.0.3", 8080));
        consistentHashManager.addNode(new Node("node4", "192.0.0.4", 8080));

        String preKey = "Data_";
        Map<String, Integer> map = new HashMap<>(200000);
        map.put("node1", 0);
        map.put("node2", 0);
        map.put("node3", 0);
        map.put("node4", 0);
        for (int i = 0; i < 200000; i++) {
            Node nextNote = consistentHashManager.getNextNode(preKey + i);
            map.computeIfPresent(nextNote.getName(), (k, v) -> v + 1);
        }
        map.entrySet().forEach(System.out::println);
    }

    @Test
    public void removeNodeTest() {
        ConsistentHashManager consistentHashManager = new ConsistentHashManager(100);
        Node node4 = new Node("node4", "192.0.0.4", 8080);

        consistentHashManager.addNode(new Node("node1", "192.0.0.1", 8080));
        consistentHashManager.addNode(new Node("node2", "192.0.0.2", 8080));
        consistentHashManager.addNode(new Node("node3", "192.0.0.3", 8080));
        consistentHashManager.addNode(node4);

        Map<String, Integer> statistic = new HashMap<>();
        statistic.put("node1", 0);
        statistic.put("node2", 0);
        statistic.put("node3", 0);
        statistic.put("node4", 0);

        String preKey = "Data_";
        for (int i = 0; i < 200000; i++) {
            Node nextNode = consistentHashManager.getNextNode(preKey + i);
            statistic.computeIfPresent(nextNode.getName(), (k, v) -> v + 1);
        }

        // 移除一个节点 模拟节点故障
        consistentHashManager.removeNote(node4);
        // 测试下移除节点后的命中率
        int n1 = 0;
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < 200000; i++) {
            Node nextNode = consistentHashManager.getNextNode(preKey + i);
            switch (nextNode.getName()) {
                case "node1":
                    n1++;
                    break;
                case "node2":
                    n2++;
                    break;
                case "node3":
                    n3++;
                    break;
                default:
                    throw new RuntimeException("node4 未清理干净");
            }
        }

        // 打印命中率, 原本的次数/删除后的次数
        for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            switch (key) {
                case "node1":
                    log.info("Node1,总访问次数={},有效访问{} 命中率 = {}", n1, value, (double) (value) / n1);
                    break;
                case "node2":
                    log.info("Node2,总访问次数={},有效访问{} 命中率 = {}", n2, value, (double) (value) / n2);
                    break;
                case "node3":
                    log.info("Node3,总访问次数={},有效访问{} 命中率 = {}", n3, value, (double) (value) / n3);
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    public void getNextNodeTest() {
    }
}
