package com.algoritmoscurso.model.backtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Solver independiente que permite enviar snapshots durante la búsqueda.
 */
public class KnightTourSolver {
    private static final int[] ROW_MOVES = { 2, 1, -1, -2, -2, -1, 1, 2 };
    private static final int[] COL_MOVES = { 1, 2, 2, 1, -1, -2, -2, -1 };
    private static final int SNAPSHOT_LIMIT = 500;

    /**
     * Ejecuta la búsqueda del tour y llama a onSnapshot.accept(flatBoard) cada vez que se marca una casilla.
     * Retorna un BacktrackingResult con los movimientos y snapshots (parciales o completos).
     */
    public BacktrackingResult solve(int n, int startRow, int startCol, Consumer<int[]> onSnapshot) {
        int boardSize = Math.max(1, n);
        int[][] board = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) board[i][j] = -1;
        }

        int sr = Math.max(0, Math.min(boardSize - 1, startRow));
        int sc = Math.max(0, Math.min(boardSize - 1, startCol));

        List<String> moves = new ArrayList<>();
        board[sr][sc] = 0;
        moves.add(String.format("Paso 0: (%d, %d)", sr, sc));
        if (onSnapshot != null) onSnapshot.accept(flatten(board));

    // Avoid unbounded snapshot accumulation which can OOM on hard searches.
    List<int[]> snapshots = new ArrayList<>();
    snapshots.add(flatten(board));
        if (!visit(board, sr, sc, 1, moves, snapshots, onSnapshot)) {
            moves.add("No se encontró un recorrido completo para el tablero indicado.");
            return new BacktrackingResult(moves, snapshots);
        }

        return new BacktrackingResult(moves, snapshots);
    }

    private boolean visit(int[][] board, int row, int col, int step, List<String> moves, List<int[]> snapshots, Consumer<int[]> onSnapshot) {
    int total = board.length * board.length;
    // If we've reached 'total' steps (0..total-1) then tour is complete
    if (step >= total) return true;
        // Warnsdorff heuristic: sort next moves by available onward moves (ascending)
        java.util.List<int[]> candidates = new java.util.ArrayList<>();
        for (int k = 0; k < ROW_MOVES.length; k++) {
            int nr = row + ROW_MOVES[k];
            int nc = col + COL_MOVES[k];
            if (isValid(board, nr, nc)) {
                candidates.add(new int[] {nr, nc});
            }
        }

        candidates.sort((a, b) -> Integer.compare(onwardMoves(board, a[0], a[1]), onwardMoves(board, b[0], b[1])));

        for (int[] mv : candidates) {
            int nr = mv[0], nc = mv[1];
            board[nr][nc] = step;
            moves.add(String.format("Paso %d: (%d, %d)", step, nr, nc));
            int[] flat = flatten(board);
            if (onSnapshot != null) onSnapshot.accept(flat);
            if (snapshots.size() < SNAPSHOT_LIMIT) snapshots.add(flat);

            if (visit(board, nr, nc, step + 1, moves, snapshots, onSnapshot)) return true;

            // backtrack
            board[nr][nc] = -1;
            moves.remove(moves.size() - 1);
            if (!snapshots.isEmpty()) snapshots.remove(snapshots.size() - 1);
        }
        return false;
    }

    private int onwardMoves(int[][] board, int r, int c) {
        int cnt = 0;
        for (int k = 0; k < ROW_MOVES.length; k++) {
            int nr = r + ROW_MOVES[k];
            int nc = c + COL_MOVES[k];
            if (nr >= 0 && nr < board.length && nc >= 0 && nc < board.length && board[nr][nc] == -1) cnt++;
        }
        return cnt;
    }

    private boolean isValid(int[][] board, int r, int c) {
        return r >= 0 && r < board.length && c >= 0 && c < board.length && board[r][c] == -1;
    }

    private int[] flatten(int[][] board) {
        int size = board.length * board.length;
        int[] flat = new int[size];
        int idx = 0;
        for (int[] rows : board) for (int v : rows) flat[idx++] = v;
        return flat;
    }
}
