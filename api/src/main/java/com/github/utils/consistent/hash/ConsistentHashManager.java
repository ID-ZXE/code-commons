package com.github.utils.consistent.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhanghang
 * @date 2022/3/23 9:59 下午
 * *****************
 * function:
 */
public class ConsistentHashManager {

    private static final int DEFAULT_VIR_NODE_COUNT = 30;
    private final int virNodeCount;
    /**
     * hash环，根据整型存储节点以及虚拟节点
     */
    private final SortedMap<Integer, Node> ring = new TreeMap();
    private final Md5HashUtil hashUtil = new Md5HashUtil();


    public ConsistentHashManager() {
        this.virNodeCount = DEFAULT_VIR_NODE_COUNT;
    }

    public ConsistentHashManager(int virNodeCount) {
        this.virNodeCount = virNodeCount;
    }

    public void addNode(Node node) {
        ring.put(hashUtil.hash(String.valueOf(node.hashCode())), node);
        for (int i = 0; i < this.virNodeCount; i++) {
            this.ring.put(hashUtil.hash(String.valueOf(i + node.hashCode())), node);
        }
    }

    public void removeNote(Node node) {
        ring.entrySet().removeIf(next -> next.getValue().equals(node));
    }

    public Node getNextNode(String key) {
        SortedMap<Integer, Node> longNodeSortedMap = ring.tailMap(hashUtil.hash(key));
        if (longNodeSortedMap.isEmpty()) {
            return ring.get(ring.firstKey());
        }
        return longNodeSortedMap.get(longNodeSortedMap.firstKey());
    }

    public int nodeCount() {
        return this.ring.size();
    }

}
