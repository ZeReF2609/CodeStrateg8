package com.algoritmoscurso.model.graph;

import com.algoritmoscurso.interfaces.IGraphAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalAlgorithm implements IGraphAlgorithm {
    private GraphModel graph;

    @Override
    public String getName() {
        return "Algoritmo de Kruskal";
    }

    @Override
    public String getDescription() {
        return "Encuentra un árbol de expansión mínima seleccionando las aristas de menor peso.";
    }

    @Override
    public void setGraph(GraphModel graph) {
        this.graph = graph;
    }

    @Override
    public GraphResult execute(int startVertex) {
        if (graph == null || graph.size() == 0) {
        return new GraphResult("No existe un grafo cargado.",
            Collections.<String>emptyList(),
            Collections.<String>emptyList(),
            Collections.<String>emptyList(),
            0);
        }

        List<GraphModel.Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);

        int n = graph.size();
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        List<String> steps = new ArrayList<>();
        List<String> selectedEdges = new ArrayList<>();
        int totalCost = 0;

        for (GraphModel.Edge edge : edges) {
            int rootU = find(parent, edge.from);
            int rootV = find(parent, edge.to);
            String edgeLabel = String.format("%s - %s (%d)", graph.label(edge.from), graph.label(edge.to), edge.weight);
            if (rootU != rootV) {
                union(parent, rank, rootU, rootV);
                selectedEdges.add(edgeLabel);
                totalCost += edge.weight;
                steps.add("Seleccionada arista " + edgeLabel);
            } else {
                steps.add("Descartada arista " + edgeLabel + " (crea ciclo)");
            }
            if (selectedEdges.size() == n - 1) {
                break;
            }
        }

        String summary = selectedEdges.size() == n - 1
                ? String.format("Árbol de expansión mínima con costo %d", totalCost)
                : "No fue posible construir un árbol de expansión mínima";

    return new GraphResult(summary,
        Collections.<String>emptyList(),
        selectedEdges,
        steps,
        totalCost);
    }

    private int find(int[] parent, int node) {
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]);
        }
        return parent[node];
    }

    private void union(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        if (rootX == rootY) return;
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
