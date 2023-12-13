package com.github.blind75.matrix;

public class GenerateMatrix {

    public int[][] generateMatrix(int n) {
        if (n == 0) return null;
        int[][] res = new int[n][n];

        int nums = n * n;
        int left = 0;
        int right = n - 1;
        int top = 0;
        int button = n - 1;
        int val = 0;

        while (nums >= 1) {
            for (int i = left; i <= right && nums >= 1; i++) {
                res[top][i] = ++val;
                nums--;
            }
            top++;
            for (int i = top; i <= button && nums >= 1; i++) {
                res[i][right] = ++val;
                nums--;
            }
            right--;

            for (int i = right; i >= left && nums >= 1; i--) {
                res[button][i] = ++val;
                nums--;
            }
            button--;

            for (int i = button; i >= top && nums >= 1; i--) {
                res[i][left] = ++val;
                nums--;
            }
            left++;
        }
        return res;
    }

}
