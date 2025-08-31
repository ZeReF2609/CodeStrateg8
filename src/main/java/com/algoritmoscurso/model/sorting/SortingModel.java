package com.algoritmoscurso.model.sorting;

import com.algoritmoscurso.interfaces.IModel;
import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SortingModel implements IModel {
    private int[] originalData;
    private int[] sortedData;
    private long executionTime;
    private String selectedAlgorithm;
    private List<ISortingAlgorithm> availableAlgorithms;
    private String description;

    public SortingModel() {
        availableAlgorithms = new ArrayList<>();
        description = "Parte 2: Implementar los métodos de ordenación...";
        initialize();
    }

    @Override
    public void initialize() {
        originalData = new int[0];
        sortedData = new int[0];
        executionTime = 0;
        selectedAlgorithm = "";
    }

    @Override
    public boolean validate() {
        return originalData != null && originalData.length > 0;
    }

    @Override
    public void clear() {
        originalData = new int[0];
        sortedData = new int[0];
        executionTime = 0;
    }

    public void setOriginalData(int[] data) {
        this.originalData = Arrays.copyOf(data, data.length);
    }

    public void setSortedData(int[] data) {
        this.sortedData = Arrays.copyOf(data, data.length);
    }

    public void setExecutionTime(long time) {
        this.executionTime = time;
    }

    public void setSelectedAlgorithm(String algorithm) {
        this.selectedAlgorithm = algorithm;
    }

    public void generateRandomData(int size, int maxValue) {
        Random random = new Random();
        originalData = new int[size];
        for (int i = 0; i < size; i++) {
            originalData[i] = random.nextInt(maxValue) + 1;
        }
    }

    public int[] getOriginalData() {
        return Arrays.copyOf(originalData, originalData.length);
    }

    public int[] getSortedData() {
        return Arrays.copyOf(sortedData, sortedData.length);
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public String getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    public List<ISortingAlgorithm> getAvailableAlgorithms() {
        return new ArrayList<>(availableAlgorithms);
    }

    public void addAlgorithm(ISortingAlgorithm algorithm) {
        availableAlgorithms.add(algorithm);
    }

    public String getDescription() { return description; }
    public void setDescription(String d) { description = d; }

    @Override
    public String toString() {
        return String.format("Datos originales: %s%nDatos ordenados: %s%nTiempo: %d ns%nAlgoritmo: %s",
                Arrays.toString(originalData), Arrays.toString(sortedData), executionTime, selectedAlgorithm);
    }
}
