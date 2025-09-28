package com.algoritmoscurso.model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resultado genérico de un algoritmo de grafos.
 */
public class GraphResult {
    private final String summary;
    private final List<String> highlightedPath;
    private final List<String> selectedEdges;
    private final List<String> steps;
    private final int totalCost;

    public GraphResult(String summary, List<String> highlightedPath, List<String> selectedEdges, List<String> steps, int totalCost) {
    this.summary = summary;
    this.highlightedPath = highlightedPath == null
        ? Collections.<String>emptyList()
        : Collections.unmodifiableList(new ArrayList<>(highlightedPath));
    this.selectedEdges = selectedEdges == null
        ? Collections.<String>emptyList()
        : Collections.unmodifiableList(new ArrayList<>(selectedEdges));
    this.steps = steps == null
        ? Collections.<String>emptyList()
        : Collections.unmodifiableList(new ArrayList<>(steps));
        this.totalCost = totalCost;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getHighlightedPath() {
        return highlightedPath;
    }

    public List<String> getSelectedEdges() {
        return selectedEdges;
    }

    public List<String> getSteps() {
        return steps;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
