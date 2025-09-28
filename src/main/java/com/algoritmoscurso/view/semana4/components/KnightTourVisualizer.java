package com.algoritmoscurso.view.semana4.components;

import javax.swing.*;
import java.awt.*;

public class KnightTourVisualizer extends JPanel {
    private int[][] board;
    private int boardSize;
    private int currentMove = 0;
    private Point knightPosition;
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color VISITED_OVERLAY = new Color(30, 144, 255, 90); // azul translúcido
    private static final Color CURRENT_POSITION = new Color(220, 20, 60); // rojo intenso
    
    public KnightTourVisualizer(int size) {
        this.boardSize = size;
        this.board = new int[size][size];
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
        resetBoard();
    }

    public void setSizeAndReset(int size) {
        this.boardSize = size;
        this.board = new int[size][size];
        resetBoard();
        revalidate();
        repaint();
    }
    
    public void resetBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = -1;
            }
        }
        currentMove = 0;
        knightPosition = null;
        repaint();
    }
    
    public void setBoard(int[][] newBoard) {
        if (newBoard == null) return;
        int newSize = newBoard.length;
        // If sizes differ, adjust internal board to match incoming snapshot
        if (newSize != this.boardSize) {
            // Ensure size change and reset happen on EDT
            try {
                javax.swing.SwingUtilities.invokeAndWait(() -> setSizeAndReset(newSize));
            } catch (Exception ex) {
                // Fallback: set directly (non-EDT)
                this.boardSize = newSize;
                this.board = new int[newSize][newSize];
                resetBoard();
            }
        }

        // Copy safely using bounds checks
        int rows = Math.min(boardSize, newBoard.length);
        int cols = Math.min(boardSize, newBoard[0].length);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = newBoard[i][j];
            }
        }
        findCurrentPosition();
        repaint();
    }
    
    public void setMove(int row, int col, int moveNumber) {
        if (isValidPosition(row, col)) {
            board[row][col] = moveNumber;
            knightPosition = new Point(col, row);
            currentMove = moveNumber;
            repaint();
        }
    }
    
    private void findCurrentPosition() {
        int maxMove = -1;
        Point lastPosition = null;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] > maxMove) {
                    maxMove = board[i][j];
                    lastPosition = new Point(j, i);
                }
            }
        }

        knightPosition = lastPosition;
        currentMove = Math.max(0, maxMove);
    }
    
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
    int padding = 20;
    int panelSize = Math.min(getWidth(), getHeight()) - padding * 2;
    int squareSize = panelSize / boardSize;
    int boardPixelSize = squareSize * boardSize;
    int startX = (getWidth() - boardPixelSize) / 2;
    int startY = (getHeight() - boardPixelSize) / 2;
        
        // Dibujar el tablero
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int x = startX + col * squareSize;
                int y = startY + row * squareSize;
                
                // Determinar color base de la casilla (ajedrez)
                Color baseColor = ((row + col) % 2 == 0) ? LIGHT_SQUARE : DARK_SQUARE;
                g2d.setColor(baseColor);
                g2d.fillRect(x, y, squareSize, squareSize);
                
                // Borde
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, squareSize, squareSize);
                
                // Overlay para casillas visitadas (>=0 significa visitada; -1 = no visitada)
                if (board[row][col] >= 0) {
                    g2d.setColor(VISITED_OVERLAY);
                    g2d.fillRect(x, y, squareSize, squareSize);
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, Math.max(10, squareSize / 4)));
                    String moveText = String.valueOf(board[row][col]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(moveText);
                    int textHeight = fm.getHeight();

                    g2d.drawString(moveText,
                        x + (squareSize - textWidth) / 2,
                        y + (squareSize + textHeight) / 2 - fm.getDescent());
                }
            }
        }
        
        // Dibujar el caballo en la posición actual
        if (knightPosition != null) {
            int kx = startX + knightPosition.x * squareSize + squareSize/2;
            int ky = startY + knightPosition.y * squareSize + squareSize/2;
            int radius = Math.max(8, squareSize/4);
            g2d.setColor(new Color(255, 255, 255, 180));
            g2d.fillOval(kx - radius - 2, ky - radius - 2, 2*radius + 4, 2*radius + 4);
            g2d.setColor(CURRENT_POSITION);
            g2d.fillOval(kx - radius, ky - radius, 2*radius, 2*radius);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawOval(kx - radius, ky - radius, 2*radius, 2*radius);
        }

        // Dibujar información
        drawInfo(g2d, startY - 20);
        
        g2d.dispose();
    }
    
    private void drawInfo(Graphics2D g2d, int y) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        
        String info = "Recorrido del Caballo (" + boardSize + "x" + boardSize + ")";
        if (currentMove > 0) {
            info += " - Movimiento: " + currentMove + "/" + (boardSize * boardSize);
        }
        
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(info);
        g2d.drawString(info, (getWidth() - textWidth) / 2, y);
        
        // Mostrar si el recorrido está completo
        if (currentMove == boardSize * boardSize) {
            g2d.setColor(new Color(0, 128, 0));
            String completeText = "¡Recorrido Completo!";
            int completeWidth = fm.stringWidth(completeText);
            g2d.drawString(completeText, (getWidth() - completeWidth) / 2, y + 25);
        }
    }
    
    public void animateStep(int row, int col, int moveNumber) {
        setMove(row, col, moveNumber);
        
        // Pequeña pausa para la animación
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Animate a sequence of flattened board snapshots (each is size*size length).
     */
    public void animateSnapshots(java.util.List<int[]> snapshots, int delayMs) {
        animateSnapshots(snapshots, delayMs, null);
    }

    /** Animate snapshots and call callback (on EDT) when finished. */
    public void animateSnapshots(java.util.List<int[]> snapshots, int delayMs, Runnable onComplete) {
        if (snapshots == null || snapshots.isEmpty()) {
            if (onComplete != null) {
                javax.swing.SwingUtilities.invokeLater(onComplete);
            }
            return;
        }

        new Thread(() -> {
            for (int[] flat : snapshots) {
                int size = (int) Math.sqrt(flat.length);
                if (size != boardSize) {
                    final int s = size;
                    try {
                        javax.swing.SwingUtilities.invokeAndWait(() -> setSizeAndReset(s));
                    } catch (Exception ex) {
                        setSizeAndReset(s);
                    }
                }
                int[][] newBoard = new int[size][size];
                for (int i = 0; i < flat.length; i++) {
                    newBoard[i / size][i % size] = flat[i];
                }
                final int[][] boardForUi = newBoard;
                try {
                    javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            setBoard(boardForUi);
                        }
                    });
                } catch (Exception ex) {
                    // fallback to direct call on exception
                    setBoard(boardForUi);
                }
                try {
                    Thread.sleep(Math.max(20, delayMs));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            if (onComplete != null) {
                javax.swing.SwingUtilities.invokeLater(onComplete);
            }
        }).start();
    }
    
    public boolean isComplete() {
        return currentMove == boardSize * boardSize;
    }
    
    public int getCurrentMove() {
        return currentMove;
    }
    
    public int getTotalMoves() {
        return boardSize * boardSize;
    }
}