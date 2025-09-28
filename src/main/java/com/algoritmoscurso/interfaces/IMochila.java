package com.algoritmoscurso.interfaces;

import com.algoritmoscurso.model.dp.DPResult;

/** Interface para el solver de Mochila 0/1 */
public interface IMochila {
    DPResult solve(int[] weights, int[] values, int capacity);
}
