package com.algoritmoscurso.view.semana3;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Vista simple para el algoritmo de Cambio de Moneda
 */
public class CoinChangeView extends JPanel implements IView {
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    
    // Componentes específicos para cambio de moneda
    private JTextField amountField;
    private JTextField coinsField;
    private JButton executeButton;
    private JTextArea descriptionArea;

    public CoinChangeView() {
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
        amountField = new JTextField("67", 10);
        coinsField = new JTextField("25,10,5,1", 20);
        
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
        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        configPanel.setBackground(Color.WHITE);
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración"));
        
        configPanel.add(new JLabel("Monto:"));
        configPanel.add(amountField);
        configPanel.add(Box.createHorizontalStrut(10));
        configPanel.add(new JLabel("Monedas (separadas por comas):"));
        configPanel.add(coinsField);
        configPanel.add(Box.createHorizontalStrut(10));
        configPanel.add(executeButton);
        
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
                executeCoinChangeAlgorithm();
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
        descriptionArea.setText("Algoritmo de Cambio de Moneda\n\n" +
                              "Este algoritmo voraz encuentra el número mínimo de monedas necesarias " +
                              "para dar cambio de una cantidad específica. Utiliza una estrategia " +
                              "voraz seleccionando siempre la moneda de mayor valor posible.");
        
        resultArea.setText("Cambio de Moneda - Algoritmo Voraz\n\n" +
                          "Instrucciones:\n" +
                          "1. Ingrese el monto a cambiar\n" +
                          "2. Especifique las denominaciones disponibles\n" +
                          "3. Haga clic en 'Ejecutar' para ver el resultado\n\n" +
                          "Ejemplo por defecto: Monto 67 con monedas [25,10,5,1]\n");
    }
    
    private void executeCoinChangeAlgorithm() {
        try {
            int amount = Integer.parseInt(amountField.getText().trim());
            String[] coinStrings = coinsField.getText().trim().split(",");
            int[] coins = new int[coinStrings.length];
            
            for (int i = 0; i < coinStrings.length; i++) {
                coins[i] = Integer.parseInt(coinStrings[i].trim());
            }
            
            // Ejecutar algoritmo voraz de cambio de moneda
            List<Integer> result = makeChange(amount, coins);
            
            // Mostrar resultados
            displayResults(amount, coins, result);
            
        } catch (NumberFormatException ex) {
            showError("Error: Por favor ingrese números válidos");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }
    
    /**
     * Algoritmo voraz para cambio de moneda
     */
    private List<Integer> makeChange(int amount, int[] coins) {
        List<Integer> result = new ArrayList<>();
        
        // Ordenar monedas de mayor a menor
        Integer[] sortedCoins = new Integer[coins.length];
        for (int i = 0; i < coins.length; i++) {
            sortedCoins[i] = coins[i];
        }
        Arrays.sort(sortedCoins, Collections.reverseOrder());
        
        int remaining = amount;
        
        for (int coin : sortedCoins) {
            while (remaining >= coin) {
                result.add(coin);
                remaining -= coin;
            }
        }
        
        if (remaining > 0) {
            throw new RuntimeException("No se puede dar cambio exacto con las monedas disponibles");
        }
        
        return result;
    }
    
    private void displayResults(int amount, int[] coins, List<Integer> result) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADOS DEL CAMBIO DE MONEDA ===\n\n");
        sb.append("Monto a cambiar: ").append(amount).append("\n");
        sb.append("Monedas disponibles: ");
        for (int i = 0; i < coins.length; i++) {
            sb.append(coins[i]);
            if (i < coins.length - 1) sb.append(", ");
        }
        sb.append("\n\n");
        
        sb.append("SOLUCIÓN ENCONTRADA:\n");
        sb.append("Monedas utilizadas: ").append(result).append("\n");
        sb.append("Número total de monedas: ").append(result.size()).append("\n\n");
        
        // Contar frecuencia de cada moneda
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int coin : result) {
            frequency.put(coin, frequency.getOrDefault(coin, 0) + 1);
        }
        
        sb.append("DESGLOSE POR DENOMINACIÓN:\n");
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            sb.append("- ").append(entry.getValue()).append(" moneda(s) de ").append(entry.getKey()).append("\n");
        }
        
        sb.append("\nCARACTERÍSTICAS:\n");
        sb.append("• Complejidad temporal: O(n) donde n = número de denominaciones\n");
        sb.append("• Complejidad espacial: O(m) donde m = número de monedas usadas\n");
        sb.append("• Garantiza solución óptima para sistemas canónicos\n");
        
        resultArea.setText(sb.toString());
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
    
    public void setAmount(int amount) {
        amountField.setText(String.valueOf(amount));
    }
    
    public void setCoins(int[] coins) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coins.length; i++) {
            sb.append(coins[i]);
            if (i < coins.length - 1) sb.append(",");
        }
        coinsField.setText(sb.toString());
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
