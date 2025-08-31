package com.algoritmoscurso.view.semana2;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionListener;

/**
 * Vista para los ejemplos de análisis Big O
 */
public class BigOView extends JPanel implements IView {
    private JList<String> ejemplosList;
    private JTextArea codeArea;
    private JTextArea analysisArea;
    private JTextArea descriptionBox;
    private JButton executeButton;
    private JTextField inputField;
    private JTextField inputField2;
    private JTextArea resultArea;
    private JPanel executionPanel; // Movido aquí como campo de la clase
    
    public BigOView() {
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
        // Lista de ejemplos simplificada
        ejemplosList = new JList<>();
        ejemplosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ejemplosList.setFont(new Font("Arial", Font.PLAIN, 12));
        ejemplosList.setFixedCellHeight(24); // Altura fija para células
        ejemplosList.setBorder(BorderFactory.createEmptyBorder());
        ejemplosList.setBackground(new Color(250, 250, 250));
        
        // Área de código simplificada
        codeArea = new JTextArea();
        codeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        codeArea.setEditable(false);
        codeArea.setBackground(new Color(252, 252, 252));
        codeArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        codeArea.setLineWrap(true);
        codeArea.setWrapStyleWord(true);
        
        // Área de análisis simplificada
        analysisArea = new JTextArea();
        analysisArea.setFont(new Font("Arial", Font.PLAIN, 12));
        analysisArea.setEditable(false);
        analysisArea.setBackground(new Color(252, 252, 252));
        analysisArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        analysisArea.setLineWrap(true);
        analysisArea.setWrapStyleWord(true);

    // Caja de descripción (no editable) situada encima de las pestañas
    descriptionBox = new JTextArea();
    descriptionBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
    descriptionBox.setEditable(false);
    descriptionBox.setBackground(new Color(250, 250, 250));
    descriptionBox.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    descriptionBox.setLineWrap(true);
    descriptionBox.setWrapStyleWord(true);
        
    // Toolbar de ejecución en lugar de panel (se mostrará arriba)
        executionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        executionPanel.setBackground(Color.WHITE);
        
    JLabel inputLabel = new JLabel("Valor de n:");
    inputLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    inputField = new JTextField("10", 6);
    JLabel inputLabel2 = new JLabel("Parámetro 2:");
    inputLabel2.setFont(new Font("Arial", Font.PLAIN, 12));
    inputField2 = new JTextField("0", 6);
        executeButton = new JButton("Ejecutar");
        styleButton(executeButton, new Color(33, 103, 178));
        
    executionPanel.add(inputLabel);
    executionPanel.add(inputField);
    executionPanel.add(inputLabel2);
    executionPanel.add(inputField2);
    executionPanel.add(executeButton);
        
    // Área de resultados: más grande para mostrar salidas
    resultArea = new JTextArea(6, 50);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(252, 252, 252));
    }
    
    private void setupLayout() {
        // Panel izquierdo minimalista
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(200, 0));
        JLabel examplesLabel = new JLabel("Ejemplos");
        examplesLabel.setForeground(new Color(33, 103, 178));
        examplesLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        examplesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        leftPanel.add(examplesLabel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(ejemplosList), BorderLayout.CENTER);
        
        // Panel derecho simplificado
        JPanel rightPanel = new JPanel(new BorderLayout(0, 8));
        
    // Pestañas simplificadas con tamaños controlados
    JTabbedPane tabbedPane = new JTabbedPane();
    JScrollPane codeScroll = new JScrollPane(codeArea);
    JScrollPane analysisScroll = new JScrollPane(analysisArea);
    // reducir visualmente las cajas de código y análisis
    codeScroll.setPreferredSize(new Dimension(520, 180));
    analysisScroll.setPreferredSize(new Dimension(520, 140));
    tabbedPane.addTab("Código", codeScroll);
    tabbedPane.addTab("Análisis", analysisScroll);
    tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));

    // Panel que contiene ejecución encima de la descripción y luego las pestañas
    JPanel topDesc = new JPanel(new BorderLayout(0, 6));
    topDesc.add(executionPanel, BorderLayout.NORTH);
    topDesc.add(new JScrollPane(descriptionBox), BorderLayout.CENTER);

    JPanel centerWithDesc = new JPanel(new BorderLayout(0, 6));
    centerWithDesc.add(topDesc, BorderLayout.NORTH);
    centerWithDesc.add(tabbedPane, BorderLayout.CENTER);
        
        // Panel de controles y resultados
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 4));
        bottomPanel.add(executionPanel, BorderLayout.NORTH);
        
    JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel = new JLabel("Resultado");
        resultLabel.setForeground(new Color(33, 103, 178));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
    resultPanel.add(resultLabel, BorderLayout.NORTH);
    JScrollPane resultScroll = new JScrollPane(resultArea);
    resultScroll.setPreferredSize(new Dimension(520, 160));
    resultPanel.add(resultScroll, BorderLayout.CENTER);
    bottomPanel.add(resultPanel, BorderLayout.CENTER);
        
    rightPanel.add(centerWithDesc, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Layout principal con divisor JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        splitPane.setDividerSize(4);
        splitPane.setBorder(null);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    // Métodos para configurar listeners
    public void setListSelectionListener(ListSelectionListener listener) {
        ejemplosList.addListSelectionListener(listener);
    }
    
    public void setExecuteButtonListener(java.awt.event.ActionListener listener) {
        executeButton.addActionListener(listener);
    }
    
    // Métodos para obtener datos de la vista
    public int getSelectedExampleIndex() {
        return ejemplosList.getSelectedIndex();
    }
    
    public String getInputValue() {
        return inputField.getText();
    }

    public String getSecondInputValue() {
        return inputField2.getText();
    }
    
    // Métodos para actualizar la vista
    public void setExamples(String[] examples) {
        ejemplosList.setListData(examples);
    }

    /**
     * Muestra la descripción de la actividad (por ejemplo provista por el modelo)
     */
    public void setDescription(String text) {
        descriptionBox.setText(text);
        descriptionBox.setCaretPosition(0);
    }
    
    public void setCodeText(String text) {
        codeArea.setText(text);
        codeArea.setCaretPosition(0);
    }
    
    public void setAnalysisText(String text) {
        analysisArea.setText(text);
        analysisArea.setCaretPosition(0);
    }
    
    public void setResultText(String text) {
        resultArea.setText(text);
        resultArea.setCaretPosition(0);
    }
    
    public void clearSelection() {
        ejemplosList.clearSelection();
        codeArea.setText("");
        analysisArea.setText("");
        resultArea.setText("");
    }
    
    @Override
    public void updateView(Object data) {
        repaint();
    }
    
    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ejemplosList.setEnabled(enabled);
        executeButton.setEnabled(enabled);
        inputField.setEnabled(enabled);
    }

    // Helper para estilo de botones (profesional, discreto)
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
    }
}
