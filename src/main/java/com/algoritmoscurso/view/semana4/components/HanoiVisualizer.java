package com.algoritmoscurso.view.semana4.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Stack;

public class HanoiVisualizer extends JPanel {
    private Stack<Integer>[] towers;
    private int numberOfDisks;
    private static final int BASE_WIDTH = 120;
    private static final int BASE_HEIGHT = 10;
    private Color[] diskColors;
    
    @SuppressWarnings("unchecked")
    public HanoiVisualizer(int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
        this.towers = new Stack[3];
        for (int i = 0; i < 3; i++) {
            towers[i] = new Stack<>();
        }
        
        // Inicializar con todos los discos en la primera torre
        for (int i = numberOfDisks; i > 0; i--) {
            towers[0].push(i);
        }
        
        // Generar colores para los discos
        initializeDiskColors();
        
        setPreferredSize(new Dimension(600, 300));
        setBackground(Color.WHITE);
    }

    public void setNumberOfDisks(int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
        for (int i = 0; i < 3; i++) towers[i] = new Stack<>();
        for (int i = numberOfDisks; i > 0; i--) towers[0].push(i);
        initializeDiskColors();
        revalidate();
        repaint();
    }
    
    private void initializeDiskColors() {
        diskColors = new Color[numberOfDisks + 1];
        for (int i = 1; i <= numberOfDisks; i++) {
            float hue = (float) i / numberOfDisks;
            diskColors[i] = Color.getHSBColor(hue, 0.8f, 0.9f);
        }
    }
    
    public void setState(List<Stack<Integer>> towerStates) {
        for (int i = 0; i < 3; i++) {
            towers[i].clear();
            if (i < towerStates.size()) {
                towers[i].addAll(towerStates.get(i));
            }
        }
        repaint();
    }
    
    public void moveDisk(int from, int to) {
        // Validate indices
        if (from < 0 || from >= towers.length || to < 0 || to >= towers.length) return;

        synchronized (towers) {
            if (towers[from] == null || towers[to] == null) return;
            if (towers[from].isEmpty()) return;
            Integer disk = towers[from].pop();
            towers[to].push(disk);
        }
        // Repaint on EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
    }
    
    public void reset() {
        for (int i = 0; i < 3; i++) {
            towers[i].clear();
        }
        
        for (int i = numberOfDisks; i > 0; i--) {
            towers[0].push(i);
        }
        repaint();
    }

    /**
     * Animate a sequence of moves where each move is [from,to]
     */
    public void animateMoves(java.util.List<int[]> moves, int delayMs) {
        if (moves == null || moves.isEmpty()) return;
        new Thread(() -> {
            for (int[] mv : moves) {
                if (mv == null || mv.length < 2) continue;
                int from = mv[0];
                int to = mv[1];
                // Run the disk move on the EDT to avoid concurrent modifications
                final int f = from;
                final int t = to;
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            moveDisk(f, t);
                        }
                    });
                } catch (Exception e) {
                    // If invokeAndWait fails, fallback to invokeLater
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            moveDisk(f, t);
                        }
                    });
                }
                try {
                    Thread.sleep(Math.max(50, delayMs));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int towerSpacing = panelWidth / 4;
        int baseY = panelHeight - 50;

        // Calcular tamaños dinámicos según la cantidad de discos
        int maxDiskAreaHeight = Math.max(80, panelHeight - 160);
        int diskHeight = Math.max(12, Math.min(28, maxDiskAreaHeight / Math.max(1, numberOfDisks + 1)));
        int towerHeight = Math.max(80, numberOfDisks * (diskHeight + 2) + 40);

        // Dibujar las tres torres
        for (int i = 0; i < 3; i++) {
            int towerCenterX = towerSpacing * (i + 1);
            drawTower(g2d, towerCenterX, baseY, i, towerHeight, diskHeight);
        }
        
        // Dibujar etiquetas
        drawLabels(g2d, towerSpacing, baseY);
        
        g2d.dispose();
    }
    
    private void drawTower(Graphics2D g2d, int centerX, int baseY, int towerIndex, int towerHeight, int diskHeight) {
        // Dibujar base
        int baseW = Math.max(BASE_WIDTH, diskHeight * 6);
        g2d.setColor(new Color(139, 69, 19)); // Marrón
        g2d.fillRect(centerX - baseW/2, baseY, baseW, BASE_HEIGHT);

        // Dibujar poste (altura dinámica)
        int towerWidth = Math.max(12, diskHeight / 2 + 6);
        g2d.setColor(new Color(101, 67, 33)); // Marrón oscuro
        g2d.fillRect(centerX - towerWidth/2, baseY - towerHeight, towerWidth, towerHeight);

        // Dibujar discos (desde la base hacia arriba)
        Stack<Integer> tower = towers[towerIndex];
        for (int i = 0; i < tower.size(); i++) {
            Integer diskSize = tower.get(i);
            int y = baseY - BASE_HEIGHT - (i + 1) * diskHeight;
            drawDisk(g2d, centerX, y, diskSize, diskHeight, baseW - 20);
        }
    }
    
    private void drawDisk(Graphics2D g2d, int centerX, int y, int size, int diskHeight, int maxDiskWidth) {
        // Calcular ancho proporcional entre un mínimo y un máximo
        int minWidth = Math.max(20, diskHeight * 2);
        int maxWidth = Math.max(minWidth + 10, maxDiskWidth);
        int diskWidth;
        if (numberOfDisks <= 1) {
            diskWidth = maxWidth;
        } else {
            diskWidth = minWidth + (size - 1) * (maxWidth - minWidth) / Math.max(1, numberOfDisks - 1);
        }

        // Dibujar disco
        Color color = diskColors.length > size ? diskColors[size] : Color.LIGHT_GRAY;
        g2d.setColor(color);
        g2d.fillRoundRect(centerX - diskWidth/2, y, diskWidth, diskHeight - 2, 6, 6);

        // Borde del disco
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.drawRoundRect(centerX - diskWidth/2, y, diskWidth, diskHeight - 2, 6, 6);

        // Número en el disco (centro)
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, Math.max(10, diskHeight - 4)));
        String text = String.valueOf(size);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = centerX - textWidth/2;
        int textY = y + (diskHeight + fm.getAscent())/2 - 2;
        g2d.drawString(text, textX, textY);
    }
    
    private void drawLabels(Graphics2D g2d, int towerSpacing, int baseY) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        
        String[] labels = {"Torre A", "Torre B", "Torre C"};
        for (int i = 0; i < 3; i++) {
            int centerX = towerSpacing * (i + 1);
            int textWidth = fm.stringWidth(labels[i]);
            g2d.drawString(labels[i], centerX - textWidth/2, baseY + 30);
        }
    }
    
    public boolean isComplete() {
        return towers[2].size() == numberOfDisks;
    }
    
    public int getNumberOfDisks() {
        return numberOfDisks;
    }
}