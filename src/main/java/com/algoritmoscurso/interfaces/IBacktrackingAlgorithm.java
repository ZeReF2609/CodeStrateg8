package com.algoritmoscurso.interfaces;

import com.algoritmoscurso.model.backtracking.BacktrackingResult;

/**
 * Contrato para algoritmos de backtracking que generan secuencias de movimientos.
 */
public interface IBacktrackingAlgorithm {
    /**
     * Nombre descriptivo del algoritmo.
     */
    String getName();

    /**
     * Breve descripción del problema que resuelve.
     */
    String getDescription();

    /**
     * Ejecuta el algoritmo con los parámetros indicados.
     *
     * @param size     tamaño del problema (por ejemplo cantidad de discos o dimensión del tablero).
     * @param extra    parámetro opcional (como posición inicial); puede ser {@code null}.
     * @return resultado con los movimientos calculados.
     */
    BacktrackingResult execute(int size, Object extra);
}
