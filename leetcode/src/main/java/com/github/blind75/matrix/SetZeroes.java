package com.github.blind75.matrix;

public class SetZeroes {

    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0) return;

        boolean[] x = new boolean[matrix.length];
        boolean[] y = new boolean[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    x[i] = true;
                    y[j] = true;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (x[i] || y[j]) {
                    matrix[i][j] = 0;
                }
            }
        }

    }

}
