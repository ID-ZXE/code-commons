package com.github.offer;

/**
 * @author hangs.zhang
 * @date 2020/06/19 09:58
 * *****************
 * function:
 * 旋转数组的最小数字
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
 */
public class FindMin {

    /**
     * right为有序数组的中间值
     *
     * @param numbers
     * @return
     */
    public static int minArray(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;

            // mid > right, 说明最小元素在mid和right之间
            if (numbers[mid] > numbers[right]) {
                left = mid + 1;
                // mid < right, 说明最小元素在left和mid之间
            } else if (numbers[mid] < numbers[right]) {
                right = mid;
                // 无法确定最小元素在左边还是右边
                // 因为元素一定在right的左边, 所以right--
            } else {
                right--;
            }
        }
        return numbers[left];
    }

    public static void main(String[] args) {
        int[] arr = {3, 4, 5, 1, 2};
        System.out.println(minArray(arr));
    }

}
