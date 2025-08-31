package com.algoritmoscurso.model.bigo;

import com.algoritmoscurso.interfaces.IModel;
import com.algoritmoscurso.interfaces.IBigOExample;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo para los ejemplos de análisis de complejidad Big O
 * Gestiona la colección de ejemplos y sus datos
 */
public class BigOModel implements IModel {
    private List<IBigOExample> examples;
    private IBigOExample selectedExample;
    private Object lastExecutionResult;
    private long lastExecutionTime;
    // Descripción de la actividad (Parte 1)
    private String description;

    public BigOModel() {
        examples = new ArrayList<>();
        // establecer descripción por defecto para la actividad
        description = "10 ejemplos con bucles y condicionales. Mostrar el cálculo de la complejidad (notación Big O) para cada ejemplo.";
        initialize();
    }

    @Override
    public void initialize() {
        examples.clear();
        selectedExample = null;
        lastExecutionResult = null;
        lastExecutionTime = 0;
        // Registrar ejemplos concretos para la actividad Big O
        examples.addAll(new BigOExamples().getExamples());
    }

    @Override
    public boolean validate() {
        return examples != null && !examples.isEmpty();
    }

    @Override
    public void clear() {
        selectedExample = null;
        lastExecutionResult = null;
        lastExecutionTime = 0;
    }

    /**
     * Agrega un ejemplo a la colección
     * @param example ejemplo a agregar
     */
    public void addExample(IBigOExample example) {
        examples.add(example);
    }

    /**
     * Selecciona un ejemplo por índice
     * @param index índice del ejemplo
     */
    public void selectExample(int index) {
        if (index >= 0 && index < examples.size()) {
            selectedExample = examples.get(index);
        }
    }

    /**
     * Ejecuta el ejemplo seleccionado
     * @param input parámetro de entrada
     * @return resultado de la ejecución
     */
    public Object executeSelectedExample(Object input) {
        if (selectedExample != null) {
            long startTime = System.nanoTime();
            lastExecutionResult = selectedExample.execute(input);
            lastExecutionTime = System.nanoTime() - startTime;
            return lastExecutionResult;
        }
        return null;
    }

    // Getters
    public List<IBigOExample> getExamples() {
        return new ArrayList<>(examples);
    }

    public IBigOExample getSelectedExample() {
        return selectedExample;
    }

    public Object getLastExecutionResult() {
        return lastExecutionResult;
    }

    public long getLastExecutionTime() {
        return lastExecutionTime;
    }

    /**
     * Obtiene los nombres de todos los ejemplos
     * @return array con los nombres
     */
    public String[] getExampleNames() {
        return examples.stream()
                .map(IBigOExample::getName)
                .toArray(String[]::new);
    }

    @Override
    public String toString() {
        return String.format("Total ejemplos: %d%nEjemplo seleccionado: %s%nÚltimo resultado: %s%nÚltimo tiempo: %d ns",
                examples.size(),
                selectedExample != null ? selectedExample.getName() : "Ninguno",
                lastExecutionResult,
                lastExecutionTime);
    }

    /**
     * Descripción textual de la actividad de Big O (Parte 1)
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
