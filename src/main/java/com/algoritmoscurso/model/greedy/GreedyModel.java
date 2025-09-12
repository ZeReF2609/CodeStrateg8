package com.algoritmoscurso.model.greedy;

import com.algoritmoscurso.interfaces.IModel;
import java.util.List;

/**
 * Modelo para algoritmos voraces
 */
public class GreedyModel implements IModel {
    private String lastResult;
    private List<Integer> lastCoinChange;
    private List<Integer> lastTravelRoute;
    private double lastTravelDistance;
    
    public GreedyModel() {
        this.lastResult = "";
    }
    
    @Override
    public void initialize() {
        this.lastResult = "";
        this.lastCoinChange = null;
        this.lastTravelRoute = null;
        this.lastTravelDistance = 0.0;
    }
    
    @Override
    public boolean validate() {
        return lastResult != null;
    }
    
    @Override
    public void clear() {
        this.lastResult = "";
        this.lastCoinChange = null;
        this.lastTravelRoute = null;
        this.lastTravelDistance = 0.0;
    }
    
    @Override
    public String toString() {
        return "GreedyModel{" +
                "lastResult='" + lastResult + '\'' +
                ", lastCoinChange=" + lastCoinChange +
                ", lastTravelRoute=" + lastTravelRoute +
                ", lastTravelDistance=" + lastTravelDistance +
                '}';
    }
    
    public void processData(Object data) {
        if (data != null) {
            this.lastResult = data.toString();
        }
    }
    
    public Object getResult() {
        return lastResult;
    }
    
    // Getters y setters específicos para algoritmos voraces
    public List<Integer> getLastCoinChange() {
        return lastCoinChange;
    }
    
    public void setLastCoinChange(List<Integer> lastCoinChange) {
        this.lastCoinChange = lastCoinChange;
    }
    
    public List<Integer> getLastTravelRoute() {
        return lastTravelRoute;
    }
    
    public void setLastTravelRoute(List<Integer> lastTravelRoute) {
        this.lastTravelRoute = lastTravelRoute;
    }
    
    public double getLastTravelDistance() {
        return lastTravelDistance;
    }
    
    public void setLastTravelDistance(double lastTravelDistance) {
        this.lastTravelDistance = lastTravelDistance;
    }
    
    public void setLastResult(String result) {
        this.lastResult = result;
    }
}
