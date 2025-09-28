package com.algoritmoscurso.model.floyd;

import com.algoritmoscurso.interfaces.IFloyd;
import com.algoritmoscurso.model.dp.DPResult;
import com.algoritmoscurso.model.dp.FloydSolver;

public class FloydAdapter implements IFloyd {
    private final FloydSolver impl = new FloydSolver();

    @Override
    public DPResult solve(int[][] adjacencyMatrix) {
        return impl.solve(adjacencyMatrix);
    }
}
