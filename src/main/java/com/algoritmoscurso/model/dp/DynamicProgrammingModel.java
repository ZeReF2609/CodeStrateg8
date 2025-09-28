package com.algoritmoscurso.model.dp;

import com.algoritmoscurso.interfaces.IFloyd;
import com.algoritmoscurso.interfaces.IMochila;
import com.algoritmoscurso.model.floyd.FloydAdapter;
import com.algoritmoscurso.model.mochila.MochilaAdapter;

/**
 * Model facade for dynamic programming week that uses the project's interface naming.
 */
public class DynamicProgrammingModel {
    private final IMochila mochila = new MochilaAdapter();
    private final IFloyd floyd = new FloydAdapter();

    public DPResult solveKnapsack(int[] weights, int[] values, int capacity) {
        return mochila.solve(weights, values, capacity);
    }

    public DPResult solveFloyd(int[][] matrix) {
        return floyd.solve(matrix);
    }
}
