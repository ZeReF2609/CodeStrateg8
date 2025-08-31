package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import com.algoritmoscurso.model.sorting.SortingModel;
import com.algoritmoscurso.model.sorting.algorithms.BubbleSort;
import com.algoritmoscurso.model.sorting.algorithms.InsertionSort;
import com.algoritmoscurso.model.sorting.algorithms.SelectionSort;
import com.algoritmoscurso.model.sorting.algorithms.QuickSort;
import com.algoritmoscurso.model.sorting.algorithms.MergeSort;
import com.algoritmoscurso.model.sorting.algorithms.HeapSort;
import com.algoritmoscurso.view.semana2.SortingView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controlador para los algoritmos de ordenación
 */
public class SortingController implements IController {
    private SortingModel model;
    private SortingView view;
    
    public SortingController(SortingModel model) {
        this.model = model;
        this.view = new SortingView();
        setupAlgorithms();
    }
    
    @Override
    public void initialize() {
        setupEventHandlers();
        updateAlgorithmsList();
        generateSampleData();
    updateView();
    // Asegurar que la vista tiene seleccionado el primer algoritmo
    view.selectFirstAlgorithm();
    // Actualizar la lista nuevamente por si la vista fue creada antes
    updateAlgorithmsList();
    }
    
    /**
     * Configura los algoritmos disponibles
     */
    private void setupAlgorithms() {
        // Registrar los algoritmos disponibles (idempotente: evitar duplicados)
        if (model.getAvailableAlgorithms().isEmpty()) {
            model.addAlgorithm(new BubbleSort());
            model.addAlgorithm(new InsertionSort());
            model.addAlgorithm(new SelectionSort());
            model.addAlgorithm(new QuickSort());
            model.addAlgorithm(new HeapSort());
            model.addAlgorithm(new MergeSort());
        }
    }
    
    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        view.setSortButtonListener(new SortButtonListener());
        view.setGenerateButtonListener(new GenerateButtonListener());
        view.setClearButtonListener(new ClearButtonListener());
    view.setAlgorithmSelectionListener(new AlgorithmSelectionListener());
    view.setCompareButtonListener(new CompareButtonListener());
    }
    
    /**
     * Actualiza la lista de algoritmos en la vista
     */
    private void updateAlgorithmsList() {
        String[] algorithmNames = model.getAvailableAlgorithms().stream()
                .map(ISortingAlgorithm::getName)
                .toArray(String[]::new);
    view.setAlgorithms(algorithmNames);
    }
    
    /**
     * Genera datos de muestra
     */
    private void generateSampleData() {
        model.generateRandomData(20, 100);
        updateInputDisplay();
    }
    
    /**
     * Actualiza la visualización de datos de entrada
     */
    private void updateInputDisplay() {
        int[] data = model.getOriginalData();
        view.setInputText(Arrays.toString(data));
    }
    
    /**
     * Actualiza la visualización de datos de salida
     */
    private void updateOutputDisplay() {
        int[] data = model.getSortedData();
        view.setOutputText(Arrays.toString(data));
        
        // Actualizar información de rendimiento
        updatePerformanceInfo();
    }
    
    /**
     * Actualiza la información de rendimiento
     */
    private void updatePerformanceInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Algoritmo: ").append(model.getSelectedAlgorithm()).append("\n");
        info.append("Tiempo de ejecución: ").append(model.getExecutionTime()).append(" nanosegundos\n");
        info.append("Tiempo en milisegundos: ").append(model.getExecutionTime() / 1_000_000.0).append(" ms\n");
        
        // Buscar el algoritmo seleccionado para mostrar su información
        String selectedName = model.getSelectedAlgorithm();
        ISortingAlgorithm selectedAlgorithm = model.getAvailableAlgorithms().stream()
                .filter(alg -> alg.getName().equals(selectedName))
                .findFirst()
                .orElse(null);
        
        if (selectedAlgorithm != null) {
            info.append("Complejidad temporal: ").append(selectedAlgorithm.getTimeComplexity()).append("\n");
            info.append("Complejidad espacial: ").append(selectedAlgorithm.getSpaceComplexity()).append("\n");
            info.append("Descripción: ").append(selectedAlgorithm.getDescription());
        }
        
        view.setTimeText(info.toString());
    }
    
    @Override
    public void handleEvent(String eventType, Object data) {
        switch (eventType) {
            case "SORT":
                performSort();
                break;
            case "GENERATE":
                generateRandomData();
                break;
            case "CLEAR":
                clearData();
                break;
            case "ALGORITHM_CHANGED":
                algorithmChanged();
                break;
            default:
                System.out.println("Evento no manejado en SortingController: " + eventType);
                break;
        }
    }
    
    /**
     * Realiza la ordenación con el algoritmo seleccionado
     */
    private void performSort() {
        try {
            String selectedAlgorithmName = view.getSelectedAlgorithm();
            if (selectedAlgorithmName == null) {
                view.showError("Por favor seleccione un algoritmo.");
                return;
            }
            
            // Procesar datos de entrada
            processInputData();
            
            if (!model.validate()) {
                view.showError("Por favor ingrese datos válidos para ordenar.");
                return;
            }
            
            // Buscar algoritmo seleccionado
            ISortingAlgorithm algorithm = model.getAvailableAlgorithms().stream()
                    .filter(alg -> alg.getName().equals(selectedAlgorithmName))
                    .findFirst()
                    .orElse(null);
            
            if (algorithm == null) {
                view.showError("Algoritmo no encontrado.");
                return;
            }
            
            // Ejecutar ordenación
            long startTime = System.nanoTime();
            int[] sortedData = algorithm.sort(model.getOriginalData());
            long endTime = System.nanoTime();
            
            // Actualizar modelo
            model.setSortedData(sortedData);
            model.setExecutionTime(endTime - startTime);
            model.setSelectedAlgorithm(selectedAlgorithmName);
            
            // Actualizar vista
            updateOutputDisplay();
            
        } catch (Exception e) {
            view.showError("Error durante la ordenación: " + e.getMessage());
        }
    }
    
    /**
     * Procesa los datos de entrada desde la vista
     */
    private void processInputData() {
        try {
            String inputText = view.getInputText().trim();
            
            if (inputText.isEmpty()) {
                return;
            }
            
            // Remover corchetes si existen
            inputText = inputText.replaceAll("[\\[\\]]", "");
            
            // Dividir por comas y convertir a enteros
            String[] parts = inputText.split(",");
            int[] data = new int[parts.length];
            
            for (int i = 0; i < parts.length; i++) {
                data[i] = Integer.parseInt(parts[i].trim());
            }
            
            model.setOriginalData(data);
            
        } catch (NumberFormatException e) {
            view.showError("Formato de datos incorrecto. Use números separados por comas.");
        }
    }
    
    /**
     * Genera nuevos datos aleatorios
     */
    private void generateRandomData() {
        model.generateRandomData(20, 100);
        updateInputDisplay();
        view.setOutputText("");
        view.setTimeText("");
    view.setComparisonText("");
    }
    
    /**
     * Limpia todos los datos
     */
    private void clearData() {
        model.clear();
        view.setInputText("");
        view.setOutputText("");
        view.setTimeText("");
        view.setComparisonText("");
    }

    // Ejecuta todos los algoritmos sobre los datos actuales y muestra comparativa
    private void compareAllAlgorithms() {
        try {
            processInputData();
            if (!model.validate()) {
                view.showError("Por favor ingrese o genere datos válidos para comparar.");
                return;
            }
            int[] data = model.getOriginalData();
            java.util.List<com.algoritmoscurso.model.sorting.SortingRunner.Result> results = com.algoritmoscurso.model.sorting.SortingRunner.runAll(model.getAvailableAlgorithms(), data);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Comparativa para %d elementos:\n", data.length));
            for (com.algoritmoscurso.model.sorting.SortingRunner.Result r : results) {
                sb.append(String.format("%s: %d ns -> %s\n", r.name, r.timeNs, Arrays.toString(Arrays.copyOf(r.sorted, Math.min(r.sorted.length, 10)))));
            }
            view.setComparisonText(sb.toString());
        } catch (Exception e) {
            view.showError("Error al comparar algoritmos: " + e.getMessage());
        }
    }
    
    /**
     * Maneja el cambio de algoritmo
     */
    private void algorithmChanged() {
        // Actualizar información del algoritmo seleccionado si es necesario
        String selectedAlgorithmName = view.getSelectedAlgorithm();
        if (selectedAlgorithmName != null) {
            model.setSelectedAlgorithm(selectedAlgorithmName);
        }
    }
    
    @Override
    public void updateModel(String action, Object... parameters) {
        // Implementar según necesidades específicas
    }
    
    @Override
    public void updateView() {
    view.updateView(model);
    }
    
    /**
     * Obtiene la vista
     */
    public SortingView getView() {
        return view;
    }

    /**
     * Fuerza la actualización de la lista de algoritmos en la vista.
     * Útil si la vista fue añadida al UI antes de inicializar completamente el controlador.
     */
    public void refreshAlgorithmsList() {
        updateAlgorithmsList();
    }
    
    // Listeners internos
    private class SortButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleEvent("SORT", null);
        }
    }
    
    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleEvent("GENERATE", null);
        }
    }
    
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleEvent("CLEAR", null);
        }
    }
    
    private class AlgorithmSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                handleEvent("ALGORITHM_CHANGED", null);
            }
        }
    }

    private class CompareButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            compareAllAlgorithms();
        }
    }
}
