package com.algoritmoscurso.view.semana3;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vista simple para algoritmos voraces siguiendo el estilo de la semana 2
 */
public class GreedyView extends JPanel implements IView {
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    private JTabbedPane mainTabbedPane;
    private JTextArea descriptionArea;
    
    // Vistas especializadas
    private CoinChangeView coinChangeView;
    private TravelingSalesmanView travelingSalesmanView;

    public GreedyView() {
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
        // Crear las vistas especializadas
        coinChangeView = new CoinChangeView();
        travelingSalesmanView = new TravelingSalesmanView();
        
        // Crear área de resultados general
        resultArea = new JTextArea(15, 60);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(252, 252, 252));
        resultArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        
        scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));
        
        // Crear panel con pestañas
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setBackground(Color.WHITE);
        mainTabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titleLabel = new JLabel("Algoritmos Voraces - Semana 3");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(33, 103, 178));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Agregar pestañas
        mainTabbedPane.addTab("📊 Resumen", createSummaryPanel());
        mainTabbedPane.addTab("💰 Cambio de Moneda", coinChangeView);
        mainTabbedPane.addTab("🗺️ Agente Viajero", travelingSalesmanView);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(mainTabbedPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        showWelcomeMessage();
    }
    
    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new BorderLayout(10, 10));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Descripción general
        descriptionArea = new JTextArea(6, 60);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(250, 250, 250));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setText("Algoritmos Voraces (Greedy Algorithms)\n\n" +
                              "Los algoritmos voraces son una técnica de diseño algorítmico que hace " +
                              "la elección localmente óptima en cada paso, con la esperanza de encontrar " +
                              "un óptimo global. Son útiles para problemas de optimización donde una " +
                              "estrategia local puede llevar a una solución global aceptable.\n\n" +
                              "En esta semana estudiaremos dos problemas clásicos:\n" +
                              "• Cambio de Moneda: Minimizar el número de monedas para dar cambio\n" +
                              "• Agente Viajero: Encontrar una ruta corta visitando todas las ciudades");
        
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(Color.WHITE);
        JLabel descLabel = new JLabel("Descripción");
        descLabel.setForeground(new Color(33, 103, 178));
        descLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        descPanel.add(descLabel, BorderLayout.NORTH);
        descPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        
        // Botones de acceso rápido
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(Color.WHITE);
        
        JButton coinButton = new JButton("💰 Ir a Cambio de Moneda");
        styleButton(coinButton, new Color(33, 103, 178));
        coinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainTabbedPane.setSelectedIndex(1);
            }
        });
        
        JButton tspButton = new JButton("🗺️ Ir a Agente Viajero");
        styleButton(tspButton, new Color(33, 103, 178));
        tspButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainTabbedPane.setSelectedIndex(2);
            }
        });
        
        buttonsPanel.add(coinButton);
        buttonsPanel.add(tspButton);
        
        summaryPanel.add(descPanel, BorderLayout.CENTER);
        summaryPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return summaryPanel;
    }
    
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(200, 35));
    }
    
    private void showWelcomeMessage() {
        resultArea.setText("Algoritmos Voraces - Semana 3\n\n" +
                          "¡Bienvenido al estudio de algoritmos voraces!\n\n" +
                          "Los algoritmos están listos para usar:\n" +
                          "• Cambio de Moneda: Encuentra el mínimo número de monedas\n" +
                          "• Agente Viajero: Encuentra una ruta corta\n\n" +
                          "Los algoritmos voraces toman decisiones localmente óptimas\n" +
                          "en cada paso, esperando encontrar un óptimo global.\n\n" +
                          "Selecciona una pestaña para comenzar.");
    }
    
    public void displayResults(Object result) {
        if (result != null) {
            resultArea.setText(result.toString());
        }
    }
    
    @Override
    public void updateView(Object data) {
        displayResults(data);
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
        coinChangeView.setEnabled(enabled);
        travelingSalesmanView.setEnabled(enabled);
        mainTabbedPane.setEnabled(enabled);
    }
    
    // Métodos de compatibilidad
    public void initialize() {
        showWelcomeMessage();
    }
    
    public void clear() {
        resultArea.setText("");
        coinChangeView.clear();
        travelingSalesmanView.clear();
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
    
    // Getters para acceso a las vistas especializadas
    public CoinChangeView getCoinChangeView() {
        return coinChangeView;
    }
    
    public TravelingSalesmanView getTravelingSalesmanView() {
        return travelingSalesmanView;
    }
}
