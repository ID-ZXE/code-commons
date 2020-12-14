package com.github.offer;

/**
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 * 每行的元素从左到右升序排列, 每列的元素从上到下升序排列
 * 现有矩阵 matrix 如下
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 */
public class FindTargetInMatrix {
	
	public static void main(String[] args) {
		int[][] arr = {
				{1, 4, 7, 11, 15},
				{2, 5, 8, 12, 19},
				{3, 6, 9, 16, 22},
				{10, 13, 14, 17, 24},
				{18, 21, 23, 26, 30}
		};
		
		System.out.println(existTarget(arr, 2));
		System.out.println(existTarget(arr, 20));
		System.out.println(existTarget(arr, 9));
		System.out.println(existTarget(arr, 18));
	}
	
	public static boolean existTarget(int[][] arr, int target) {
		if (arr == null || arr[0] == null) return false;
		int length = arr[0].length;
		
		for (int i = 0, j = length - 1; (i < length) || (j > 0); ) {
			if (arr[i][j] == target) {
				return true;
			} else if (arr[i][j] > target) {
				j--;
			} else {
				i++;
			}
		}
		return false;
	}
	
}
