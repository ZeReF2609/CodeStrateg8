package com.algoritmoscurso.interfaces;

import java.util.List;

/**
 * Interfaz para el problema del cambio de moneda
 */
public interface ICoinChange {
    /**
     * Calcula el cambio mínimo de monedas
     * @param amount cantidad a cambiar
     * @param coins monedas disponibles
     * @return lista de monedas utilizadas
     */
    List<Integer> makeChange(int amount, int[] coins);
    
    /**
     * Obtiene el número mínimo de monedas necesarias
     * @param amount cantidad a cambiar
     * @param coins monedas disponibles
     * @return número mínimo de monedas
     */
    int getMinCoins(int amount, int[] coins);
}
