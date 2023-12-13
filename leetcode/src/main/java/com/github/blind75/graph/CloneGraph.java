package com.github.blind75.graph;

import java.util.*;

public class CloneGraph {

    public Node cloneGraph(Node node) {
        HashMap<Integer, Node> map = new HashMap<>();
        dfs(node, map);
        return map.get(1);
    }

    private void dfs(Node node, Map<Integer, Node> map) {
        if (node == null) return;
        Node newNode;
        if (!map.containsKey(node.val)) {
            newNode = new Node(node.val);
            newNode.neighbors = new ArrayList<>();
            map.put(node.val, newNode);
        } else {
            newNode = map.get(node.val);
        }
        for (Node neighbor : node.neighbors) {
            Node temp = map.get(neighbor.val);
            if (Objects.isNull(temp)) {
                temp = new Node(neighbor.val);
                temp.neighbors = new ArrayList<>();
                map.put(neighbor.val, temp);
                newNode.neighbors.add(temp);
                dfs(neighbor, map);
            } else {
                newNode.neighbors.add(temp);
            }
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node1.neighbors = Arrays.asList(node2, node4);
        node2.neighbors = Arrays.asList(node1, node3);
        node3.neighbors = Arrays.asList(node2, node4);
        node4.neighbors = Arrays.asList(node1, node3);

        Node node = new CloneGraph().cloneGraph(node1);
        System.out.println(node);
    }

}

class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}