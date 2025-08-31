package com.algoritmoscurso.interfaces;

/**
 * Interface para ejemplos de análisis de complejidad
 * Define el contrato para ejemplos de Big O
 */
public interface IBigOExample {
    /**
     * Ejecuta el ejemplo
     * @param input parámetro de entrada para el ejemplo
     * @return resultado de la ejecución
     */
    Object execute(Object input);
    
    /**
     * Obtiene el código fuente del ejemplo
     * @return código fuente como string
     */
    String getSourceCode();
    
    /**
     * Obtiene el análisis de complejidad
     * @return análisis de complejidad Big O
     */
    String getComplexityAnalysis();
    
    /**
     * Obtiene el nombre del ejemplo
     * @return nombre del ejemplo
     */
    String getName();
    
    /**
     * Obtiene la complejidad temporal
     * @return complejidad temporal en notación Big O
     */
    String getTimeComplexity();
}
