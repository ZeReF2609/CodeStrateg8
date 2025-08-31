package com.algoritmoscurso;

import com.algoritmoscurso.controller.ApplicationController;
import javax.swing.SwingUtilities;

/**
 * Clase principal de la aplicación Repositorio de Algoritmos
 * Curso: Análisis de Algoritmos
 * 
 * Implementa el patrón MVC (Model-View-Controller) para una mejor
 * organización y separación de responsabilidades.
 * 
 * Estructura MVC:
 * - Model: Gestiona datos y lógica de negocio
 * - View: Maneja la interfaz de usuario
 * - Controller: Coordina la comunicación entre Model y View
 */
public class Main {
    public static void main(String[] args) {
        // Inicializar la aplicación en el EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                new ApplicationController();
            } catch (Exception e) {
                System.err.println("Error al inicializar la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
