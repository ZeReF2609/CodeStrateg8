package com.algoritmoscurso.view.semana4;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.algoritmoscurso.view.semana4.components.RecursionVisualizer;

/**
 * Vista simplificada para algoritmos recursivos - Semana 4
 */
public class RecursionView extends JPanel implements IView {
    
    // Componentes para QuickSort recursivo
    private JTextArea inputArrayArea;
    private JButton quickSortButton;
    
    // Componentes para Búsqueda Binaria recursiva
    private JTextArea searchArrayArea;
    private JSpinner searchValueSpinner;
    private JButton binarySearchButton;
    
    // Área de resultados
    private JTextArea resultArea;
    private JButton clearButton;
    private JLabel statusLabel;
    // Visualizador para animaciones de recursión
    private RecursionVisualizer recursionVisualizer;

    public RecursionView() {
        initializeView();
    }

    @Override
    public void initializeView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setupComponents();
        setupLayout();
    }

    private void setupComponents() {
        // Componentes para QuickSort
        inputArrayArea = new JTextArea(3, 30);
        inputArrayArea.setText("64, 34, 25, 12, 22, 11, 90");
        inputArrayArea.setBorder(BorderFactory.createTitledBorder("Array para ordenar"));
        
        quickSortButton = new JButton("Ejecutar QuickSort Recursivo");
        
        // Componentes para Búsqueda Binaria
        searchArrayArea = new JTextArea(3, 30);
        searchArrayArea.setText("2, 3, 4, 10, 40, 50, 60, 70, 80, 90");
        searchArrayArea.setBorder(BorderFactory.createTitledBorder("Array ordenado para buscar"));
        
        searchValueSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
        binarySearchButton = new JButton("Ejecutar Búsqueda Binaria Recursiva");
        
        // Botón de control
        clearButton = new JButton("Limpiar");
        
        // Área de resultados
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        statusLabel = new JLabel("Listo para ejecutar algoritmos recursivos");
    recursionVisualizer = new RecursionVisualizer();
        
        // Estilos
        styleButtons();
    }

    private void styleButtons() {
        Color primaryColor = new Color(33, 103, 178);
        Color clearColor = Color.GRAY;
        
        styleButton(quickSortButton, primaryColor);
        styleButton(binarySearchButton, primaryColor);
        styleButton(clearButton, clearColor);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupLayout() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña 1: QuickSort Recursivo
        JPanel quickSortPanel = createQuickSortPanel();
        tabbedPane.addTab("QuickSort Recursivo", quickSortPanel);
        
        // Pestaña 2: Búsqueda Binaria Recursiva
        JPanel binarySearchPanel = createBinarySearchPanel();
        tabbedPane.addTab("Busqueda Binaria Recursiva", binarySearchPanel);
        
        // Pestaña 3: Resultados
        JPanel resultsPanel = createResultsPanel();
        tabbedPane.addTab("Resultados", resultsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private JPanel createQuickSortPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel de entrada
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JScrollPane(inputArrayArea), BorderLayout.CENTER);
        
        // Panel de botón
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(quickSortButton);
        
        // Panel de descripción
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder("Descripción"));
        
        JTextArea descArea = new JTextArea(
            "QuickSort es un algoritmo de ordenamiento recursivo que utiliza la estrategia " +
            "divide y vencerás. Selecciona un elemento como pivote y particiona el array " +
            "alrededor del pivote, luego ordena recursivamente las sub-arrays.\n\n" +
            "Complejidad promedio: O(n log n)\n" +
            "Complejidad peor caso: O(n²)\n" +
            "Espacio: O(log n)"
        );
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(new Color(248, 248, 248));
        
        descPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);
        
    panel.add(inputPanel, BorderLayout.NORTH);
    panel.add(buttonPanel, BorderLayout.CENTER);
    panel.add(descPanel, BorderLayout.SOUTH);
    // Visualizador en la parte inferior
    JPanel visWrap = new JPanel(new BorderLayout());
    visWrap.setBorder(BorderFactory.createTitledBorder("Visualización - QuickSort"));
    visWrap.add(recursionVisualizer, BorderLayout.CENTER);
    panel.add(visWrap, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createBinarySearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel de entrada
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JScrollPane(searchArrayArea), BorderLayout.CENTER);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Valor a buscar:"));
        searchPanel.add(searchValueSpinner);
        searchPanel.add(binarySearchButton);
        
        // Panel de descripción
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder("Descripción"));
        
        JTextArea descArea = new JTextArea(
            "La búsqueda binaria recursiva es un algoritmo eficiente para encontrar un " +
            "elemento específico en un array ordenado. Divide el array por la mitad " +
            "y busca recursivamente en la mitad apropiada.\n\n" +
            "Complejidad: O(log n)\n" +
            "Espacio: O(log n) debido a las llamadas recursivas\n" +
            "Requiere: Array previamente ordenado"
        );
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(new Color(248, 248, 248));
        
        descPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);
        
    panel.add(inputPanel, BorderLayout.NORTH);
    panel.add(searchPanel, BorderLayout.CENTER);
    panel.add(descPanel, BorderLayout.SOUTH);
    JPanel visWrap = new JPanel(new BorderLayout());
    visWrap.setBorder(BorderFactory.createTitledBorder("Visualización - Búsqueda Binaria"));
    visWrap.add(recursionVisualizer, BorderLayout.CENTER);
    panel.add(visWrap, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel de control
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(clearButton);
        
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        
        return panel;
    }

    // Métodos públicos para el controlador
    
    public void setQuickSortListener(ActionListener listener) {
        quickSortButton.addActionListener(listener);
    }
    
    public void setBinarySearchListener(ActionListener listener) {
        binarySearchButton.addActionListener(listener);
    }
    
    public void setClearListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }
    
    public String getInputArray() {
        return inputArrayArea.getText().trim();
    }
    
    public String getSearchArray() {
        return searchArrayArea.getText().trim();
    }
    
    public int getSearchValue() {
        return (Integer) searchValueSpinner.getValue();
    }
    
    public void displayResult(String algorithmName, String result) {
        StringBuilder output = new StringBuilder();
        output.append("=== ").append(algorithmName.toUpperCase()).append(" ===\n");
        output.append(result).append("\n\n");
        
        resultArea.append(output.toString());
        resultArea.setCaretPosition(resultArea.getDocument().getLength());
    }
    
    public void clearResults() {
        resultArea.setText("");
        recursionVisualizer.setArray(new int[0]);
    }

    public RecursionVisualizer getRecursionVisualizer() {
        return recursionVisualizer;
    }
    
    public void showMessage(String message) {
        statusLabel.setText("[OK] " + message);
        statusLabel.setForeground(new Color(0, 128, 0));
    }
    
    public void showError(String error) {
        statusLabel.setText("[ERROR] " + error);
        statusLabel.setForeground(Color.RED);
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    @Override
    public void updateView(Object data) {
        if (data instanceof String) {
            displayResult("Algoritmo Recursivo", (String) data);
        }
    }
}