package com.github.greedy.medium;

import java.util.Arrays;

/**
 * @author zhanghang
 * @date 2020/12/13 1:24 下午
 * *****************
 * function:
 */
public class FindMinArrowShots {

    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length <= 0) {
            return 0;
        }

        int result = 1;
        /*
            根据 结束位置，从小到大 排序原数组
         */
        Arrays.sort(points, (point1, point2) -> (point1[1] > point2[1] ? 1 : -1));

        /*
            贪心，遍历数组，计算结果：
                由于原数组根据 结束位置，从小到大 排序
                因此，我们只用判断 当前气球的开始位置 和 当前区间的结束位置 大小关系：
                    若 当前气球的开始位置 < 当前区间的结束位置，则表示当前气球也在当前区间内
                    若 不满足上述条件，则当前气球不在当前区间内，箭数+1，更新当前区间的结束位置
         */
        int curEnd = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > curEnd) {
                result++;
                curEnd = points[i][1];
            }
        }

        return result;
    }

}
