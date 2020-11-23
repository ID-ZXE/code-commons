package com.github.algorithm.offer;

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
