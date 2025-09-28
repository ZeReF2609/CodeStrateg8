package com.algoritmoscurso.view.semana4.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SortingVisualizer extends JPanel {
    private int[] array;
    private int[] highlightedIndices;
    private List<int[]> steps;
    private int currentStep = 0;
    private String algorithmName;
    
    public SortingVisualizer() {
        setPreferredSize(new Dimension(600, 300));
        setBackground(Color.WHITE);
        this.steps = new ArrayList<>();
        this.highlightedIndices = new int[0];
    }
    
    public void setArray(int[] array) {
        this.array = array.clone();
        this.steps.clear();
        this.currentStep = 0;
        repaint();
    }
    
    public void setSteps(List<int[]> steps) {
        this.steps = new ArrayList<>(steps);
        this.currentStep = 0;
        if (!steps.isEmpty()) {
            this.array = steps.get(0).clone();
        }
        repaint();
    }
    
    public void setAlgorithmName(String name) {
        this.algorithmName = name;
    }
    
    public void setHighlightedIndices(int... indices) {
        this.highlightedIndices = indices;
        repaint();
    }
    
    public void nextStep() {
        if (currentStep < steps.size() - 1) {
            currentStep++;
            array = steps.get(currentStep).clone();
            repaint();
        }
    }
    
    public void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            array = steps.get(currentStep).clone();
            repaint();
        }
    }
    
    public void resetToStart() {
        currentStep = 0;
        if (!steps.isEmpty()) {
            array = steps.get(0).clone();
            repaint();
        }
    }
    
    public boolean hasNextStep() {
        return currentStep < steps.size() - 1;
    }
    
    public boolean hasPreviousStep() {
        return currentStep > 0;
    }
    
    public int getCurrentStep() {
        return currentStep + 1;
    }
    
    public int getTotalSteps() {
        return steps.size();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (array == null || array.length == 0) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Ingrese un arreglo para visualizar", 20, getHeight() / 2);
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawBars(g2d);
        drawInfo(g2d);
        
        g2d.dispose();
    }
    
    private void drawBars(Graphics2D g2d) {
        int width = getWidth() - 40;
        int height = getHeight() - 80;
        int barWidth = width / array.length;
        int maxValue = findMaxValue();
        
        for (int i = 0; i < array.length; i++) {
            int barHeight = (int) ((double) array[i] / maxValue * height);
            int x = 20 + i * barWidth;
            int y = getHeight() - 40 - barHeight;
            
            // Determinar color de la barra
            Color barColor = Color.LIGHT_GRAY;
            if (isHighlighted(i)) {
                barColor = new Color(255, 100, 100); // Rojo para elementos destacados
            }
            
            // Dibujar barra
            g2d.setColor(barColor);
            g2d.fillRect(x + 2, y, barWidth - 4, barHeight);
            
            // Borde de la barra
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x + 2, y, barWidth - 4, barHeight);
            
            // Valor en la parte superior de la barra
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 11));
            String value = String.valueOf(array[i]);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(value);
            g2d.drawString(value, x + (barWidth - textWidth) / 2, y - 5);
            
            // Índice en la parte inferior
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String index = String.valueOf(i);
            int indexWidth = fm.stringWidth(index);
            g2d.drawString(index, x + (barWidth - indexWidth) / 2, getHeight() - 20);
        }
    }
    
    private void drawInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        String info = "";
        if (algorithmName != null) {
            info = algorithmName;
        }
        
        if (steps.size() > 0) {
            info += " - Paso " + getCurrentStep() + " de " + getTotalSteps();
        }
        
        if (!info.isEmpty()) {
            g2d.drawString(info, 20, 20);
        }
    }
    
    private int findMaxValue() {
        int max = Integer.MIN_VALUE;
        for (int value : array) {
            max = Math.max(max, value);
        }
        return max > 0 ? max : 1;
    }
    
    private boolean isHighlighted(int index) {
        for (int highlightedIndex : highlightedIndices) {
            if (highlightedIndex == index) {
                return true;
            }
        }
        return false;
    }
}