package com.algoritmoscurso.model.graph;

import com.algoritmoscurso.interfaces.IGraphAlgorithm;
import com.algoritmoscurso.interfaces.IModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo simplificado para algoritmos de grafos que permite operaciones dinámicas.
 */
public class GraphAlgorithmsModel implements IModel {
    private GraphModel graph;
    private List<IGraphAlgorithm> availableAlgorithms;

    public GraphAlgorithmsModel() {
        initializeAlgorithms();
    }

    @Override
    public void initialize() {
        // Inicializar con grafo vacío
        graph = null;
    }

    @Override
    public boolean validate() {
        return graph != null && graph.size() > 0;
    }

    @Override
    public void clear() {
        graph = null;
    }

    private void initializeAlgorithms() {
        availableAlgorithms = new ArrayList<>();
        availableAlgorithms.add(new DijkstraAlgorithm());
        availableAlgorithms.add(new KruskalAlgorithm());
    }

    /**
     * Crea un nuevo grafo vacío con el tamaño especificado.
     */
    public void createGraph(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor a 0");
        }
        graph = new GraphModel(size);
    }

    /**
     * Carga un grafo de muestra predefinido.
     */
    public void loadSampleGraph() {
        graph = new GraphModel();
        graph.loadSampleGraph();
    }

    /**
     * Establece una conexión entre dos vértices.
     */
    public void setConnection(int from, int to, int weight) {
        if (graph == null) {
            throw new IllegalStateException("No hay grafo inicializado");
        }
        graph.setEdge(from, to, weight);
    }

    /**
     * Elimina una conexión entre dos vértices.
     */
    public void removeConnection(int from, int to) {
        if (graph != null) {
            graph.removeEdge(from, to);
        }
    }

    /**
     * Ejecuta un algoritmo específico sobre el grafo actual.
     */
    public GraphResult executeAlgorithm(String algorithmName, int startVertex) {
        if (graph == null) {
            return createErrorResult("No hay grafo cargado");
        }

        IGraphAlgorithm algorithm = findAlgorithmByName(algorithmName);
        if (algorithm == null) {
            return createErrorResult("Algoritmo no encontrado: " + algorithmName);
        }

        algorithm.setGraph(graph);
        return algorithm.execute(startVertex);
    }

    private IGraphAlgorithm findAlgorithmByName(String name) {
        return availableAlgorithms.stream()
                .filter(alg -> alg.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private GraphResult createErrorResult(String message) {
        List<String> emptyList = new ArrayList<>();
        return new GraphResult(message, emptyList, emptyList, emptyList, 0);
    }

    public GraphModel getGraph() {
        return graph;
    }

    public List<String> getAvailableAlgorithmNames() {
        List<String> names = new ArrayList<>();
        for (IGraphAlgorithm algorithm : availableAlgorithms) {
            names.add(algorithm.getName());
        }
        return names;
    }

    public String getAlgorithmDescription(String algorithmName) {
        IGraphAlgorithm algorithm = findAlgorithmByName(algorithmName);
        return algorithm != null ? algorithm.getDescription() : "";
    }

    public List<IGraphAlgorithm> getAlgorithms() {
        return new ArrayList<>(availableAlgorithms);
    }
}
