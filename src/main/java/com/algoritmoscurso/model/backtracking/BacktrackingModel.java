package com.algoritmoscurso.model.backtracking;

import com.algoritmoscurso.interfaces.IBacktrackingAlgorithm;
import com.algoritmoscurso.interfaces.IModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BacktrackingModel implements IModel {
    private final List<IBacktrackingAlgorithm> algorithms;

    public BacktrackingModel() {
        this.algorithms = new ArrayList<>();
        initialize();
    }

    @Override
    public void initialize() {
        algorithms.clear();
        algorithms.add(new TowerOfHanoiAlgorithm());
        algorithms.add(new KnightTourAlgorithm());
    }

    @Override
    public boolean validate() {
        return !algorithms.isEmpty();
    }

    @Override
    public void clear() {
        algorithms.clear();
    }

    public List<IBacktrackingAlgorithm> getAlgorithms() {
        if (algorithms.isEmpty()) {
            initialize();
        }
        return new ArrayList<>(algorithms);
    }

    public IBacktrackingAlgorithm findAlgorithm(String name) {
        return getAlgorithms().stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public BacktrackingResult run(IBacktrackingAlgorithm algorithm, int size, Object extra) {
        if (algorithm == null) {
            List<String> message = new ArrayList<>();
            message.add("No se seleccionó algoritmo");
            return new BacktrackingResult(message, Collections.<int[]>emptyList());
        }
        return algorithm.execute(size, extra);
    }

    /**
     * Método de conveniencia para ejecutar el Tour del Caballo
     */
    public BacktrackingResult solveKnightTour(int boardSize, int startX, int startY) {
        IBacktrackingAlgorithm knightTour = findAlgorithm("Salto del Caballo");
        if (knightTour == null) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("Algoritmo Salto del Caballo no encontrado");
            return new BacktrackingResult(errorMessage, Collections.<int[]>emptyList());
        }
        
        // Pasar coordenadas de inicio como array
        int[] startPosition = {startX, startY};
        return knightTour.execute(boardSize, startPosition);
    }

    /**
     * Método de conveniencia para ejecutar Torres de Hanoi
     */
    public BacktrackingResult solveTowerOfHanoi(int numberOfDisks) {
        IBacktrackingAlgorithm hanoi = findAlgorithm("Torres de Hanoi");
        if (hanoi == null) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("Algoritmo Torres de Hanoi no encontrado");
            return new BacktrackingResult(errorMessage, Collections.<int[]>emptyList());
        }
        
        return hanoi.execute(numberOfDisks, null);
    }
}
