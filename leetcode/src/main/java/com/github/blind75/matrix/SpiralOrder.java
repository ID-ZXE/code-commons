package com.github.blind75.matrix;

import java.util.ArrayList;
import java.util.List;

public class SpiralOrder {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return res;
        int nums = matrix.length * matrix[0].length;

        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int button = matrix.length - 1;

        while (nums >= 1) {
            for (int i = left; i <= right && nums >= 1; i++) {
                res.add(matrix[top][i]);
                nums--;
            }
            top++;
            for (int i = top; i <= button && nums >= 1; i++) {
                res.add(matrix[i][right]);
                nums--;
            }
            right--;

            for (int i = right; i >= left && nums >= 1; i--) {
                res.add(matrix[button][i]);
                nums--;
            }
            button--;

            for (int i = button; i >= top && nums >= 1; i--) {
                res.add(matrix[i][left]);
                nums--;
            }
            left++;
        }
        return res;
    }

}
