package com.github.blind75.graph;

public class NumIslands {

    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                res += dfs(grid, i, j);
            }
        }
        return res;
    }

    private int dfs(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i > grid.length - 1 || j > grid[0].length - 1) return 0;
        if (grid[i][j] == '0') return 0;
        grid[i][j] = '0';

        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);

        return 1;
    }

    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}};
        System.out.println(new NumIslands().numIslands(grid));
    }

}
