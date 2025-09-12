package com.algoritmoscurso.model.greedy;

import com.algoritmoscurso.interfaces.ITravelingSalesman;
import com.algoritmoscurso.interfaces.IGreedyAlgorithm;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del algoritmo voraz para el problema del agente viajero
 */
public class TravelingSalesmanGreedy implements ITravelingSalesman, IGreedyAlgorithm {
    private double[][] distances;
    private int startCity;
    private List<Integer> route;
    private double totalDistance;
    
    public TravelingSalesmanGreedy() {
        this.route = new ArrayList<>();
        this.totalDistance = 0.0;
    }
    
    public TravelingSalesmanGreedy(double[][] distances, int startCity) {
        this.distances = distances;
        this.startCity = startCity;
        this.route = new ArrayList<>();
        this.totalDistance = 0.0;
    }
    
    @Override
    public List<Integer> findRoute(double[][] distances, int startCity) {
        this.distances = distances;
        this.startCity = startCity;
        this.route = new ArrayList<>();
        this.totalDistance = 0.0;
        
        int numCities = distances.length;
        boolean[] visited = new boolean[numCities];
        
        // Comenzar desde la ciudad inicial
        int currentCity = startCity;
        route.add(currentCity);
        visited[currentCity] = true;
        
        // Visitar las ciudades restantes
        for (int i = 1; i < numCities; i++) {
            int nearestCity = findNearestUnvisitedCity(currentCity, visited);
            if (nearestCity != -1) {
                route.add(nearestCity);
                totalDistance += distances[currentCity][nearestCity];
                visited[nearestCity] = true;
                currentCity = nearestCity;
            }
        }
        
        // Regresar a la ciudad inicial
        if (route.size() > 1) {
            totalDistance += distances[currentCity][startCity];
            route.add(startCity);
        }
        
        return new ArrayList<>(route);
    }
    
    private int findNearestUnvisitedCity(int currentCity, boolean[] visited) {
        int nearestCity = -1;
        double minDistance = Double.MAX_VALUE;
        
        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[currentCity][i] < minDistance) {
                minDistance = distances[currentCity][i];
                nearestCity = i;
            }
        }
        
        return nearestCity;
    }
    
    @Override
    public double calculateTotalDistance(List<Integer> route, double[][] distances) {
        if (route.size() < 2) return 0.0;
        
        double total = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += distances[route.get(i)][route.get(i + 1)];
        }
        
        return total;
    }
    
    @Override
    public Object execute() {
        if (distances != null && distances.length > 0) {
            return findRoute(distances, startCity);
        }
        return new ArrayList<>();
    }
    
    @Override
    public String getDescription() {
        return "Algoritmo voraz para el problema del agente viajero. " +
               "En cada paso, selecciona la ciudad más cercana no visitada " +
               "hasta visitar todas las ciudades y regresar al punto de partida.";
    }
    
    @Override
    public String getName() {
        return "Agente Viajero (Greedy)";
    }
    
    // Getters y setters
    public double[][] getDistances() {
        return distances;
    }
    
    public void setDistances(double[][] distances) {
        this.distances = distances;
    }
    
    public int getStartCity() {
        return startCity;
    }
    
    public void setStartCity(int startCity) {
        this.startCity = startCity;
    }
    
    public List<Integer> getRoute() {
        return new ArrayList<>(route);
    }
    
    public double getTotalDistance() {
        return totalDistance;
    }
}
