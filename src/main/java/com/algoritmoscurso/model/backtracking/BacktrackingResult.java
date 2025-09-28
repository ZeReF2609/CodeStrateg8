package com.algoritmoscurso.model.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Almacena la secuencia de movimientos generada por un algoritmo de backtracking.
 */
public class BacktrackingResult {
    private final List<String> moves;
    private final List<int[]> boardSnapshots;

    public BacktrackingResult(List<String> moves, List<int[]> boardSnapshots) {
    this.moves = moves == null ? Collections.<String>emptyList()
        : Collections.unmodifiableList(new ArrayList<>(moves));
    this.boardSnapshots = boardSnapshots == null ? Collections.<int[]>emptyList()
        : Collections.unmodifiableList(new ArrayList<>(boardSnapshots));
    }

    public List<String> getMoves() {
    return moves;
    }

    /**
     * Representación opcional del tablero (por ejemplo, tour del caballo).
     * Cada snapshot es un arreglo plano que representa la cuadrícula.
     */
    public List<int[]> getBoardSnapshots() {
    return boardSnapshots;
    }
}
