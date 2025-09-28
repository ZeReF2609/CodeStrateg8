package com.algoritmoscurso.model.graph;

import com.algoritmoscurso.interfaces.IGraphAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraAlgorithm implements IGraphAlgorithm {
    private GraphModel graph;

    @Override
    public String getName() {
        return "Algoritmo de Dijkstra";
    }

    @Override
    public String getDescription() {
        return "Calcula las rutas más cortas desde un vértice origen hacia el resto de vértices.";
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
        int n = graph.size();
        if (startVertex < 0 || startVertex >= n) {
            startVertex = 0;
        }
        int target = n - 1;

        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[startVertex] = 0;

        List<String> steps = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int u = selectMinVertex(dist, visited);
            if (u == -1) break;
            visited[u] = true;
            steps.add(String.format("Seleccionado %s con distancia %d", graph.label(u), dist[u]));
            int[][] matrix = graph.getAdjacencyMatrix();
            for (int v = 0; v < n; v++) {
                int weight = matrix[u][v];
                if (weight > 0 && !visited[v] && dist[u] != Integer.MAX_VALUE) {
                    int newDist = dist[u] + weight;
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        prev[v] = u;
                        steps.add(String.format("Actualizado %s -> %s = %d", graph.label(u), graph.label(v), newDist));
                    }
                }
            }
        }

        List<String> path = buildPath(prev, startVertex, target);
        List<String> edges = new ArrayList<>();
        int total = dist[target];
        if (!path.isEmpty()) {
            for (int i = 0; i < path.size() - 1; i++) {
                String from = path.get(i);
                String to = path.get(i + 1);
                int fromIndex = indexOfLabel(from);
                int toIndex = indexOfLabel(to);
                int weight = graph.getAdjacencyMatrix()[fromIndex][toIndex];
                edges.add(String.format("%s - %s (%d)", from, to, weight));
            }
        }
        if (total == Integer.MAX_VALUE) {
            total = 0;
        }
        String summary = path.isEmpty()
                ? "No se encontró ruta hacia el destino seleccionado"
                : String.format("Ruta más corta de %s a %s con costo %d", graph.label(startVertex), graph.label(target), total);

        return new GraphResult(summary, path, edges, steps, total);
    }

    private int selectMinVertex(int[] dist, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int vertex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < minDistance) {
                minDistance = dist[i];
                vertex = i;
            }
        }
        return vertex;
    }

    private List<String> buildPath(int[] prev, int start, int target) {
        List<String> path = new ArrayList<>();
        int current = target;
        while (current != -1) {
            path.add(0, graph.label(current));
            if (current == start) {
                break;
            }
            current = prev[current];
        }
        if (path.isEmpty() || !path.get(0).equals(graph.label(start))) {
            return Collections.<String>emptyList();
        }
        return path;
    }

    private int indexOfLabel(String label) {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.label(i).equals(label)) {
                return i;
            }
        }
        return -1;
    }
}
