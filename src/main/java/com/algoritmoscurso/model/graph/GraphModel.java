package com.algoritmoscurso.model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representación dinámica de un grafo ponderado no dirigido.
 */
public class GraphModel {
    private int[][] adjacencyMatrix;
    private String[] vertexLabels;

    public GraphModel() {
        // Constructor vacío para crear grafos dinámicos
    }

    public GraphModel(int size) {
        createEmptyGraph(size);
    }

    /**
     * Crea un grafo vacío con el número especificado de vértices.
     */
    public void createEmptyGraph(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño del grafo debe ser mayor a 0");
        }
        
        vertexLabels = new String[size];
        adjacencyMatrix = new int[size][size];
        
        // Generar etiquetas automáticas
        for (int i = 0; i < size; i++) {
            vertexLabels[i] = String.valueOf((char)('A' + i));
        }
        
        // Inicializar matriz con 0s
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
    }

    /**
     * Carga un grafo de ejemplo predefinido.
     */
    public void loadSampleGraph() {
        vertexLabels = new String[] { "A", "B", "C", "D", "E", "F" };
        adjacencyMatrix = new int[][] {
                //  A   B   C   D   E   F
                {  0,  7,  9,  0, 14,  0 }, // A
                {  7,  0, 10, 15,  0,  0 }, // B
                {  9, 10,  0, 11,  2,  0 }, // C
                {  0, 15, 11,  0,  6,  9 }, // D
                { 14,  0,  2,  6,  0,  9 }, // E
                {  0,  0,  0,  9,  9,  0 }  // F
        };
    }

    /**
     * Establece una arista entre dos vértices con el peso especificado.
     */
    public void setEdge(int from, int to, int weight) {
        if (!isValidVertex(from) || !isValidVertex(to)) {
            throw new IllegalArgumentException("Índice de vértice inválido");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        
        adjacencyMatrix[from][to] = weight;
        adjacencyMatrix[to][from] = weight; // Grafo no dirigido
    }

    /**
     * Obtiene el peso de la arista entre dos vértices.
     */
    public int getEdgeWeight(int from, int to) {
        if (!isValidVertex(from) || !isValidVertex(to)) {
            return 0;
        }
        return adjacencyMatrix[from][to];
    }

    /**
     * Elimina una arista entre dos vértices.
     */
    public void removeEdge(int from, int to) {
        if (isValidVertex(from) && isValidVertex(to)) {
            adjacencyMatrix[from][to] = 0;
            adjacencyMatrix[to][from] = 0;
        }
    }

    /**
     * Verifica si un índice de vértice es válido.
     */
    private boolean isValidVertex(int vertex) {
        return vertex >= 0 && vertex < size();
    }

    public int size() {
        return vertexLabels == null ? 0 : vertexLabels.length;
    }

    public String label(int index) {
        if (vertexLabels == null || index < 0 || index >= vertexLabels.length) {
            return "";
        }
        return vertexLabels[index];
    }

    /**
     * Establece una etiqueta personalizada para un vértice.
     */
    public void setVertexLabel(int index, String label) {
        if (isValidVertex(index)) {
            vertexLabels[index] = label;
        }
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public String[] getVertexLabels() {
        return vertexLabels != null ? vertexLabels.clone() : new String[0];
    }

    public List<Edge> getEdges() {
        if (adjacencyMatrix == null) return Collections.emptyList();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
                int weight = adjacencyMatrix[i][j];
                if (weight > 0) {
                    edges.add(new Edge(i, j, weight));
                }
            }
        }
        return edges;
    }

    public static class Edge implements Comparable<Edge> {
        public final int from;
        public final int to;
        public final int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return String.format("%d-%d (%d)", from, to, weight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;
            Edge edge = (Edge) o;
            return from == edge.from && to == edge.to && weight == edge.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, weight);
        }
    }
}
