package com.algoritmoscurso.view.semana2;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista para los algoritmos de ordenación
 */
public class SortingView extends JPanel implements IView {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextArea timeArea;
    private JTextArea descriptionBox;
    private JList<String> algorithmList;
    private DefaultListModel<String> algorithmListModel;
    private JButton sortButton;
    private JButton generateRandomButton;
    private JButton clearButton;
    private JTextArea comparisonArea;
    private JButton compareButton;
    
    public SortingView() {
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
    // Left: algorithm list + action buttons stacked
    algorithmListModel = new DefaultListModel<>();
    algorithmList = new JList<>(algorithmListModel);
    algorithmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    algorithmList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    algorithmList.setVisibleRowCount(8);

    sortButton = new JButton("Ordenar");
    compareButton = new JButton("Comparar");
    generateRandomButton = new JButton("Generar");
    clearButton = new JButton("Limpiar");

    styleButton(sortButton, new Color(33, 103, 178));
    styleButton(compareButton, new Color(33, 103, 178));
    styleButton(generateRandomButton, new Color(33, 103, 178));
    styleButton(clearButton, new Color(33, 103, 178));

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout(6, 6));
    leftPanel.setBackground(Color.WHITE);
    leftPanel.setBorder(BorderFactory.createTitledBorder("Métodos"));
    leftPanel.add(new JScrollPane(algorithmList), BorderLayout.CENTER);

    JPanel leftButtons = new JPanel();
    leftButtons.setBackground(Color.WHITE);
    leftButtons.setLayout(new BoxLayout(leftButtons, BoxLayout.Y_AXIS));
    leftButtons.add(Box.createVerticalStrut(6));
    leftButtons.add(wrapButton(sortButton));
    leftButtons.add(Box.createVerticalStrut(6));
    leftButtons.add(wrapButton(compareButton));
    leftButtons.add(Box.createVerticalStrut(6));
    leftButtons.add(wrapButton(generateRandomButton));
    leftButtons.add(Box.createVerticalStrut(6));
    leftButtons.add(wrapButton(clearButton));
    leftButtons.add(Box.createVerticalGlue());
    leftPanel.add(leftButtons, BorderLayout.SOUTH);

    // Main text areas
    inputArea = new JTextArea(8, 36);
    inputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

    outputArea = new JTextArea(10, 36);
    outputArea.setEditable(false);
    outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    outputArea.setBackground(new Color(250, 250, 250));

    // Performance and comparison
    timeArea = new JTextArea(4, 48);
    timeArea.setEditable(false);
    timeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    timeArea.setBackground(new Color(250, 250, 250));

    descriptionBox = new JTextArea(3, 60);
    descriptionBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
    descriptionBox.setEditable(false);
    descriptionBox.setBackground(new Color(250, 250, 250));
    descriptionBox.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    descriptionBox.setLineWrap(true);
    descriptionBox.setWrapStyleWord(true);

    comparisonArea = new JTextArea(8, 60);
    comparisonArea.setEditable(false);
    comparisonArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
    comparisonArea.setBackground(new Color(245, 245, 245));

    add(leftPanel, BorderLayout.WEST);
    }
    
    private void setupLayout() {
    // Right area: description at top, then input/output split, then performance/comparison
    JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
    rightPanel.setBackground(Color.WHITE);
    rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel descLabel = new JLabel("Descripción");
    descLabel.setForeground(new Color(33, 103, 178));
    descLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
    JPanel descPanel = new JPanel(new BorderLayout());
    descPanel.setBackground(Color.WHITE);
    descPanel.add(descLabel, BorderLayout.NORTH);
    descPanel.add(new JScrollPane(descriptionBox), BorderLayout.CENTER);

    // Input / Output side-by-side
    JPanel ioPanel = new JPanel(new GridLayout(1, 2, 8, 8));
    ioPanel.setBackground(Color.WHITE);

    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.setBackground(Color.WHITE);
    JLabel inputLabel = new JLabel("Entrada");
    inputLabel.setForeground(new Color(33, 103, 178));
    inputLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
    inputPanel.add(inputLabel, BorderLayout.NORTH);
    inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

    JPanel outputPanel = new JPanel(new BorderLayout());
    outputPanel.setBackground(Color.WHITE);
    JLabel outputLabel = new JLabel("Salida");
    outputLabel.setForeground(new Color(33, 103, 178));
    outputLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
    outputPanel.add(outputLabel, BorderLayout.NORTH);
    outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

    ioPanel.add(inputPanel);
    ioPanel.add(outputPanel);

    // Performance and comparison - stacked vertically with splitter
    JPanel perfPanel = new JPanel(new BorderLayout(6, 6));
    perfPanel.setBackground(Color.WHITE);
    JLabel timeLabel = new JLabel("Rendimiento");
    timeLabel.setForeground(new Color(33, 103, 178));
    timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
    perfPanel.add(timeLabel, BorderLayout.NORTH);
    perfPanel.add(new JScrollPane(timeArea), BorderLayout.CENTER);

    JPanel comparisonPanel = new JPanel(new BorderLayout());
    comparisonPanel.setBackground(Color.WHITE);
    JLabel compLabel = new JLabel("Comparativa");
    compLabel.setForeground(new Color(33, 103, 178));
    compLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
    comparisonPanel.add(compLabel, BorderLayout.NORTH);
    comparisonPanel.add(new JScrollPane(comparisonArea), BorderLayout.CENTER);

    JSplitPane perfSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, perfPanel, comparisonPanel);
    perfSplit.setResizeWeight(0.35);
    perfSplit.setBorder(null);

    rightPanel.add(descPanel, BorderLayout.NORTH);
    rightPanel.add(ioPanel, BorderLayout.CENTER);
    rightPanel.add(perfSplit, BorderLayout.SOUTH);

    add(rightPanel, BorderLayout.CENTER);
    }    // Helpers para diseño encapsulado
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
    }

    // Helper to provide consistent button sizing inside a vertical box
    private JPanel wrapButton(JButton b) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setBackground(Color.WHITE);
        b.setMaximumSize(new Dimension(160, 36));
        b.setPreferredSize(new Dimension(160, 36));
        p.add(b);
        return p;
    }

    
    
    // Métodos para configurar listeners
    public void setSortButtonListener(ActionListener listener) {
        sortButton.addActionListener(listener);
    }
    
    public void setGenerateButtonListener(ActionListener listener) {
        generateRandomButton.addActionListener(listener);
    }
    
    public void setClearButtonListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }
    
    public void setAlgorithmSelectionListener(ListSelectionListener listener) {
        algorithmList.addListSelectionListener(listener);
    }
    
    // Métodos para obtener datos de la vista
    public String getInputText() {
        return inputArea.getText();
    }
    
    public String getSelectedAlgorithm() {
    return algorithmList.getSelectedValue();
    }
    
    // Métodos para actualizar la vista
    public void setInputText(String text) {
        inputArea.setText(text);
    }
    
    public void setOutputText(String text) {
        outputArea.setText(text);
    }
    
    public void setTimeText(String text) {
        timeArea.setText(text);
    }
    
    public void setAlgorithms(String[] algorithms) {
        algorithmListModel.clear();
        for (String algorithm : algorithms) {
            algorithmListModel.addElement(algorithm);
        }
        // Seleccionar el primero por defecto
        if (!algorithmListModel.isEmpty()) algorithmList.setSelectedIndex(0);
    }

    /**
     * Forzar la selección del primer algoritmo (API pública)
     */
    public void selectFirstAlgorithm() {
    if (!algorithmListModel.isEmpty()) algorithmList.setSelectedIndex(0);
    }

    public void setComparisonText(String text) {
        comparisonArea.setText(text);
        comparisonArea.setCaretPosition(0);
    }

    public void setCompareButtonListener(ActionListener listener) {
        compareButton.addActionListener(listener);
    }

    /**
     * Muestra la descripción de la actividad (Parte 2)
     */
    public void setDescription(String text) {
        descriptionBox.setText(text);
        descriptionBox.setCaretPosition(0);
    }
    
    @Override
    public void updateView(Object data) {
        // Implementación específica según el tipo de datos
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
        sortButton.setEnabled(enabled);
        generateRandomButton.setEnabled(enabled);
        clearButton.setEnabled(enabled);
    algorithmList.setEnabled(enabled);
        inputArea.setEnabled(enabled);
    }
}
