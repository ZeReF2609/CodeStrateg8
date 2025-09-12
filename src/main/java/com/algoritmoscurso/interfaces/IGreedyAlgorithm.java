package com.algoritmoscurso.interfaces;

/**
 * Interfaz para algoritmos voraces (greedy)
 */
public interface IGreedyAlgorithm {
    /**
     * Ejecuta el algoritmo voraz
     * @return resultado del algoritmo
     */
    Object execute();
    
    /**
     * Obtiene una descripción del algoritmo
     * @return descripción del algoritmo
     */
    String getDescription();
    
    /**
     * Obtiene el nombre del algoritmo
     * @return nombre del algoritmo
     */
    String getName();
}
