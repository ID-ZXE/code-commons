package com.github.leetcode.medium.tree;

import com.github.structure.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/07/12 17:20
 * *****************
 * function:
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 * <p>
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 */
public class ZigzagLevelOrder {

    /*
        3
       / \
      9  20
        /  \
       15   7
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> nodeDeque = new ArrayDeque<>();
        nodeDeque.addFirst(root);
        int L = 1;

        //每次循环遍历二叉树的一层，并将下一层的节点储存进列
        while (!nodeDeque.isEmpty()) {
            //获取目前要遍历的这一层的节点数
            int size = nodeDeque.size();
            result.add(new ArrayList<>());
            //奇数层的节点在头部进出列
            for (int i = 0; L % 2 > 0 && i < size; i++) {
                TreeNode node = nodeDeque.pollFirst();
                result.get(result.size() - 1).add(node.value);
                //将下一层的非空左节点和右节点在尾部入列
                if (node.left != null) {
                    nodeDeque.addLast(node.left);
                }
                if (node.right != null) {
                    nodeDeque.addLast(node.right);
                }
            }
            //偶数层的节点在尾部进出列
            for (int i = 0; L % 2 == 0 && i < size; i++) {
                TreeNode node = nodeDeque.pollLast();
                result.get(result.size() - 1).add(node.value);
                //将下一层的右节点和左节点在头部入列
                if (node.right != null) {
                    nodeDeque.addFirst(node.right);
                }
                if (node.left != null) {
                    nodeDeque.addFirst(node.left);
                }
            }
            L++;
        }
        return result;
    }

}
