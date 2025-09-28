package com.algoritmoscurso.model.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple 0/1 knapsack DP solver that records DP table snapshots for visualization.
 */
public class KnapsackSolver {
    /**
     * items: weights[] and values[] length n, capacity W
     * returns DPResult with best value and snapshots (each snapshot is flattened table rows*(W+1))
     */
    public DPResult solve(int[] weights, int[] values, int W) {
        int n = weights.length;
        int[][] dp = new int[n + 1][W + 1];
        List<int[]> snaps = new ArrayList<>();

        // initial snapshot
        snaps.add(flatten(dp));

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
            // snapshot after processing item i
            snaps.add(flatten(dp));
            // cap snapshots
            if (snaps.size() > 400) break;
        }

        // recover selected items
        List<Integer> selected = new ArrayList<>();
        int res = dp[n][W];
        int w = W;
        for (int i = n; i > 0 && res > 0; i--) {
            if (res != dp[i - 1][w]) {
                selected.add(i - 1);
                res -= values[i - 1];
                w -= weights[i - 1];
            }
        }

        return new DPResult(dp[n][W], snaps, selected);
    }

    private int[] flatten(int[][] m) {
        int r = m.length; int c = m[0].length;
        int[] flat = new int[r * c];
        int idx = 0;
        for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) flat[idx++] = m[i][j];
        return flat;
    }
}
