package com.algoritmoscurso.view.semana4;

import com.algoritmoscurso.interfaces.IView;
import com.algoritmoscurso.model.backtracking.BacktrackingResult;
import com.algoritmoscurso.view.semana4.components.HanoiVisualizer;
import com.algoritmoscurso.view.semana4.components.KnightTourVisualizer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Vista para algoritmos de backtracking aplicados a grafos - Semana 4
 */
public class BacktrackingGraphView extends JPanel implements IView {
    
    // Componentes para el Tour del Caballo
    private JSpinner boardSizeSpinner;
    private JSpinner startXSpinner;
    private JSpinner startYSpinner;
    private JButton startKnightTourButton;
    
    // Componentes para Torres de Hanoi
    private JSpinner disksSpinner;
    private JButton startHanoiButton;
    
    // Área de resultados
    private JTextArea resultArea;
    private JTextArea stepsArea;
    private JButton clearButton;
    
    // Panel de visualización
    private KnightTourVisualizer knightVisualizer;
    private HanoiVisualizer hanoiVisualizer;
    private JLabel statusLabel;

    public BacktrackingGraphView() {
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
        // Componentes para Tour del Caballo
        boardSizeSpinner = new JSpinner(new SpinnerNumberModel(8, 4, 12, 1));
        startXSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 11, 1));
        startYSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 11, 1));
        startKnightTourButton = new JButton("Ejecutar Tour del Caballo");
        
        // Componentes para Torres de Hanoi
        disksSpinner = new JSpinner(new SpinnerNumberModel(3, 2, 8, 1));
        startHanoiButton = new JButton("Ejecutar Torres de Hanoi");
        
        // Botones de control
        clearButton = new JButton("Limpiar");
        
        // Áreas de resultado
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        stepsArea = new JTextArea();
        stepsArea.setEditable(false);
        stepsArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        
    // Inicializar visualizadores
    knightVisualizer = new KnightTourVisualizer(8);
    hanoiVisualizer = new HanoiVisualizer(3);

        // Ajustar límites de spinners cuando cambie el tamaño del tablero
        boardSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent ev) {
                int size = (Integer) boardSizeSpinner.getValue();
                // actualizar max de coordenadas
                SpinnerNumberModel mx = (SpinnerNumberModel) startXSpinner.getModel();
                SpinnerNumberModel my = (SpinnerNumberModel) startYSpinner.getModel();
                mx.setMaximum(size - 1);
                my.setMaximum(size - 1);
                // clamp current values to new max
                int curX = (Integer) mx.getValue();
                int curY = (Integer) my.getValue();
                if (curX > size - 1) mx.setValue(size - 1);
                if (curY > size - 1) my.setValue(size - 1);
                // actualizar visualizador inmediatamente
                knightVisualizer.setSizeAndReset(size);
            }
        });
        
        statusLabel = new JLabel("Listo para ejecutar algoritmos de backtracking");
        
        // Estilos
        styleButtons();
    }

    private void styleButtons() {
        Color primaryColor = new Color(33, 103, 178);
        Color secondaryColor = new Color(220, 20, 60);
        
        styleButton(startKnightTourButton, primaryColor);
        styleButton(startHanoiButton, secondaryColor);
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
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña 1: Tour del Caballo
        JPanel knightTourPanel = createKnightTourPanel();
        tabbedPane.addTab("Tour del Caballo", knightTourPanel);
        
        // Pestaña 2: Torres de Hanoi
        JPanel hanoiPanel = createHanoiPanel();
        tabbedPane.addTab("Torres de Hanoi", hanoiPanel);
        
        // Pestaña 3: Resultados
        JPanel resultsPanel = createResultsPanel();
        tabbedPane.addTab("Resultados", resultsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    /**
     * Habilita o deshabilita los controles mientras se ejecuta un algoritmo para evitar
     * múltiples ejecuciones concurrentes que puedan dejar la UI en mal estado.
     */
    public void setControlsEnabled(boolean enabled) {
        boardSizeSpinner.setEnabled(enabled);
        startXSpinner.setEnabled(enabled);
        startYSpinner.setEnabled(enabled);
        startKnightTourButton.setEnabled(enabled);
        disksSpinner.setEnabled(enabled);
        startHanoiButton.setEnabled(enabled);
        clearButton.setEnabled(enabled);
    }

    private JPanel createKnightTourPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel de configuración
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración del Tour del Caballo"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        configPanel.add(new JLabel("Tamaño del tablero:"), gbc);
        gbc.gridx = 1;
        configPanel.add(boardSizeSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        configPanel.add(new JLabel("Posición inicial X:"), gbc);
        gbc.gridx = 1;
        configPanel.add(startXSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        configPanel.add(new JLabel("Posición inicial Y:"), gbc);
        gbc.gridx = 1;
        configPanel.add(startYSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        configPanel.add(startKnightTourButton, gbc);
        
        // Panel de descripción
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder("Descripción"));
        
        JTextArea descArea = new JTextArea(
            "El Tour del Caballo es un problema clásico de backtracking donde se debe encontrar " +
            "una secuencia de movimientos de caballo que visite cada casilla del tablero exactamente una vez.\n\n" +
            "El algoritmo utiliza backtracking para explorar todas las posibilidades y encontrar " +
            "una solución válida."
        );
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(new Color(248, 248, 248));
        
        descPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);
        
        panel.add(configPanel, BorderLayout.NORTH);
        panel.add(descPanel, BorderLayout.CENTER);

        // Área de visualización para el Tour del Caballo (más gráfica y separada)
        JPanel visArea = new JPanel(new BorderLayout());
        visArea.setBorder(BorderFactory.createTitledBorder("Visualización - Tour del Caballo"));
        visArea.add(knightVisualizer, BorderLayout.CENTER);
        visArea.setPreferredSize(new Dimension(520, 520));
        panel.add(visArea, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHanoiPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel de configuración
        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración de Torres de Hanoi"));
        configPanel.add(new JLabel("Número de discos:"));
        configPanel.add(disksSpinner);
        configPanel.add(startHanoiButton);
        
        // Panel de descripción
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder("Descripción"));
        
        JTextArea descArea = new JTextArea(
            "Las Torres de Hanoi es un juego matemático que consiste en tres varillas y un número " +
            "de discos de diferentes tamaños que pueden deslizarse por las varillas.\n\n" +
            "El objetivo es mover todos los discos de la primera varilla a la tercera, siguiendo estas reglas:\n" +
            "1. Solo se puede mover un disco a la vez\n" +
            "2. Solo se puede tomar el disco superior de una pila\n" +
            "3. No se puede colocar un disco grande sobre uno pequeño\n\n" +
            "Este algoritmo demuestra el poder del enfoque recursivo y backtracking."
        );
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(new Color(248, 248, 248));
        
        descPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);
        
        panel.add(configPanel, BorderLayout.NORTH);
        panel.add(descPanel, BorderLayout.CENTER);

        // Área de visualización para Torres de Hanoi (separada)
        JPanel visArea = new JPanel(new BorderLayout());
        visArea.setBorder(BorderFactory.createTitledBorder("Visualización - Torres de Hanoi"));
        visArea.add(hanoiVisualizer, BorderLayout.CENTER);
        visArea.setPreferredSize(new Dimension(600, 320));
        panel.add(visArea, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel de control
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(clearButton);
        
        // Pestañas de resultados
        JTabbedPane resultTabs = new JTabbedPane();
        resultTabs.addTab("Resultado", new JScrollPane(resultArea));
        resultTabs.addTab("Pasos", new JScrollPane(stepsArea));
        
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(resultTabs, BorderLayout.CENTER);
        
        return panel;
    }

    // Métodos públicos para el controlador
    
    public void setKnightTourListener(ActionListener listener) {
        startKnightTourButton.addActionListener(listener);
    }
    
    public void setHanoiListener(ActionListener listener) {
        startHanoiButton.addActionListener(listener);
    }
    
    public void setClearListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }
    
    public int getBoardSize() {
        return (Integer) boardSizeSpinner.getValue();
    }
    
    public int getStartX() {
        return (Integer) startXSpinner.getValue();
    }
    
    public int getStartY() {
        return (Integer) startYSpinner.getValue();
    }
    
    public int getDisksCount() {
        return (Integer) disksSpinner.getValue();
    }
    
    public void displayResult(BacktrackingResult result) {
        StringBuilder resultText = new StringBuilder();
        resultText.append("=== RESULTADO DE BACKTRACKING ===\n");
        
        if (result.getMoves().isEmpty()) {
            resultText.append("Estado: FALLO - No se encontró solución\n");
        } else {
            resultText.append("Estado: ÉXITO - Solución encontrada\n");
            resultText.append("Número de movimientos: ").append(result.getMoves().size()).append("\n\n");
            
            resultText.append("Secuencia de movimientos:\n");
            for (int i = 0; i < result.getMoves().size(); i++) {
                resultText.append(String.format("%3d. %s\n", i + 1, result.getMoves().get(i)));
            }
            
            // Información adicional sobre snapshots del tablero si están disponibles
            if (!result.getBoardSnapshots().isEmpty()) {
                resultText.append("\nSnapshots del tablero disponibles: ")
                          .append(result.getBoardSnapshots().size()).append("\n");
            }
        }
        
        resultArea.setText(resultText.toString());
        
        // Actualizar visualizador correspondiente basándose en el contenido de los movimientos
        if (!result.getMoves().isEmpty()) {
            String firstMove = result.getMoves().get(0).toLowerCase();
            if (firstMove.contains("caballo") || firstMove.contains("knight") || firstMove.contains("casilla")) {
                // Actualizar el visualizador del caballo
                if (!result.getBoardSnapshots().isEmpty()) {
                    int[] lastBoard = result.getBoardSnapshots().get(result.getBoardSnapshots().size() - 1);
                    updateKnightBoard(lastBoard);
                }
            } else if (firstMove.contains("disco") || firstMove.contains("torre") || firstMove.contains("hanoi")) {
                // Actualizar el visualizador de Hanoi
                parseHanoiMoves(result.getMoves());
            }
        }
        
        // Mostrar pasos como movimientos
        StringBuilder stepsText = new StringBuilder();
        stepsText.append("=== SECUENCIA DETALLADA ===\n\n");
        
        if (result.getMoves().isEmpty()) {
            stepsText.append("No se generaron movimientos válidos.\n");
        } else {
            for (int i = 0; i < result.getMoves().size(); i++) {
                stepsText.append("Movimiento ").append(i + 1).append(": ")
                         .append(result.getMoves().get(i)).append("\n");
            }
        }
        
        stepsArea.setText(stepsText.toString());
    }
    
    public void clearResults() {
        resultArea.setText("");
        stepsArea.setText("");
        knightVisualizer.resetBoard();
        hanoiVisualizer.reset();
    }

    public KnightTourVisualizer getKnightVisualizer() {
        return knightVisualizer;
    }

    public HanoiVisualizer getHanoiVisualizer() {
        return hanoiVisualizer;
    }
    
    private void updateKnightBoard(int[] flatBoard) {
        int size = (int) Math.sqrt(flatBoard.length);
        int[][] board = new int[size][size];
        
        // Convertir array plano a matriz 2D
        for (int i = 0; i < flatBoard.length; i++) {
            board[i / size][i % size] = flatBoard[i];
        }
        
        knightVisualizer.setBoard(board);
    }
    
    private void parseHanoiMoves(List<String> moves) {
        // Resetear el visualizador al estado inicial
        hanoiVisualizer.reset();
        
        // Procesar cada movimiento
        for (String move : moves) {
            // Buscar patrón "torre X a torre Y" o similar
            if (move.toLowerCase().contains("torre") && move.toLowerCase().contains(" a ")) {
                try {
                    String[] parts = move.toLowerCase().split(" a ");
                    if (parts.length >= 2) {
                        int from = extractTowerNumber(parts[0]);
                        int to = extractTowerNumber(parts[1]);
                        if (from >= 0 && to >= 0) {
                            hanoiVisualizer.moveDisk(from, to);
                        }
                    }
                } catch (Exception e) {
                    // Continuar si no se puede parsear el movimiento
                }
            }
        }
    }
    
    private int extractTowerNumber(String text) {
        // Buscar números en el texto (A=0, B=1, C=2)
        if (text.contains("a") || text.contains("A")) return 0;
        if (text.contains("b") || text.contains("B")) return 1;
        if (text.contains("c") || text.contains("C")) return 2;
        
        // Buscar números directos
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                int num = Character.getNumericValue(c);
                if (num >= 1 && num <= 3) return num - 1; // Convertir a base 0
            }
        }
        return -1;
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
        if (data instanceof BacktrackingResult) {
            displayResult((BacktrackingResult) data);
        }
    }
}