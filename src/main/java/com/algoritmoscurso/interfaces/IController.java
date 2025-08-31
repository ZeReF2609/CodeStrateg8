package com.algoritmoscurso.interfaces;

/**
 * Interface para controladores en el patrón MVC
 * Define operaciones básicas de control de la aplicación
 */
public interface IController {
    /**
     * Inicializa el controlador y establece las conexiones entre modelo y vista
     */
    void initialize();
    
    /**
     * Maneja eventos de la vista
     * @param eventType tipo de evento
     * @param data datos asociados al evento
     */
    void handleEvent(String eventType, Object data);
    
    /**
     * Actualiza el modelo basado en acciones del usuario
     * @param action acción a realizar
     * @param parameters parámetros de la acción
     */
    void updateModel(String action, Object... parameters);
    
    /**
     * Actualiza la vista basada en cambios del modelo
     */
    void updateView();
}
