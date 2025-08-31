package com.algoritmoscurso.interfaces;

/**
 * Interface para vistas en el patrón MVC
 * Define operaciones básicas para la interfaz de usuario
 */
public interface IView {
    /**
     * Inicializa la vista y sus componentes
     */
    void initializeView();
    
    /**
     * Actualiza la vista con nuevos datos
     * @param data datos para actualizar la vista
     */
    void updateView(Object data);
    
    /**
     * Muestra un mensaje al usuario
     * @param message mensaje a mostrar
     */
    void showMessage(String message);
    
    /**
     * Muestra un mensaje de error al usuario
     * @param error mensaje de error
     */
    void showError(String error);
    
    /**
     * Habilita o deshabilita la vista
     * @param enabled true para habilitar, false para deshabilitar
     */
    void setEnabled(boolean enabled);
}
