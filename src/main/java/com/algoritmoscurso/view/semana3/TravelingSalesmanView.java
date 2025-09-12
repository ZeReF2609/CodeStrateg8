package com.algoritmoscurso.view.semana3;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Vista simple para el Problema del Agente Viajero (TSP)
 */
public class TravelingSalesmanView extends JPanel implements IView {
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    
    // Componentes específicos para TSP
    private JTextArea distanceMatrixArea;
    private JTextField startCityField;
    private JButton executeButton;
    private JTextArea descriptionArea;

    public TravelingSalesmanView() {
        initializeView();
    }
    
    @Override
    public void initializeView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setupComponents();
        setupLayout();
        setupEventListeners();
        showWelcomeMessage();
    }
    
    private void setupComponents() {
        // Área de descripción
        descriptionArea = new JTextArea(3, 60);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(250, 250, 250));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        // Campos de entrada
        distanceMatrixArea = new JTextArea("0,10,15,20;10,0,35,25;15,35,0,30;20,25,30,0", 4, 40);
        distanceMatrixArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        distanceMatrixArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        startCityField = new JTextField("0", 10);
        
        // Botón de ejecución
        executeButton = new JButton("Ejecutar");
        styleButton(executeButton, new Color(33, 103, 178));
        
        // Área de resultados
        resultArea = new JTextArea(15, 60);
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        resultArea.setBackground(new Color(252, 252, 252));
        resultArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        
        scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));
    }
    
    private void setupLayout() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de descripción
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(Color.WHITE);
        JLabel descLabel = new JLabel("Descripción");
        descLabel.setForeground(new Color(33, 103, 178));
        descLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        descPanel.add(descLabel, BorderLayout.NORTH);
        descPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        
        // Panel de configuración
        JPanel configPanel = new JPanel(new BorderLayout(10, 10));
        configPanel.setBackground(Color.WHITE);
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración"));
        
        // Subpanel para matriz de distancias
        JPanel matrixPanel = new JPanel(new BorderLayout());
        matrixPanel.setBackground(Color.WHITE);
        JLabel matrixLabel = new JLabel("Matriz de Distancias (formato: fila1;fila2;fila3;...):");
        matrixLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        matrixPanel.add(matrixLabel, BorderLayout.NORTH);
        matrixPanel.add(new JScrollPane(distanceMatrixArea), BorderLayout.CENTER);
        
        // Subpanel para controles
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.setBackground(Color.WHITE);
        controlsPanel.add(new JLabel("Ciudad de inicio:"));
        controlsPanel.add(startCityField);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(executeButton);
        
        configPanel.add(matrixPanel, BorderLayout.CENTER);
        configPanel.add(controlsPanel, BorderLayout.SOUTH);
        
        // Panel central que combina configuración y resultados
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(configPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(descPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupEventListeners() {
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeTSPAlgorithm();
            }
        });
    }
    
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
    }
    
    private void showWelcomeMessage() {
        descriptionArea.setText("Problema del Agente Viajero (TSP)\n\n" +
                              "Este algoritmo voraz encuentra una ruta que visite todas las ciudades " +
                              "exactamente una vez y regrese al punto de origen, minimizando la distancia total. " +
                              "Utiliza una estrategia voraz seleccionando siempre la ciudad más cercana no visitada.");
        
        resultArea.setText("Agente Viajero - Algoritmo Voraz\n\n" +
                          "Instrucciones:\n" +
                          "1. Ingrese la matriz de distancias entre ciudades\n" +
                          "2. Especifique la ciudad de inicio (índice desde 0)\n" +
                          "3. Haga clic en 'Ejecutar' para ver el resultado\n\n" +
                          "Ejemplo por defecto: 4 ciudades con ciudad de inicio 0\n");
    }
    
    private void executeTSPAlgorithm() {
        try {
            double[][] distances = parseDistanceMatrix();
            int startCity = Integer.parseInt(startCityField.getText().trim());
            
            if (startCity < 0 || startCity >= distances.length) {
                showError("Error: La ciudad de inicio debe estar entre 0 y " + (distances.length - 1));
                return;
            }
            
            // Ejecutar algoritmo voraz del agente viajero
            TSPResult result = solveTSP(distances, startCity);
            
            // Mostrar resultados
            displayResults(distances, startCity, result);
            
        } catch (NumberFormatException ex) {
            showError("Error: Por favor ingrese números válidos");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }
    
    private double[][] parseDistanceMatrix() throws Exception {
        String matrixText = distanceMatrixArea.getText().trim();
        String[] rows = matrixText.split(";");
        
        if (rows.length == 0) {
            throw new Exception("La matriz de distancias no puede estar vacía");
        }
        
        double[][] matrix = new double[rows.length][];
        
        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split(",");
            if (values.length != rows.length) {
                throw new Exception("La matriz debe ser cuadrada");
            }
            
            matrix[i] = new double[values.length];
            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Double.parseDouble(values[j].trim());
            }
        }
        
        return matrix;
    }
    
    /**
     * Algoritmo voraz para el problema del agente viajero
     */
    private TSPResult solveTSP(double[][] distances, int startCity) {
        int numCities = distances.length;
        List<Integer> route = new ArrayList<>();
        boolean[] visited = new boolean[numCities];
        
        int currentCity = startCity;
        route.add(currentCity);
        visited[currentCity] = true;
        
        double totalDistance = 0;
        
        // Visitar ciudades usando estrategia voraz (ciudad más cercana)
        for (int i = 1; i < numCities; i++) {
            double minDistance = Double.MAX_VALUE;
            int nextCity = -1;
            
            for (int j = 0; j < numCities; j++) {
                if (!visited[j] && distances[currentCity][j] < minDistance) {
                    minDistance = distances[currentCity][j];
                    nextCity = j;
                }
            }
            
            if (nextCity != -1) {
                route.add(nextCity);
                visited[nextCity] = true;
                totalDistance += minDistance;
                currentCity = nextCity;
            }
        }
        
        // Regresar a la ciudad de origen
        totalDistance += distances[currentCity][startCity];
        route.add(startCity);
        
        return new TSPResult(route, totalDistance);
    }
    
    private void displayResults(double[][] distances, int startCity, TSPResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADOS DEL AGENTE VIAJERO ===\n\n");
        sb.append("Número de ciudades: ").append(distances.length).append("\n");
        sb.append("Ciudad de inicio: ").append(startCity).append("\n\n");
        
        sb.append("MATRIZ DE DISTANCIAS:\n");
        for (int i = 0; i < distances.length; i++) {
            sb.append("Ciudad ").append(i).append(": ");
            for (int j = 0; j < distances[i].length; j++) {
                sb.append(String.format("%6.1f", distances[i][j]));
                if (j < distances[i].length - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        
        sb.append("SOLUCIÓN ENCONTRADA:\n");
        sb.append("Ruta: ").append(result.route).append("\n");
        sb.append("Distancia total: ").append(String.format("%.2f", result.totalDistance)).append("\n\n");
        
        sb.append("DESGLOSE DEL RECORRIDO:\n");
        for (int i = 0; i < result.route.size() - 1; i++) {
            int from = result.route.get(i);
            int to = result.route.get(i + 1);
            double distance = distances[from][to];
            sb.append("- Ciudad ").append(from).append(" → Ciudad ").append(to)
              .append(" (distancia: ").append(String.format("%.2f", distance)).append(")\n");
        }
        
        sb.append("\nCARACTERÍSTICAS:\n");
        sb.append("• Algoritmo: Greedy (ciudad más cercana)\n");
        sb.append("• Complejidad temporal: O(n²) donde n = número de ciudades\n");
        sb.append("• Complejidad espacial: O(n)\n");
        sb.append("• Nota: No garantiza la solución óptima global\n");
        
        resultArea.setText(sb.toString());
    }
    
    /**
     * Clase auxiliar para resultados del TSP
     */
    private static class TSPResult {
        List<Integer> route;
        double totalDistance;
        
        TSPResult(List<Integer> route, double totalDistance) {
            this.route = route;
            this.totalDistance = totalDistance;
        }
    }
    
    @Override
    public void updateView(Object data) {
        if (data != null) {
            resultArea.setText(data.toString());
        }
    }
    
    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void clear() {
        resultArea.setText("");
    }
    
    public void setDistanceMatrix(double[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
                if (j < matrix[i].length - 1) sb.append(",");
            }
            if (i < matrix.length - 1) sb.append(";");
        }
        distanceMatrixArea.setText(sb.toString());
    }
    
    public void setStartCity(int startCity) {
        startCityField.setText(String.valueOf(startCity));
    }
    
    /**
     * Establece la descripción de la actividad
     */
    public void setDescription(String text) {
        if (descriptionArea != null) {
            descriptionArea.setText(text);
            descriptionArea.setCaretPosition(0);
        }
    }
}
