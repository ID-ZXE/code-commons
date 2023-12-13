package com.github.blind75.Interval;

import java.util.Arrays;
import java.util.Comparator;

public class EraseOverlapIntervals {

    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int res = Integer.MAX_VALUE;
        int[] dp = new int[intervals.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < intervals.length; i++) {
            for (int j = 0; j < i; j++) {
                if(intervals[i][0] >= intervals[j][1]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            res = Math.min(res, intervals.length - dp[i]);
        }
        return res;
    }

}
