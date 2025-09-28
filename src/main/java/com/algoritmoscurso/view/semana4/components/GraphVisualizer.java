package com.algoritmoscurso.view.semana4.components;

import com.algoritmoscurso.model.graph.GraphModel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GraphVisualizer extends JPanel {
    private GraphModel graph;
    private Map<String, Point> nodePositions;
    private List<String> highlightedPath;
    private Color nodeColor = new Color(255, 255, 224); // Crema
    private Color edgeColor = Color.BLACK;
    private Color pathColor = new Color(0, 128, 0); // Verde
    private int nodeRadius = 25;
    
    public GraphVisualizer() {
        this.nodePositions = new HashMap<>();
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
    }
    
    public void setGraph(GraphModel graph) {
        this.graph = graph;
        calculateNodePositions();
        repaint();
    }
    
    public void setHighlightedPath(List<String> path) {
        this.highlightedPath = path;
        repaint();
    }
    
    private void calculateNodePositions() {
        if (graph == null || graph.size() == 0) return;

        nodePositions.clear();
        String[] vertexLabels = graph.getVertexLabels();
        List<String> nodes = java.util.Arrays.asList(vertexLabels);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 60;
        
        if (nodes.size() == 1) {
            nodePositions.put(nodes.get(0), new Point(centerX, centerY));
        } else {
            double angleStep = 2 * Math.PI / nodes.size();
            for (int i = 0; i < nodes.size(); i++) {
                double angle = i * angleStep - Math.PI / 2; // Empezar desde arriba
                int x = centerX + (int)(radius * Math.cos(angle));
                int y = centerY + (int)(radius * Math.sin(angle));
                nodePositions.put(nodes.get(i), new Point(x, y));
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (graph == null) return;
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Si las posiciones no están calculadas, calcularlas
        if (nodePositions.isEmpty()) {
            calculateNodePositions();
        }
        
        // Dibujar aristas
        drawEdges(g2d);
        
        // Dibujar nodos
        drawNodes(g2d);
        
        g2d.dispose();
    }
    
    private void drawEdges(Graphics2D g2d) {
        int[][] matrix = graph.getAdjacencyMatrix();
        if (matrix == null) return;

        String[] vertexLabels = graph.getVertexLabels();
        List<String> nodes = java.util.Arrays.asList(vertexLabels);
        
        g2d.setStroke(new BasicStroke(2.0f));
        
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                if (matrix[i][j] != 0) {
                    Point p1 = nodePositions.get(nodes.get(i));
                    Point p2 = nodePositions.get(nodes.get(j));
                    
                    if (p1 != null && p2 != null) {
                        // Determinar color de la arista
                        boolean isHighlighted = isEdgeHighlighted(nodes.get(i), nodes.get(j));
                        g2d.setColor(isHighlighted ? pathColor : edgeColor);
                        
                        // Dibujar línea
                        g2d.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
                        
                        // Dibujar peso
                        drawEdgeWeight(g2d, p1, p2, matrix[i][j]);
                    }
                }
            }
        }
    }
    
    private void drawEdgeWeight(Graphics2D g2d, Point p1, Point p2, int weight) {
        int midX = (p1.x + p2.x) / 2;
        int midY = (p1.y + p2.y) / 2;
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        
        String weightStr = String.valueOf(weight);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(weightStr);
        int textHeight = fm.getHeight();
        
        // Fondo blanco para el texto
        g2d.setColor(Color.WHITE);
        g2d.fillRect(midX - textWidth/2 - 2, midY - textHeight/2 - 2, textWidth + 4, textHeight);
        
        g2d.setColor(Color.BLACK);
        g2d.drawString(weightStr, midX - textWidth/2, midY + fm.getAscent()/2);
    }
    
    private void drawNodes(Graphics2D g2d) {
        for (Map.Entry<String, Point> entry : nodePositions.entrySet()) {
            String node = entry.getKey();
            Point pos = entry.getValue();
            
            boolean isHighlighted = highlightedPath != null && highlightedPath.contains(node);
            
            // Dibujar círculo del nodo
            g2d.setColor(isHighlighted ? pathColor : nodeColor);
            Ellipse2D circle = new Ellipse2D.Double(pos.x - nodeRadius, pos.y - nodeRadius, 
                                                   2 * nodeRadius, 2 * nodeRadius);
            g2d.fill(circle);
            
            // Borde del nodo
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.draw(circle);
            
            // Texto del nodo
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(node);
            int textHeight = fm.getHeight();
            g2d.drawString(node, pos.x - textWidth/2, pos.y + fm.getAscent()/2);
        }
    }
    
    private boolean isEdgeHighlighted(String node1, String node2) {
        if (highlightedPath == null || highlightedPath.size() < 2) return false;
        
        for (int i = 0; i < highlightedPath.size() - 1; i++) {
            String current = highlightedPath.get(i);
            String next = highlightedPath.get(i + 1);
            if ((current.equals(node1) && next.equals(node2)) || 
                (current.equals(node2) && next.equals(node1))) {
                return true;
            }
        }
        return false;
    }
}