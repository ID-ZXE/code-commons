package com.github.blind75.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Merge {

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return intervals;
        }
        List<int[]> resList = new ArrayList<>();
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        int left = intervals[0][0];
        int right = intervals[0][1];
        boolean notEnd = true;
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if(right < interval[0]) {
                resList.add(new int[]{left, right});
                left = interval[0];
                right = interval[1];
                notEnd = true;
            } else if(left > interval[1]){
                resList.add(interval);
                notEnd = false;
            } else {
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
                notEnd = true;
            }
        }
        if(notEnd) {
            resList.add(new int[]{left, right});
        }
        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < resList.size(); i++) {
            res[i] = resList.get(i);
        }
        return res;
    }

}
