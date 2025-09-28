package com.algoritmoscurso.model.backtracking;

import com.algoritmoscurso.interfaces.IBacktrackingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class KnightTourAlgorithm implements IBacktrackingAlgorithm {
    private static final int[] ROW_MOVES = { 2, 1, -1, -2, -2, -1, 1, 2 };
    private static final int[] COL_MOVES = { 1, 2, 2, 1, -1, -2, -2, -1 };

    @Override
    public String getName() {
        return "Salto del Caballo";
    }

    @Override
    public String getDescription() {
        return "Recorre todas las casillas de un tablero con el movimiento del caballo.";
    }

    @Override
    public BacktrackingResult execute(int size, Object extra) {
        int boardSize = size <= 0 ? 5 : size;
        int[][] board = new int[boardSize][boardSize];
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                board[r][c] = -1;
            }
        }

        int startRow = 0;
        int startCol = 0;

        // ✅ Versión compatible con Java 8
        if (extra instanceof int[]) {
            int[] coords = (int[]) extra;
            if (coords.length == 2) {
                startRow = Math.max(0, Math.min(boardSize - 1, coords[0]));
                startCol = Math.max(0, Math.min(boardSize - 1, coords[1]));
            }
        }

        List<String> moves = new ArrayList<>();
        board[startRow][startCol] = 0;
        moves.add(String.format("Paso 0: (%d, %d)", startRow, startCol));

        List<int[]> snapshots = new ArrayList<>();
        snapshots.add(flatten(board)); // estado inicial

        if (!visit(board, startRow, startCol, 1, moves, snapshots)) {
            moves.add("No se encontró un recorrido completo para el tablero indicado.");
            // Devolver snapshots parciales para poder visualizar el progreso
            // Limitar el número de snapshots para evitar uso excesivo de memoria
            int MAX_SNAPS = 5000;
            if (snapshots.size() > MAX_SNAPS) {
                snapshots = snapshots.subList(0, MAX_SNAPS);
            }
            return new BacktrackingResult(moves, snapshots);
        }

        // Si se encontró recorrido completo, devolver todos los snapshots
        return new BacktrackingResult(moves, snapshots);
    }

    private boolean visit(int[][] board, int row, int col, int step, List<String> moves, List<int[]> snapshots) {
        int total = board.length * board.length;
        if (step == total) {
            return true;
        }
        for (int i = 0; i < ROW_MOVES.length; i++) {
            int nextRow = row + ROW_MOVES[i];
            int nextCol = col + COL_MOVES[i];
            if (isValidMove(board, nextRow, nextCol)) {
                board[nextRow][nextCol] = step;
                moves.add(String.format("Paso %d: (%d, %d)", step, nextRow, nextCol));
                snapshots.add(flatten(board));
                if (visit(board, nextRow, nextCol, step + 1, moves, snapshots)) {
                    return true;
                }
                // backtrack
                board[nextRow][nextCol] = -1;
                moves.remove(moves.size() - 1);
                snapshots.remove(snapshots.size() - 1);
            }
        }
        return false;
    }

    private boolean isValidMove(int[][] board, int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length && board[row][col] == -1;
    }

    private int[] flatten(int[][] board) {
        int size = board.length * board.length;
        int[] flat = new int[size];
        int index = 0;
        for (int[] rows : board) {
            for (int value : rows) {
                flat[index++] = value;
            }
        }
        return flat;
    }
}
