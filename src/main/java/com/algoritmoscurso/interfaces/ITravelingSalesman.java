package com.algoritmoscurso.interfaces;

import java.util.List;

/**
 * Interfaz para el problema del agente viajero
 */
public interface ITravelingSalesman {
    /**
     * Encuentra la ruta más corta usando algoritmo voraz
     * @param distances matriz de distancias entre ciudades
     * @param startCity ciudad de inicio
     * @return lista de ciudades en orden de visita
     */
    List<Integer> findRoute(double[][] distances, int startCity);
    
    /**
     * Calcula la distancia total de una ruta
     * @param route ruta de ciudades
     * @param distances matriz de distancias
     * @return distancia total
     */
    double calculateTotalDistance(List<Integer> route, double[][] distances);
}
