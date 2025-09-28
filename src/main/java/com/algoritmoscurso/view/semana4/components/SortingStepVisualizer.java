package com.algoritmoscurso.view.semana4.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SortingStepVisualizer extends JPanel {
    private List<int[]> steps;
    private int currentStep = 0;
    private String algorithmName;
    private JButton prevButton, nextButton, playButton, pauseButton;
    private JSlider stepSlider;
    private JLabel stepLabel;
    private Timer animationTimer;
    private boolean isPlaying = false;
    private SortingVisualizer sortingVisualizer;
    
    public SortingStepVisualizer() {
        this.steps = new ArrayList<>();
        this.sortingVisualizer = new SortingVisualizer();
        initializeControls();
        setupLayout();
        setupTimer();
    }
    
    private void initializeControls() {
        prevButton = new JButton("◀");
        nextButton = new JButton("▶");
        playButton = new JButton("▶");
        pauseButton = new JButton("⏸");
        
        stepSlider = new JSlider();
        stepLabel = new JLabel("Paso: 0/0");
        
        // Configurar botones
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        playButton.setEnabled(false);
        pauseButton.setEnabled(false);
        pauseButton.setVisible(false);
        
        // Agregar listeners
        prevButton.addActionListener(e -> previousStep());
        nextButton.addActionListener(e -> nextStep());
        playButton.addActionListener(e -> startAnimation());
        pauseButton.addActionListener(e -> stopAnimation());
        
        stepSlider.addChangeListener(e -> {
            if (!stepSlider.getValueIsAdjusting()) {
                goToStep(stepSlider.getValue());
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con el visualizador
        add(sortingVisualizer, BorderLayout.CENTER);
        
        // Panel de controles
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(prevButton);
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(nextButton);
        controlPanel.add(stepLabel);
        
        // Panel del slider
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(new JLabel("Progreso: "), BorderLayout.WEST);
        sliderPanel.add(stepSlider, BorderLayout.CENTER);
        
        // Panel inferior combinado
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        bottomPanel.add(sliderPanel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupTimer() {
        animationTimer = new Timer(1000, e -> {
            if (hasNextStep()) {
                nextStep();
            } else {
                stopAnimation();
            }
        });
    }
    
    public void setSteps(List<int[]> steps, String algorithmName) {
        this.steps = new ArrayList<>(steps);
        this.algorithmName = algorithmName;
        this.currentStep = 0;
        
        sortingVisualizer.setSteps(steps);
        sortingVisualizer.setAlgorithmName(algorithmName);
        
        updateSlider();
        updateControls();
        updateStepLabel();
    }
    
    public void setArray(int[] array) {
        sortingVisualizer.setArray(array);
        this.steps.clear();
        this.currentStep = 0;
        updateControls();
        updateStepLabel();
    }
    
    private void updateSlider() {
        stepSlider.setMinimum(0);
        stepSlider.setMaximum(Math.max(0, steps.size() - 1));
        stepSlider.setValue(currentStep);
    }
    
    private void updateControls() {
        boolean hasSteps = !steps.isEmpty();
        prevButton.setEnabled(hasSteps && currentStep > 0);
        nextButton.setEnabled(hasSteps && currentStep < steps.size() - 1);
        playButton.setEnabled(hasSteps && currentStep < steps.size() - 1);
    }
    
    private void updateStepLabel() {
        int total = Math.max(1, steps.size());
        stepLabel.setText(String.format("Paso: %d/%d", currentStep + 1, total));
    }
    
    private void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            sortingVisualizer.previousStep();
            stepSlider.setValue(currentStep);
            updateControls();
            updateStepLabel();
        }
    }
    
    private void nextStep() {
        if (currentStep < steps.size() - 1) {
            currentStep++;
            sortingVisualizer.nextStep();
            stepSlider.setValue(currentStep);
            updateControls();
            updateStepLabel();
        }
    }
    
    private void goToStep(int step) {
        if (step >= 0 && step < steps.size() && step != currentStep) {
            currentStep = step;
            
            // Actualizar el visualizador al paso específico
            sortingVisualizer.resetToStart();
            for (int i = 0; i < step; i++) {
                sortingVisualizer.nextStep();
            }
            
            updateControls();
            updateStepLabel();
        }
    }
    
    private void startAnimation() {
        if (!steps.isEmpty() && currentStep < steps.size() - 1) {
            isPlaying = true;
            playButton.setVisible(false);
            pauseButton.setVisible(true);
            animationTimer.start();
        }
    }
    
    private void stopAnimation() {
        isPlaying = false;
        animationTimer.stop();
        pauseButton.setVisible(false);
        playButton.setVisible(true);
    }
    
    private boolean hasNextStep() {
        return currentStep < steps.size() - 1;
    }
    
    public void reset() {
        stopAnimation();
        currentStep = 0;
        if (!steps.isEmpty()) {
            sortingVisualizer.resetToStart();
            stepSlider.setValue(0);
        }
        updateControls();
        updateStepLabel();
    }
    
    public void clear() {
        stopAnimation();
        steps.clear();
        currentStep = 0;
        sortingVisualizer.setArray(new int[0]);
        updateSlider();
        updateControls();
        updateStepLabel();
    }
    
    public void setHighlightedIndices(int... indices) {
        sortingVisualizer.setHighlightedIndices(indices);
    }
    
    public void setAnimationSpeed(int milliseconds) {
        animationTimer.setDelay(Math.max(100, milliseconds));
    }
}