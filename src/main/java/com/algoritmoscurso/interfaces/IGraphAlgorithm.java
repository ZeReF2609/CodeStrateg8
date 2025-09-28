package com.algoritmoscurso.interfaces;

import com.algoritmoscurso.model.graph.GraphModel;
import com.algoritmoscurso.model.graph.GraphResult;

/**
 * Contrato para cualquier algoritmo de grafos que opere sobre {@link GraphModel}.
 */
public interface IGraphAlgorithm {
    /**
     * Nombre legible del algoritmo.
     */
    String getName();

    /**
     * Breve descripción del algoritmo y su propósito.
     */
    String getDescription();

    /**
     * Establece la instancia de grafo sobre la que se ejecutará el algoritmo.
     */
    void setGraph(GraphModel graph);

    /**
     * Ejecuta el algoritmo, utilizando el vértice de inicio cuando aplique.
     *
     * @param startVertex índice del vértice inicial; puede ignorarse si el algoritmo no lo requiere.
     * @return resultado estructurado con los pasos y valores calculados.
     */
    GraphResult execute(int startVertex);
}
