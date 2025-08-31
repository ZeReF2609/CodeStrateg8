package com.algoritmoscurso.interfaces;

/**
 * Interface para algoritmos de ordenación
 * Define el contrato que deben cumplir todos los algoritmos de ordenación
 */
public interface ISortingAlgorithm {
    /**
     * Ordena un arreglo de enteros
     * @param array arreglo a ordenar
     * @return arreglo ordenado
     */
    int[] sort(int[] array);
    
    /**
     * Obtiene el nombre del algoritmo
     * @return nombre del algoritmo
     */
    String getName();
    
    /**
     * Obtiene la complejidad temporal del algoritmo
     * @return complejidad temporal en notación Big O
     */
    String getTimeComplexity();
    
    /**
     * Obtiene la complejidad espacial del algoritmo
     * @return complejidad espacial en notación Big O
     */
    String getSpaceComplexity();
    
    /**
     * Obtiene una descripción del algoritmo
     * @return descripción del algoritmo
     */
    String getDescription();
}
