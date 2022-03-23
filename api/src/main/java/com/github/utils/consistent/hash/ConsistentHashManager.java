package com.github.utils.consistent.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhanghang
 * @date 2022/3/23 9:59 下午
 * *****************
 * function:
 * 一致性hash介绍
 * https://www.zsythink.net/archives/1182
 */
public class ConsistentHashManager {

    private static final int DEFAULT_VIR_NODE_COUNT = 30;

    private final int virNodeCount;

    /**
     * hash环，根据整型存储节点以及虚拟节点
     * integer正好是2的32次方
     */
    private final SortedMap<Integer, Node> ring = new TreeMap<>();

    private final Md5HashUtil hashUtil = new Md5HashUtil();

    public ConsistentHashManager() {
        this.virNodeCount = DEFAULT_VIR_NODE_COUNT;
    }

    public ConsistentHashManager(int virNodeCount) {
        this.virNodeCount = virNodeCount;
    }

    /**
     * 要继续增加更多物理节点时，如何继续保持平均分布？这很难实现，从另一个方面再想，虚拟节点的作用是什么？
     * 是为了让分配更平均，防止血崩的概率，其实哪怕这些虚拟节点的位置都是随机的，只要节点数量够多，就已经达到了目的。
     *
     * @param node
     */
    public void addNode(Node node) {
        ring.put(hashUtil.hash(String.valueOf(node.hashCode())), node);
        for (int i = 1; i <= this.virNodeCount; i++) {
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
