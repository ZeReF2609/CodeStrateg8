package com.algoritmoscurso.model.mochila;

import com.algoritmoscurso.interfaces.IMochila;
import com.algoritmoscurso.model.dp.DPResult;
import com.algoritmoscurso.model.dp.KnapsackSolver;

public class MochilaAdapter implements IMochila {
    private final KnapsackSolver impl = new KnapsackSolver();

    @Override
    public DPResult solve(int[] weights, int[] values, int capacity) {
        return impl.solve(weights, values, capacity);
    }
}
