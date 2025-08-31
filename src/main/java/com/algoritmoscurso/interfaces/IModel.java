package com.algoritmoscurso.interfaces;

/**
 * Interface para modelos de datos genéricos
 * Define operaciones básicas de manipulación de datos
 */
public interface IModel {
    /**
     * Inicializa el modelo con datos por defecto
     */
    void initialize();
    
    /**
     * Valida los datos del modelo
     * @return true si los datos son válidos, false en caso contrario
     */
    boolean validate();
    
    /**
     * Limpia todos los datos del modelo
     */
    void clear();
    
    /**
     * Obtiene una representación en string del modelo
     * @return representación del modelo
     */
    String toString();
}
