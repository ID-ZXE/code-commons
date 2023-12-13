package com.github.blind75.Interval;

import java.util.ArrayList;
import java.util.List;

public class Insert {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> resList = new ArrayList<>();

        int left = newInterval[0];
        int right = newInterval[1];
        boolean placed = false;
        for (int[] interval : intervals) {
            if (right < interval[0]) {
                if(!placed) {
                    resList.add(new int[]{left, right});
                    placed = true;
                }
                resList.add(interval);
            } else if (left > interval[1]) {
                resList.add(interval);
            } else {
                left = Math.min(interval[0], left);
                right = Math.max(interval[1], right);
            }
        }

        if (!placed) {
            resList.add(new int[]{left, right});
        }
        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < resList.size(); i++) {
            res[i] = resList.get(i);
        }
        return res;
    }

}
