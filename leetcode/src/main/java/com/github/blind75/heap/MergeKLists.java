package com.github.blind75.heap;

import java.util.*;

public class MergeKLists {

    public ListNode mergeKLists(ListNode[] lists) {
        // 队列的头是按指定排序方式的最小元素
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });

        String s = "1";
        Queue<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();


        for (ListNode listNode : lists) {
            if (listNode != null) {
                priorityQueue.offer(listNode);
            }
        }

        ListNode head = priorityQueue.poll();
        ListNode cur = head;
        while (!priorityQueue.isEmpty()) {
            ListNode listNode = priorityQueue.poll();
            cur.next = listNode;
            cur = listNode;
            if (listNode.next != null) {
                priorityQueue.offer(listNode.next);
            }
        }
        return head;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
