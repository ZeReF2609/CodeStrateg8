package com.algoritmoscurso.view.semana4.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Simple visualizer for recursion algorithms: shows an array as bars and highlights
 * ranges / pivot / mid positions. Includes an inner Snapshot POJO used by controller.
 */
public class RecursionVisualizer extends JPanel {
    public static class Snapshot {
        public final int[] array;
        public final int low;
        public final int high;
        public final int pivot; // -1 if none
        public final int left; // for binary search
        public final int right;
        public final int mid;
        public final String note;

        public Snapshot(int[] array, int low, int high, int pivot, int left, int right, int mid, String note) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.pivot = pivot;
            this.left = left;
            this.right = right;
            this.mid = mid;
            this.note = note;
        }
    }

    private int[] array = new int[0];
    private Snapshot current;

    public RecursionVisualizer() {
        setPreferredSize(new Dimension(520, 200));
        setBackground(Color.WHITE);
    }

    public void setArray(int[] arr) {
        if (arr == null) arr = new int[0];
        this.array = arr.clone();
        this.current = null;
        repaint();
    }

    public void showSnapshot(Snapshot s) {
        if (s == null) return;
        this.array = s.array.clone();
        this.current = s;
        repaint();
    }

    public void animateSnapshots(List<Snapshot> snapshots, int delayMs) {
        if (snapshots == null || snapshots.isEmpty()) return;
        new Thread(() -> {
            for (Snapshot s : snapshots) {
                try {
                    SwingUtilities.invokeAndWait(() -> showSnapshot(s));
                } catch (Exception ex) {
                    // fallback
                    showSnapshot(s);
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
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();

        if (array == null || array.length == 0) {
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("Sin datos", w/2 - 20, h/2);
            g2.dispose();
            return;
        }

        int n = array.length;
        int padding = 10;
        int availW = w - padding * 2;
        int barW = Math.max(4, availW / n);
        int maxVal = Integer.MIN_VALUE;
        for (int v : array) if (v > maxVal) maxVal = v;
        if (maxVal <= 0) maxVal = 1;

        for (int i = 0; i < n; i++) {
            int x = padding + i * barW;
            int barH = (int) ((h - 40) * ((double) array[i] / maxVal));
            int y = h - 30 - barH;

            // default color
            Color fill = new Color(120, 140, 190);
            if (current != null) {
                if (i >= current.low && i <= current.high) {
                    fill = new Color(200, 220, 255);
                }
                if (current.pivot >= 0 && i == current.pivot) {
                    fill = new Color(220, 100, 100);
                }
                if (current.mid >= 0 && i == current.mid) {
                    fill = new Color(250, 200, 80);
                }
            }

            g2.setColor(fill);
            g2.fillRect(x, y, barW - 2, barH);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRect(x, y, barW - 2, barH);

            // draw value
            g2.setFont(new Font("Arial", Font.PLAIN, Math.max(10, barW/3)));
            String txt = String.valueOf(array[i]);
            int tw = g2.getFontMetrics().stringWidth(txt);
            g2.drawString(txt, x + (barW - tw)/2 - 1, y - 4);
        }

        // draw note
        if (current != null && current.note != null) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.drawString(current.note, 10, 16);
        }

        g2.dispose();
    }
}
