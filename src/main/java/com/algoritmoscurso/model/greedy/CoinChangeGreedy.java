package com.algoritmoscurso.model.greedy;

import com.algoritmoscurso.interfaces.ICoinChange;
import com.algoritmoscurso.interfaces.IGreedyAlgorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementación del algoritmo voraz para el cambio de moneda
 */
public class CoinChangeGreedy implements ICoinChange, IGreedyAlgorithm {
    private int amount;
    private int[] coins;
    private List<Integer> result;
    
    public CoinChangeGreedy() {
        this.result = new ArrayList<>();
    }
    
    public CoinChangeGreedy(int amount, int[] coins) {
        this.amount = amount;
        this.coins = coins.clone();
        this.result = new ArrayList<>();
        // Ordenar monedas de mayor a menor para algoritmo voraz
        Arrays.sort(this.coins);
        for (int i = 0; i < this.coins.length / 2; i++) {
            int temp = this.coins[i];
            this.coins[i] = this.coins[this.coins.length - 1 - i];
            this.coins[this.coins.length - 1 - i] = temp;
        }
    }
    
    @Override
    public List<Integer> makeChange(int amount, int[] coins) {
        this.amount = amount;
        this.coins = coins.clone();
        this.result = new ArrayList<>();
        
        // Ordenar monedas de mayor a menor
        Arrays.sort(this.coins);
        for (int i = 0; i < this.coins.length / 2; i++) {
            int temp = this.coins[i];
            this.coins[i] = this.coins[this.coins.length - 1 - i];
            this.coins[this.coins.length - 1 - i] = temp;
        }
        
        int remainingAmount = amount;
        
        for (int coin : this.coins) {
            while (remainingAmount >= coin) {
                this.result.add(coin);
                remainingAmount -= coin;
            }
        }
        
        return new ArrayList<>(this.result);
    }
    
    @Override
    public int getMinCoins(int amount, int[] coins) {
        List<Integer> change = makeChange(amount, coins);
        return change.size();
    }
    
    @Override
    public Object execute() {
        if (coins != null && amount > 0) {
            return makeChange(amount, coins);
        }
        return new ArrayList<>();
    }
    
    @Override
    public String getDescription() {
        return "Algoritmo voraz para el cambio de moneda. " +
               "Selecciona siempre la moneda de mayor valor posible " +
               "hasta completar el monto deseado.";
    }
    
    @Override
    public String getName() {
        return "Cambio de Moneda (Greedy)";
    }
    
    // Getters y setters
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public int[] getCoins() {
        return coins != null ? coins.clone() : null;
    }
    
    public void setCoins(int[] coins) {
        this.coins = coins != null ? coins.clone() : null;
    }
    
    public List<Integer> getResult() {
        return new ArrayList<>(result);
    }
}
