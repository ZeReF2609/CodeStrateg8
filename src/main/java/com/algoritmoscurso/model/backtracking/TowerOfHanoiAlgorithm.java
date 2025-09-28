package com.algoritmoscurso.model.backtracking;

import com.algoritmoscurso.interfaces.IBacktrackingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TowerOfHanoiAlgorithm implements IBacktrackingAlgorithm {
    @Override
    public String getName() {
        return "Torres de Hanoi";
    }

    @Override
    public String getDescription() {
        return "Mueve discos entre torres siguiendo las reglas del puzzle.";
    }

    @Override
    public BacktrackingResult execute(int discs, Object extra) {
        int numberOfDiscs = discs <= 0 ? 3 : discs;
        List<String> moves = new ArrayList<>();
        solve(numberOfDiscs, 'A', 'C', 'B', moves);
        return new BacktrackingResult(moves, Collections.<int[]>emptyList());
    }

    private void solve(int n, char from, char to, char aux, List<String> moves) {
        if (n == 1) {
            moves.add(String.format("Mover disco 1 de %c a %c", from, to));
            return;
        }
        solve(n - 1, from, aux, to, moves);
        moves.add(String.format("Mover disco %d de %c a %c", n, from, to));
        solve(n - 1, aux, to, from, moves);
    }
}
