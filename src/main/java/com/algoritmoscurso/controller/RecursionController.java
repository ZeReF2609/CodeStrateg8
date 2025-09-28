package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.view.semana4.RecursionView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador simplificado para algoritmos recursivos - Semana 4
 */
public class RecursionController implements IController {
    private RecursionView view;

    public RecursionController() {
        this.view = new RecursionView();
        initialize();
    }

    @Override
    public void initialize() {
        setupEventListeners();
    }

    private void setupEventListeners() {
        // Listener para QuickSort
        view.setQuickSortListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeQuickSort();
            }
        });

        // Listener para Búsqueda Binaria
        view.setBinarySearchListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeBinarySearch();
            }
        });

        // Listener para limpiar resultados
        view.setClearListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResults();
            }
        });
    }

    private void executeQuickSort() {
        try {
            String input = view.getInputArray();
            if (input.isEmpty()) {
                view.showError("Por favor ingrese un array para ordenar");
                return;
            }

            // Parsear el array
            int[] array = parseArrayFromString(input);
            if (array.length == 0) {
                view.showError("Array inválido. Use formato: 1, 2, 3, 4");
                return;
            }

            view.showMessage("Ejecutando QuickSort recursivo...");

            // Crear copia para mostrar el original y snapshots para animación
            int[] original = array.clone();
            java.util.List<String> steps = new ArrayList<>();
            java.util.List<com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot> snaps = new java.util.ArrayList<>();

            // capture initial snapshot
            snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
                original.clone(), 0, original.length - 1, -1, -1, -1, -1, "Inicio"));

            // Ejecutar QuickSort con seguimiento de pasos que genera snapshots
            quickSortWithSnapshots(array, 0, array.length - 1, steps, 0, snaps);

            // Mostrar resultado
            StringBuilder result = new StringBuilder();
            result.append("Array original: ").append(Arrays.toString(original)).append("\n");
            result.append("Array ordenado: ").append(Arrays.toString(array)).append("\n");
            result.append("Pasos ejecutados: ").append(steps.size()).append("\n\n");
            result.append("Pasos detallados:\n");
            
            for (int i = 0; i < Math.min(steps.size(), 20); i++) {
                result.append(i + 1).append(". ").append(steps.get(i)).append("\n");
            }
            
            if (steps.size() > 20) {
                result.append("... (").append(steps.size() - 20).append(" pasos más)\n");
            }

            view.displayResult("QuickSort Recursivo", result.toString());
            view.showMessage("QuickSort completado exitosamente");

            // Animar snapshots (en background)
            view.getRecursionVisualizer().animateSnapshots(snaps, 220);

        } catch (Exception ex) {
            view.showError("Error ejecutando QuickSort: " + ex.getMessage());
        }
    }

    private void executeBinarySearch() {
        try {
            String input = view.getSearchArray();
            int searchValue = view.getSearchValue();
            
            if (input.isEmpty()) {
                view.showError("Por favor ingrese un array ordenado para buscar");
                return;
            }

            // Parsear el array
            int[] array = parseArrayFromString(input);
            if (array.length == 0) {
                view.showError("Array inválido. Use formato: 1, 2, 3, 4");
                return;
            }

            view.showMessage("Ejecutando Búsqueda Binaria recursiva...");

            List<String> steps = new ArrayList<>();
            java.util.List<com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot> snaps = new java.util.ArrayList<>();

            int result = binarySearchWithSnapshots(array, 0, array.length - 1, searchValue, steps, 0, snaps);

            // Mostrar resultado
            StringBuilder output = new StringBuilder();
            output.append("Array: ").append(Arrays.toString(array)).append("\n");
            output.append("Valor buscado: ").append(searchValue).append("\n");
            
            if (result != -1) {
                output.append("Resultado: ENCONTRADO en posición ").append(result).append("\n");
            } else {
                output.append("Resultado: NO ENCONTRADO\n");
            }
            
            output.append("Comparaciones realizadas: ").append(steps.size()).append("\n\n");
            output.append("Pasos detallados:\n");
            
            for (int i = 0; i < steps.size(); i++) {
                output.append(i + 1).append(". ").append(steps.get(i)).append("\n");
            }

            view.displayResult("Búsqueda Binaria Recursiva", output.toString());
            view.showMessage("Búsqueda binaria completada");

            view.getRecursionVisualizer().animateSnapshots(snaps, 220);

        } catch (Exception ex) {
            view.showError("Error ejecutando Búsqueda Binaria: " + ex.getMessage());
        }
    }

    private void clearResults() {
        view.clearResults();
        view.showMessage("Resultados limpiados");
    }

    // Implementación de QuickSort recursivo con seguimiento
    private void quickSortWithSnapshots(int[] arr, int low, int high, List<String> steps, int depth,
                                         java.util.List<com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot> snaps) {
        if (low < high) {
            String indent = indent(depth);
            steps.add(indent + "QuickSort(" + low + ", " + high + ") - Rango: " + Arrays.toString(Arrays.copyOfRange(arr, low, high + 1)));
            // snapshot before partition
            snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
                arr.clone(), low, high, -1, -1, -1, -1, "Entrada QuickSort(" + low + "," + high + ")"));

            int pi = partitionWithSnapshots(arr, low, high, steps, depth + 1, snaps);
            steps.add(indent + "Pivote en posición " + pi + ", valor: " + arr[pi]);
            snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
                arr.clone(), low, high, pi, -1, -1, -1, "Pivote: " + arr[pi]));

            quickSortWithSnapshots(arr, low, pi - 1, steps, depth + 1, snaps);
            quickSortWithSnapshots(arr, pi + 1, high, steps, depth + 1, snaps);
        }
    }

    private int partitionWithSnapshots(int[] arr, int low, int high, List<String> steps, int depth,
                                       java.util.List<com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot> snaps) {
        String indent = indent(depth);
        int pivot = arr[high];
        steps.add(indent + "Seleccionar pivote: " + pivot + " (posición " + high + ")");
        snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
            arr.clone(), low, high, high, -1, -1, -1, "Seleccionar pivote: " + pivot));

        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
                    arr.clone(), low, high, high, -1, -1, -1, "Intercambiar positions " + i + " y " + j));
                if (i != j) {
                    steps.add(indent + "Intercambiar " + arr[j] + " y " + arr[i]);
                }
            }
        }

        swap(arr, i + 1, high);
        steps.add(indent + "Colocar pivote en posición final: " + (i + 1));
        snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
            arr.clone(), low, high, i + 1, -1, -1, -1, "Pivote en posición " + (i + 1)));

        return i + 1;
    }

    // (removed old partition - replaced with partitionWithSnapshots above)

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Implementación de Búsqueda Binaria recursiva con seguimiento
    private int binarySearchWithSnapshots(int[] arr, int left, int right, int target, List<String> steps, int depth,
                                          java.util.List<com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot> snaps) {
        String indent = indent(depth);

        if (right >= left) {
            int mid = left + (right - left) / 2;
            steps.add(indent + "Buscar en [" + left + ".." + right + "], medio=" + mid + ", valor=" + arr[mid]);
            snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
                arr.clone(), left, right, -1, left, right, mid, "Examinar medio=" + mid));

            if (arr[mid] == target) {
                steps.add(indent + "¡Encontrado! " + target + " está en posición " + mid);
                return mid;
            }

            if (arr[mid] > target) {
                steps.add(indent + arr[mid] + " > " + target + ", buscar en mitad izquierda");
                return binarySearchWithSnapshots(arr, left, mid - 1, target, steps, depth + 1, snaps);
            }

            steps.add(indent + arr[mid] + " < " + target + ", buscar en mitad derecha");
            return binarySearchWithSnapshots(arr, mid + 1, right, target, steps, depth + 1, snaps);
        }

        steps.add(indent + "No encontrado - rango agotado");
        snaps.add(new com.algoritmoscurso.view.semana4.components.RecursionVisualizer.Snapshot(
            arr.clone(), left, right, -1, left, right, -1, "Rango agotado"));
        return -1;
    }

    private int[] parseArrayFromString(String input) {
        try {
            String[] parts = input.split(",");
            int[] array = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                array[i] = Integer.parseInt(parts[i].trim());
            }
            return array;
        } catch (NumberFormatException e) {
            return new int[0];
        }
    }

    // Helper para generar indentación compatible con Java 8
    private String indent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    @Override
    public void handleEvent(String eventType, Object data) {
        switch (eventType) {
            case "QUICKSORT":
                executeQuickSort();
                break;
            case "BINARY_SEARCH":
                executeBinarySearch();
                break;
            case "CLEAR":
                clearResults();
                break;
            default:
                view.showError("Evento no reconocido: " + eventType);
        }
    }

    @Override
    public void updateModel(String action, Object... parameters) {
        // Los algoritmos recursivos son funciones puras, no necesitan modelo persistente
        view.showMessage("Modelo actualizado: " + action);
    }

    @Override
    public void updateView() {
        view.showMessage("Vista de recursión actualizada");
    }

    /**
     * Obtiene la vista asociada a este controlador.
     * @return panel de la vista
     */
    public JPanel getView() {
        return view;
    }
}