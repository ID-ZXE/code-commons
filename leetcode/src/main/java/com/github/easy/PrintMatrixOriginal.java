package com.github.easy;

import java.util.Arrays;

/**
 * @author zhanghang
 * @date 2020/12/17 3:48 下午
 * *****************
 * function: 矩阵顺时针打印
 */
public class PrintMatrixOriginal {

    public static int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return new int[0];
        int size = matrix.length * matrix[0].length;
        int[] res = new int[size];
        int index = 0;
        int upside = 0;
        int below = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (true) {
            for (int i = left; i <= right; i++) {
                res[index++] = matrix[upside][i];
            }
            upside++;
            if (upside > below) break;
            for (int i = upside; i <= below; i++) {
                res[index++] = matrix[i][right];
            }
            right--;
            if (right < left) break;
            for (int i = right; i >= left; i--) {
                res[index++] = matrix[below][i];
            }
            below--;
            if (below < upside) break;
            for (int i = below; i >= upside; i--) {
                res[index++] = matrix[i][left];
            }
            left++;
            if (left > right) break;
        }
        return res;
    }

    /**
     * 1.
     * 1 2 3 4
     * 5 6 7 8
     * 1 2 3 4
     * 5 6 7 8
     * <p>
     * 2.
     * 1 2 3 4
     * 5 6 7 8
     * 1 2 3 4
     * <p>
     * 3.
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * 1 2 3
     */
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {1, 2, 3, 4}, {5, 6, 7, 8}};
        int[] res1 = spiralOrder(matrix);
        Arrays.stream(res1).forEach(System.out::print);
        System.out.println();

        int[][] matrix2 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {1, 2, 3, 4}};
        int[] res2 = spiralOrder(matrix2);
        Arrays.stream(res2).forEach(System.out::print);
        System.out.println();

        int[][] matrix3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 2, 3}};
        int[] res3 = spiralOrder(matrix3);
        Arrays.stream(res3).forEach(System.out::print);
    }

}
