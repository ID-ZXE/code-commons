package com.github.blind75.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CanFinish {

    /**
     * 示例 1：
     * <p>
     * 输入：numCourses = 2, prerequisites = [[1,0]]
     * 输出：true
     * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> reverseMap = new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            map.put(prerequisites[i][0], prerequisites[i][1]);
            reverseMap.put(prerequisites[i][1], prerequisites[i][0]);
        }

        int num = prerequisites.length * prerequisites.length;
        int c = prerequisites[0][0];
        while (!map.isEmpty() && num >= 0) {
            Integer depC = map.get(c);
            if (depC == null) {
                Integer remove = reverseMap.get(c);
                map.remove(remove);

                Object[] array = map.entrySet().toArray();
                if (array.length > 0) {
                    c = ((Map.Entry<Integer, Integer>) array[0]).getKey();
                } else {
                    return true;
                }
            } else {
                num--;
                c = depC;
            }
        }

        return map.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(new CanFinish().canFinish(2, new int[][]{new int[]{1, 4}, new int[]{3, 2}}));
        // System.out.println(new CanFinish().canFinish(2, new int[][]{new int[]{1, 0}}));
        // System.out.println(new CanFinish().canFinish(2, new int[][]{new int[]{1, 4}, new int[]{2, 4}, new int[]{3, 1}, new int[]{3, 2}}));
        // System.out.println(new CanFinish().canFinish(2, new int[][]{new int[]{1, 0}, new int[]{0, 1}}));
    }

}
