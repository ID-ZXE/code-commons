package com.github.blind75;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        ListNode mid = findMid(head);
        ListNode leftNode = head;
        ListNode rightNode = reverse(mid);

        ListNode temp = head;
        while (temp.next != mid) {
            temp = temp.next;
        }
        temp.next = null;

        while (rightNode != null) {
            ListNode leftNext = leftNode.next;
            ListNode rightNext = rightNode.next;

            leftNode.next = rightNode;
            rightNode.next = leftNext;

            leftNode = leftNext;
            rightNode = rightNext;
        }
    }

    public static ListNode findMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast != null) {
            slow = slow.next;
        }
        return slow;
    }

    public static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode node = head;
        while (node != null) {
            ListNode next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    public static void main(String[] args) {
//        ListNode listNode1 = new ListNode(1);
//        ListNode listNode2 = new ListNode(2);
//        ListNode listNode3 = new ListNode(3);
//        ListNode listNode4 = new ListNode(4);
//        listNode1.next = listNode2;
//        listNode2.next = listNode3;
//        listNode3.next = listNode4;
//        reorderList(listNode1);
//        print(listNode1);

        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        reorderList(listNode1);
        print(listNode1);
    }

    public static void print(ListNode head) {
        ListNode node = head;
        while(node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    public static class ListNode {
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
