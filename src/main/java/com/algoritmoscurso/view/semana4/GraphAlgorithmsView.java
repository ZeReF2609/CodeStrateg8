package com.algoritmoscurso.view.semana4;

import com.algoritmoscurso.interfaces.IView;
import com.algoritmoscurso.model.graph.GraphModel;
import com.algoritmoscurso.model.graph.GraphResult;
import com.algoritmoscurso.view.semana4.components.GraphVisualizer;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Vista dinámica para algoritmos de grafos - Semana 4
 */
public class GraphAlgorithmsView extends JPanel implements IView {
    
    // Componentes principales
    private JSpinner graphSizeSpinner;
    private JButton createGraphButton;
    private JButton loadSampleButton;
    
    // Panel de conexiones
    private JSpinner fromVertexSpinner;
    private JSpinner toVertexSpinner;
    private JSpinner weightSpinner;
    private JButton addConnectionButton;
    private JButton removeConnectionButton;
    
    // Panel de algoritmos
    private JList<String> algorithmList;
    private DefaultListModel<String> algorithmListModel;
    private JTextArea algorithmDescriptionArea;
    private JSpinner startVertexSpinner;
    private JButton executeButton;
    private JButton clearButton;
    
    // Panel de visualización del grafo
    private JTable adjacencyTable;
    private DefaultTableModel tableModel;
    private GraphVisualizer graphVisualizer;
    
    // Panel de resultados
    private JTextArea resultArea;
    private JTextArea stepsArea;
    private JLabel statusLabel;

    public GraphAlgorithmsView() {
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
        // Componentes de creación de grafo
        graphSizeSpinner = new JSpinner(new SpinnerNumberModel(6, 2, 26, 1));
        createGraphButton = new JButton("Crear Grafo");
        loadSampleButton = new JButton("Cargar Muestra");
        
        // Componentes de conexiones
        fromVertexSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 25, 1));
        toVertexSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 25, 1));
        weightSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        addConnectionButton = new JButton("Agregar");
        removeConnectionButton = new JButton("Remover");
        
        // Lista de algoritmos
        algorithmListModel = new DefaultListModel<>();
        algorithmList = new JList<>(algorithmListModel);
        algorithmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        algorithmList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Descripción del algoritmo
        algorithmDescriptionArea = new JTextArea(3, 30);
        algorithmDescriptionArea.setEditable(false);
        algorithmDescriptionArea.setLineWrap(true);
        algorithmDescriptionArea.setWrapStyleWord(true);
        algorithmDescriptionArea.setBackground(new Color(248, 248, 248));
        
        // Vértice de inicio
        startVertexSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 25, 1));
        executeButton = new JButton("Ejecutar");
        clearButton = new JButton("Limpiar");
        
        // Tabla de adyacencia
        tableModel = new DefaultTableModel();
        adjacencyTable = new JTable(tableModel);
        adjacencyTable.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        // Visualizador gráfico
        graphVisualizer = new GraphVisualizer();
        
        // Áreas de resultado
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        stepsArea = new JTextArea();
        stepsArea.setEditable(false);
        stepsArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        
        statusLabel = new JLabel("Listo para comenzar");
        
        // Estilos
        styleButtons();
    }

    private void styleButtons() {
        Color primaryColor = new Color(33, 103, 178);
        Color secondaryColor = new Color(60, 179, 113);
        Color warningColor = new Color(255, 140, 0);
        
        styleButton(createGraphButton, primaryColor);
        styleButton(loadSampleButton, secondaryColor);
        styleButton(addConnectionButton, primaryColor);
        styleButton(removeConnectionButton, warningColor);
        styleButton(executeButton, primaryColor);
        styleButton(clearButton, Color.GRAY);
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
        // Panel principal con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña 1: Configuración del Grafo
        JPanel graphConfigPanel = createGraphConfigPanel();
        tabbedPane.addTab("Configuracion", graphConfigPanel);
        
        // Pestaña 2: Algoritmos y Resultados
        JPanel algorithmsPanel = createAlgorithmsPanel();
        tabbedPane.addTab("Algoritmos", algorithmsPanel);
        
        // Pestaña 3: Visualización
        JPanel visualizationPanel = createVisualizationPanel();
        tabbedPane.addTab("Visualizacion", visualizationPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private JPanel createGraphConfigPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel superior: Creación de grafo
        JPanel creationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        creationPanel.setBorder(BorderFactory.createTitledBorder("Crear Grafo"));
        creationPanel.add(new JLabel("Tamaño:"));
        creationPanel.add(graphSizeSpinner);
        creationPanel.add(createGraphButton);
        creationPanel.add(loadSampleButton);
        
        // Panel central: Conexiones
        JPanel connectionsPanel = new JPanel(new GridBagLayout());
        connectionsPanel.setBorder(BorderFactory.createTitledBorder("Gestionar Conexiones"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        connectionsPanel.add(new JLabel("Desde:"), gbc);
        gbc.gridx = 1;
        connectionsPanel.add(fromVertexSpinner, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        connectionsPanel.add(new JLabel("Hacia:"), gbc);
        gbc.gridx = 3;
        connectionsPanel.add(toVertexSpinner, gbc);
        
        gbc.gridx = 4; gbc.gridy = 0;
        connectionsPanel.add(new JLabel("Peso:"), gbc);
        gbc.gridx = 5;
        connectionsPanel.add(weightSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        connectionsPanel.add(addConnectionButton, gbc);
        
        gbc.gridx = 3; gbc.gridwidth = 3;
        connectionsPanel.add(removeConnectionButton, gbc);
        
        panel.add(creationPanel, BorderLayout.NORTH);
        panel.add(connectionsPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createAlgorithmsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel izquierdo: Lista de algoritmos
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Algoritmos Disponibles"));
        leftPanel.add(new JScrollPane(algorithmList), BorderLayout.CENTER);
        
        // Panel de descripción
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder("Descripción"));
        descPanel.add(new JScrollPane(algorithmDescriptionArea), BorderLayout.CENTER);
        
        // Panel de controles
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Vértice inicial:"));
        controlPanel.add(startVertexSpinner);
        controlPanel.add(executeButton);
        controlPanel.add(clearButton);
        
        // Panel derecho: Resultados
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        JTabbedPane resultTabs = new JTabbedPane();
        resultTabs.addTab("Resultado", new JScrollPane(resultArea));
        resultTabs.addTab("Pasos", new JScrollPane(stepsArea));
        
        rightPanel.add(resultTabs, BorderLayout.CENTER);
        
        // Layout principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.add(leftPanel, BorderLayout.CENTER);
        leftContainer.add(descPanel, BorderLayout.SOUTH);
        leftContainer.add(controlPanel, BorderLayout.PAGE_END);
        
        splitPane.setLeftComponent(leftContainer);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(350);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createVisualizationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel superior: Visualización gráfica del grafo
        JPanel graphPanel = new JPanel(new BorderLayout());
        graphPanel.setBorder(BorderFactory.createTitledBorder("Visualización del Grafo"));
        graphPanel.add(graphVisualizer, BorderLayout.CENTER);
        
        // Panel inferior: Tabla de adyacencia
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Matriz de Adyacencia"));
        JScrollPane scrollPane = new JScrollPane(adjacencyTable);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Dividir la vista con un JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(graphPanel);
        splitPane.setBottomComponent(tablePanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.6);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }

    // Métodos públicos para el controlador
    
    public void setCreateGraphListener(ActionListener listener) {
        createGraphButton.addActionListener(listener);
    }
    
    public void setLoadSampleListener(ActionListener listener) {
        loadSampleButton.addActionListener(listener);
    }
    
    public void setAddConnectionListener(ActionListener listener) {
        addConnectionButton.addActionListener(listener);
    }
    
    public void setRemoveConnectionListener(ActionListener listener) {
        removeConnectionButton.addActionListener(listener);
    }
    
    public void setExecuteAlgorithmListener(ActionListener listener) {
        executeButton.addActionListener(listener);
    }
    
    public void setClearListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }
    
    public void setAlgorithmSelectionListener(ListSelectionListener listener) {
        algorithmList.addListSelectionListener(listener);
    }
    
    public int getGraphSize() {
        return (Integer) graphSizeSpinner.getValue();
    }
    
    public int getFromVertex() {
        return (Integer) fromVertexSpinner.getValue();
    }
    
    public int getToVertex() {
        return (Integer) toVertexSpinner.getValue();
    }
    
    public int getEdgeWeight() {
        return (Integer) weightSpinner.getValue();
    }
    
    public int getStartVertex() {
        return (Integer) startVertexSpinner.getValue();
    }
    
    public String getSelectedAlgorithm() {
        return algorithmList.getSelectedValue();
    }
    
    public void setAvailableAlgorithms(List<String> algorithms) {
        algorithmListModel.clear();
        for (String algorithm : algorithms) {
            algorithmListModel.addElement(algorithm);
        }
    }
    
    public void setAlgorithmDescription(String description) {
        algorithmDescriptionArea.setText(description);
    }
    
    public void updateGraphDisplay(GraphModel graph) {
        if (graph == null) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            return;
        }
        
        int size = graph.size();
        String[] labels = graph.getVertexLabels();
        int[][] matrix = graph.getAdjacencyMatrix();
        
        // Configurar columnas
        String[] columnNames = new String[size + 1];
        columnNames[0] = "";
        System.arraycopy(labels, 0, columnNames, 1, size);
        
        // Configurar filas
        Object[][] data = new Object[size][size + 1];
        for (int i = 0; i < size; i++) {
            data[i][0] = labels[i];
            for (int j = 0; j < size; j++) {
                data[i][j + 1] = matrix[i][j] == 0 ? "-" : String.valueOf(matrix[i][j]);
            }
        }
        
        tableModel.setDataVector(data, columnNames);
        
        // Actualizar visualizador gráfico
        graphVisualizer.setGraph(graph);
        
        // Actualizar límites de los spinners
        updateSpinnerLimits(size);
    }
    
    private void updateSpinnerLimits(int maxSize) {
        int max = maxSize - 1;
        
        ((SpinnerNumberModel)fromVertexSpinner.getModel()).setMaximum(max);
        ((SpinnerNumberModel)toVertexSpinner.getModel()).setMaximum(max);
        ((SpinnerNumberModel)startVertexSpinner.getModel()).setMaximum(max);
        
        // Ajustar valores si están fuera del rango
        if ((Integer)fromVertexSpinner.getValue() > max) {
            fromVertexSpinner.setValue(max);
        }
        if ((Integer)toVertexSpinner.getValue() > max) {
            toVertexSpinner.setValue(max);
        }
        if ((Integer)startVertexSpinner.getValue() > max) {
            startVertexSpinner.setValue(max);
        }
    }
    
    public void displayResult(GraphResult result) {
        StringBuilder resultText = new StringBuilder();
        resultText.append("=== RESULTADO ===\n");
        resultText.append("Resumen: ").append(result.getSummary()).append("\n");
        resultText.append("Costo total: ").append(result.getTotalCost()).append("\n\n");
        
        if (!result.getHighlightedPath().isEmpty()) {
            resultText.append("Camino destacado:\n");
            for (String pathElement : result.getHighlightedPath()) {
                resultText.append("- ").append(pathElement).append("\n");
            }
        }
        
        if (!result.getSelectedEdges().isEmpty()) {
            resultText.append("\nAristas seleccionadas:\n");
            for (String edge : result.getSelectedEdges()) {
                resultText.append("- ").append(edge).append("\n");
            }
        }
        
        resultArea.setText(resultText.toString());
        
        // Actualizar visualizador gráfico con resultados
        if (result.getHighlightedPath() != null && !result.getHighlightedPath().isEmpty()) {
            graphVisualizer.setHighlightedPath(result.getHighlightedPath());
        }
        
        // Mostrar pasos
        StringBuilder stepsText = new StringBuilder();
        stepsText.append("=== PASOS DEL ALGORITMO ===\n\n");
        for (int i = 0; i < result.getSteps().size(); i++) {
            stepsText.append("Paso ").append(i + 1).append(": ").append(result.getSteps().get(i)).append("\n");
        }
        
        stepsArea.setText(stepsText.toString());
    }
    
    public void clearResults() {
        resultArea.setText("");
        stepsArea.setText("");
        graphVisualizer.setHighlightedPath(null);
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
        // Método requerido por IView - actualización genérica de la vista
        if (data instanceof GraphModel) {
            updateGraphDisplay((GraphModel) data);
        } else if (data instanceof GraphResult) {
            displayResult((GraphResult) data);
        }
    }
}