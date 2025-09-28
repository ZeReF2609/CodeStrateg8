package com.algoritmoscurso.view.semana5;

import com.algoritmoscurso.model.dp.DPResult;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class FloydView extends JPanel {
    private JTextArea matrixArea;
    private JButton runButton;
    private JPanel graphPanel;
    private JPanel matrixPanel;
    private JTextArea resultArea;

    public FloydView() {
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel(new BorderLayout());
        matrixArea = new JTextArea("0 3 0 7\n8 0 2 0\n5 0 0 1\n2 0 0 0", 6, 30);
        top.add(new JScrollPane(matrixArea), BorderLayout.CENTER);
        runButton = new JButton("Ejecutar Floyd-Warshall");
        top.add(runButton, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        graphPanel = new GraphPanel();
        matrixPanel = new MatrixPanel();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphPanel, matrixPanel);
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);

        resultArea = new JTextArea(); resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);
    }

    public void setRunListener(ActionListener l) { runButton.addActionListener(l); }
    public String getMatrixText() { return matrixArea.getText(); }

    public void showResult(DPResult r) {
        final List<int[]> snaps = r.getSnapshots();
        resultArea.setText("snapshots=" + snaps.size());
        if (snaps == null || snaps.isEmpty()) return;

        // animate snapshots using a Swing Timer on the EDT
        final int delay = 300;
        final int[] idx = new int[] {0};
        Timer t = new Timer(delay, null);
        t.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (idx[0] >= snaps.size()) {
                    ((Timer)e.getSource()).stop();
                    // when finished, show path and highlight on graph
                    int best = r.getBestValue();
                    java.util.List<Integer> path = r.getSelectedItems();
                    if (path != null && !path.isEmpty()) {
                        ((GraphPanel)graphPanel).setHighlightedPath(path);
                        graphPanel.repaint();
                    }
                    // show result dialog
                    String msg;
                    if (best == Integer.MAX_VALUE) {
                        msg = "No existe camino A->E (infinito).";
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Distancia A->E = ").append(best).append("\nCamino: ");
                        if (path != null && !path.isEmpty()) {
                            for (int i = 0; i < path.size(); i++) {
                                sb.append((char)('A' + path.get(i)));
                                if (i < path.size() - 1) sb.append(" -> ");
                            }
                        } else sb.append("(sin camino)");
                        msg = sb.toString();
                    }
                    JOptionPane.showMessageDialog(FloydView.this, msg, "Resultado Floyd-Warshall", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int[] flat = snaps.get(idx[0]++);
                int n = (int)Math.round(Math.sqrt(flat.length));
                int[][] m = toMatrix(flat, n);
                ((GraphPanel)graphPanel).setMatrix(m);
                ((MatrixPanel)matrixPanel).setMatrix(m);
                resultArea.setText(String.format("snapshot %d / %d", idx[0], snaps.size()));
            }
        });
        t.setInitialDelay(0);
        t.start();

        // show distance and path if available
        int best = r.getBestValue();
        java.util.List<Integer> path = r.getSelectedItems();
        if (best == Integer.MAX_VALUE) {
            // unreachable
            if (path == null || path.isEmpty()) resultArea.append("\nA->E: no path");
            else resultArea.append("\nA->E: no path (infinite)");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("\nDist A->E = ").append(best).append("\nPath: ");
            if (path != null && !path.isEmpty()) {
                for (int i = 0; i < path.size(); i++) {
                    int v = path.get(i);
                    sb.append((char)('A' + v));
                    if (i < path.size() - 1) sb.append(" -> ");
                }
            } else {
                sb.append("(no path)");
            }
            resultArea.append(sb.toString());
        }
    }

    private static int[][] toMatrix(int[] flat, int n) {
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = flat[i * n + j];
                m[i][j] = v;
            }
        }
        return m;
    }

    // Simple panel that draws the directed graph based on adjacency weights (non-zero = edge)
    private static class GraphPanel extends JPanel {
        private int[][] matrix;
        private java.util.List<Integer> highlightedPath;

        public GraphPanel() {
            setPreferredSize(new Dimension(400, 400));
        }

        public void setMatrix(int[][] m) {
            this.matrix = m;
            repaint();
        }

        public void setHighlightedPath(java.util.List<Integer> path) {
            this.highlightedPath = path;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (matrix == null) return;
            int n = matrix.length;
            int w = getWidth();
            int h = getHeight();
            int radius = Math.min(w, h) / 2 - 40;
            int cx = w / 2;
            int cy = h / 2;
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n - Math.PI / 2;
                int x = cx + (int)(Math.cos(angle) * radius);
                int y = cy + (int)(Math.sin(angle) * radius);
                pts[i] = new Point(x, y);
            }
            // draw edges
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int val = matrix[i][j];
                    if (i == j) continue;
                    if (val != 0 && val != Integer.MAX_VALUE) {
                        boolean highlighted = false;
                        if (highlightedPath != null && highlightedPath.size() >= 2) {
                            for (int p = 0; p < highlightedPath.size() - 1; p++) {
                                if (highlightedPath.get(p) == i && highlightedPath.get(p+1) == j) { highlighted = true; break; }
                            }
                        }
                        drawArrow(g2, pts[i].x, pts[i].y, pts[j].x, pts[j].y, String.valueOf(val), highlighted);
                    }
                }
            }
            // draw nodes
            for (int i = 0; i < n; i++) {
                int nodeR = 20;
                int x = pts[i].x - nodeR;
                int y = pts[i].y - nodeR;
                g2.setColor(Color.WHITE);
                g2.fillOval(x, y, nodeR*2, nodeR*2);
                g2.setColor(Color.BLACK);
                g2.drawOval(x, y, nodeR*2, nodeR*2);
                String label = String.valueOf(i+1);
                FontMetrics fm = g2.getFontMetrics();
                int tx = pts[i].x - fm.stringWidth(label)/2;
                int ty = pts[i].y + fm.getAscent()/2 - 2;
                g2.drawString(label, tx, ty);
            }
        }

        private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2, String label, boolean highlighted) {
            if (highlighted) {
                g2.setColor(new Color(200, 40, 40));
                g2.setStroke(new BasicStroke(3.5f));
            } else {
                g2.setColor(new Color(0, 120, 215));
                g2.setStroke(new BasicStroke(2f));
            }
            int dx = x2 - x1;
            int dy = y2 - y1;
            double dist = Math.hypot(dx, dy);
            if (dist < 1) return;
            double ux = dx / dist;
            double uy = dy / dist;
            int pad = 24; // leave space from node edge
            int sx = x1 + (int)(ux * pad);
            int sy = y1 + (int)(uy * pad);
            int ex = x2 - (int)(ux * pad);
            int ey = y2 - (int)(uy * pad);
            g2.drawLine(sx, sy, ex, ey);
            // arrowhead
            double ax = ex - ux * 8 - uy * 6;
            double ay = ey - uy * 8 + ux * 6;
            double bx = ex - ux * 8 + uy * 6;
            double by = ey - uy * 8 - ux * 6;
            int[] xs = new int[] {(int)ex, (int)ax, (int)bx};
            int[] ys = new int[] {(int)ey, (int)ay, (int)by};
            g2.fillPolygon(xs, ys, 3);
            // label midpoint
            String txt = label;
            FontMetrics fm = g2.getFontMetrics();
            int mx = (sx + ex) / 2 - fm.stringWidth(txt)/2;
            int my = (sy + ey) / 2 - 4;
            g2.setColor(Color.DARK_GRAY);
            g2.drawString(txt, mx, my);
        }
    }

    // Panel that draws a matrix with True/False colored cells
    private static class MatrixPanel extends JPanel {
        private int[][] matrix;

        public MatrixPanel() {
            setPreferredSize(new Dimension(400, 400));
        }

        public void setMatrix(int[][] m) {
            this.matrix = m;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (matrix == null) return;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int n = matrix.length;
            int w = getWidth();
            int h = getHeight();
            int margin = 30;
            int cellW = (w - margin*2) / n;
            int cellH = (h - margin*2) / n;
            int startX = margin;
            int startY = margin;
            FontMetrics fm = g2.getFontMetrics();

            // headers
            g2.setColor(Color.BLACK);
            for (int j = 0; j < n; j++) {
                String header = String.valueOf(j+1);
                int hx = startX + j * cellW + (cellW - fm.stringWidth(header)) / 2;
                int hy = startY - 8;
                g2.drawString(header, hx, hy);
            }
            for (int i = 0; i < n; i++) {
                String header = String.valueOf(i+1);
                int hx = startX - 16;
                int hy = startY + i * cellH + (cellH + fm.getAscent()) / 2 - 2;
                g2.drawString(header, hx, hy);
            }

            // cells
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int x = startX + j * cellW;
                    int y = startY + i * cellH;
                            int val = matrix[i][j];
                            if (val == Integer.MAX_VALUE) g2.setColor(new Color(255, 230, 230));
                            else g2.setColor(new Color(230, 255, 230));
                            g2.fillRect(x, y, cellW, cellH);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(x, y, cellW, cellH);
                            String txt;
                            if (val == Integer.MAX_VALUE) txt = "∞";
                            else txt = String.valueOf(val);
                            int tx = x + (cellW - fm.stringWidth(txt)) / 2;
                            int ty = y + (cellH + fm.getAscent()) / 2 - 2;
                            g2.setColor(Color.DARK_GRAY);
                            g2.drawString(txt, tx, ty);
                }
            }
        }
    }
}
