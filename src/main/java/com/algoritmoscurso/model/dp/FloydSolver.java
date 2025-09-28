package com.algoritmoscurso.model.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Floyd-Warshall solver that records snapshots of the distance matrix for visualization.
 */
public class FloydSolver {
    /**
     * graph: adjacency matrix where Integer.MAX_VALUE or large value means no edge.
     */
    public DPResult solve(int[][] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        List<int[]> snaps = new ArrayList<>();

        int[][] next = new int[n][n];

        final int INF = Integer.MAX_VALUE / 4;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
                if (i == j) dist[i][j] = 0;
                if (dist[i][j] == 0 && i != j) dist[i][j] = INF; // assume 0 means no edge in some inputs
                if (dist[i][j] < INF && i != j) next[i][j] = j; else next[i][j] = -1;
            }
        }

        snaps.add(flatten(dist));

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
            snaps.add(flatten(dist));
            if (snaps.size() > 400) break;
        }

        // Reconstruct path from node 0 (A) to node n-1 (E)
        List<Integer> path = new ArrayList<>();
        int s = 0; int t = n - 1;
        if (next[s][t] != -1) {
            int cur = s;
            path.add(cur);
            while (cur != t) {
                cur = next[cur][t];
                if (cur == -1) { path.clear(); break; }
                path.add(cur);
            }
        }

        int best = dist[0][n-1] >= INF ? Integer.MAX_VALUE : dist[0][n-1];
        return new DPResult(best, snaps, path);
    }

    private int[] flatten(int[][] m) {
        int r = m.length; int c = m[0].length;
        int[] flat = new int[r * c];
        int idx = 0;
        for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) flat[idx++] = m[i][j];
        return flat;
    }
}
