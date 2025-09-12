package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.interfaces.ICoinChange;
import com.algoritmoscurso.interfaces.ITravelingSalesman;
import com.algoritmoscurso.model.greedy.GreedyModel;
import com.algoritmoscurso.model.greedy.CoinChangeGreedy;
import com.algoritmoscurso.model.greedy.TravelingSalesmanGreedy;
import com.algoritmoscurso.view.semana3.GreedyView;
import com.algoritmoscurso.view.semana3.CoinChangeView;
import com.algoritmoscurso.view.semana3.TravelingSalesmanView;

/**
 * Controlador para algoritmos voraces de la semana 3
 * Gestiona Coin Change, Traveling Salesman y otros algoritmos greedy
 */
public class GreedyController implements IController {
    private GreedyModel model;
    private GreedyView mainView;
    
    // Algoritmos disponibles
    private ICoinChange coinChangeAlgorithm;
    private ITravelingSalesman tspAlgorithm;
    
    public GreedyController(GreedyModel model) {
        this.model = model;
        this.mainView = new GreedyView();
        setupAlgorithms();
    }
    
    @Override
    public void initialize() {
        setupAlgorithms();
        updateView();
        
        // Inicializar las vistas individuales si es necesario
        if (mainView != null) {
            mainView.initialize();
        }
    }
    
    /**
     * Configura los algoritmos disponibles
     */
    private void setupAlgorithms() {
        // Inicializar algoritmos específicos
        coinChangeAlgorithm = new CoinChangeGreedy();
        tspAlgorithm = new TravelingSalesmanGreedy();
    }
    
    @Override
    public void handleEvent(String eventType, Object data) {
        switch (eventType) {
            case "EXECUTE_COIN_CHANGE":
                executeCoinChange((Object[]) data);
                break;
            case "EXECUTE_TSP":
                executeTSP((Object[]) data);
                break;
            case "CLEAR_RESULTS":
                clearResults();
                break;
            default:
                System.out.println("Evento no manejado en GreedyController: " + eventType);
                break;
        }
    }
    
    /**
     * Ejecuta el algoritmo de cambio de moneda
     */
    private void executeCoinChange(Object[] parameters) {
        try {
            if (parameters != null && parameters.length >= 2) {
                int amount = (Integer) parameters[0];
                int[] denominations = (int[]) parameters[1];
                
                if (coinChangeAlgorithm != null) {
                    java.util.List<Integer> result = coinChangeAlgorithm.makeChange(amount, denominations);
                    int minCoins = coinChangeAlgorithm.getMinCoins(amount, denominations);
                    
                    // Actualizar modelo
                    model.setLastCoinChange(result);
                    String resultMsg = "Cambio para " + amount + ": " + result + " (Total: " + minCoins + " monedas)";
                    model.processData(resultMsg);
                    
                    // Actualizar vista si tiene método para mostrar resultados
                    if (mainView != null) {
                        mainView.displayResults(resultMsg);
                    }
                }
            }
        } catch (Exception ex) {
            String errorMsg = "Error en cambio de moneda: " + ex.getMessage();
            model.processData(errorMsg);
            if (mainView != null) {
                mainView.showError(errorMsg);
            }
        }
    }
    
    /**
     * Ejecuta el algoritmo del agente viajero
     */
    private void executeTSP(Object[] parameters) {
        try {
            if (parameters != null && parameters.length >= 2) {
                double[][] distances = (double[][]) parameters[0];
                int startCity = (Integer) parameters[1];
                
                if (tspAlgorithm != null) {
                    java.util.List<Integer> route = tspAlgorithm.findRoute(distances, startCity);
                    
                    // Calcular distancia total (implementación simple)
                    double totalDistance = calculateTotalDistance(distances, route);
                    
                    // Actualizar modelo
                    model.setLastTravelRoute(route);
                    model.setLastTravelDistance(totalDistance);
                    String resultMsg = "Ruta TSP desde ciudad " + startCity + ": " + route + 
                                      " (Distancia total: " + String.format("%.2f", totalDistance) + ")";
                    model.processData(resultMsg);
                    
                    // Actualizar vista
                    if (mainView != null) {
                        mainView.displayResults(resultMsg);
                    }
                }
            }
        } catch (Exception ex) {
            String errorMsg = "Error en TSP: " + ex.getMessage();
            model.processData(errorMsg);
            if (mainView != null) {
                mainView.showError(errorMsg);
            }
        }
    }
    
    /**
     * Calcula la distancia total de una ruta
     */
    private double calculateTotalDistance(double[][] distances, java.util.List<Integer> route) {
        double total = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            int from = route.get(i);
            int to = route.get(i + 1);
            total += distances[from][to];
        }
        // Regresar al punto de inicio
        if (route.size() > 1) {
            total += distances[route.get(route.size() - 1)][route.get(0)];
        }
        return total;
    }
    
    /**
     * Limpia los resultados
     */
    private void clearResults() {
        model.clear();
        if (mainView != null) {
            mainView.clear();
        }
    }
    
    @Override
    public void updateModel(String action, Object... parameters) {
        switch (action) {
            case "CLEAR_RESULTS":
                model.clear();
                break;
            case "SET_COIN_CHANGE":
                if (parameters[0] instanceof java.util.List<?>) {
                    @SuppressWarnings("unchecked")
                    java.util.List<Integer> coinChange = (java.util.List<Integer>) parameters[0];
                    model.setLastCoinChange(coinChange);
                }
                break;
            case "SET_TSP_ROUTE":
                if (parameters.length >= 2) {
                    if (parameters[0] instanceof java.util.List<?>) {
                        @SuppressWarnings("unchecked")
                        java.util.List<Integer> route = (java.util.List<Integer>) parameters[0];
                        model.setLastTravelRoute(route);
                    }
                    if (parameters[1] instanceof Double) {
                        model.setLastTravelDistance((Double) parameters[1]);
                    }
                }
                break;
            default:
                System.out.println("Acción no manejada en GreedyController: " + action);
                break;
        }
    }
    
    @Override
    public void updateView() {
        if (mainView != null) {
            mainView.updateView(model);
            
            // Actualizar vistas específicas si tienen resultados
            CoinChangeView coinView = mainView.getCoinChangeView();
            TravelingSalesmanView tspView = mainView.getTravelingSalesmanView();
            
            if (coinView != null && model.getLastCoinChange() != null) {
                // Las vistas individuales se manejan por sí mismas
                coinView.updateView(model.getLastCoinChange());
            }
            
            if (tspView != null && model.getLastTravelRoute() != null) {
                // Las vistas individuales se manejan por sí mismas
                tspView.updateView(model.getLastTravelRoute());
            }
        }
    }
    
    /**
     * Obtiene la vista principal de algoritmos voraces
     */
    public GreedyView getView() {
        return mainView;
    }
    
    /**
     * Obtiene la vista de cambio de moneda
     */
    public CoinChangeView getCoinChangeView() {
        return mainView != null ? mainView.getCoinChangeView() : null;
    }
    
    /**
     * Obtiene la vista del agente viajero
     */
    public TravelingSalesmanView getTravelingSalesmanView() {
        return mainView != null ? mainView.getTravelingSalesmanView() : null;
    }
    
    /**
     * Obtiene el modelo
     */
    public GreedyModel getModel() {
        return model;
    }
    
    /**
     * Ejecuta ejemplo de cambio de moneda con valores por defecto
     */
    public void executeCoinChangeExample() {
        executeCoinChange(new Object[]{67, new int[]{25, 10, 5, 1}});
    }
    
    /**
     * Ejecuta ejemplo de TSP con valores por defecto
     */
    public void executeTSPExample() {
        double[][] distances = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        executeTSP(new Object[]{distances, 0});
    }
}
