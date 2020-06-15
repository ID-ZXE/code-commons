package com.github.algorithm.structure;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hangs.zhang
 * @date 2020/03/20 22:55
 * *****************
 * function: 二叉树
 */
public class TreeNode {

    public TreeNode left;

    public TreeNode right;

    public int value;

    public TreeNode(int value) {
        this.value = value;
    }

    public TreeNode(TreeNode left, TreeNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public static TreeNode mockTreeNode() {
        TreeNode head = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        TreeNode node5 = new TreeNode(6);
        TreeNode node6 = new TreeNode(7);

        head.left = node1;
        head.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;

        return head;
    }

    /**
     * 前序遍历
     */
    public static void preOrder(TreeNode head) {
        if (Objects.isNull(head)) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.value);
            if (!Objects.isNull(node.right)) {
                stack.push(node.right);
            }
            // left后进, 先出
            if (!Objects.isNull(node.left)) {
                stack.push(node.left);
            }
        }
        System.out.println();
    }

    /**
     * 前序遍历
     */
    public static void inOrder(TreeNode head) {
        if (Objects.isNull(head)) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = head;
        while (!stack.isEmpty() || cur != null) {
            // Stack, 左子树后进
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode node = stack.pop();
            System.out.print(node.value);
            cur = node.right;
        }
        System.out.println();
    }

    /**
     * 分层打印
     */
    public static void hierarchyPrint(TreeNode head) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.value);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        preOrder(mockTreeNode());
        inOrder(mockTreeNode());
        hierarchyPrint(mockTreeNode());
    }

}
