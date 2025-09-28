package com.algoritmoscurso.interfaces;

import com.algoritmoscurso.model.dp.DPResult;

/** Interface para el solver de Floyd-Warshall */
public interface IFloyd {
    DPResult solve(int[][] adjacencyMatrix);
}
