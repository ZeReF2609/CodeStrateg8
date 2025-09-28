package com.algoritmoscurso.view.semana5;

import com.algoritmoscurso.model.dp.DPResult;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Simplified Knapsack view: input fields on top, a JTable that displays the DP table,
 * and simple controls to step through snapshots or autoplay.
 */
public class KnapsackView extends JPanel {
    private JTextField weightsField; // comma-separated
    private JTextField valuesField;
    private JSpinner capacitySpinner;
    private JButton runButton;
    private JLabel infoLabel;

    private JTable table;
    private JScrollPane tableScroll;
    private JButton prevBtn, nextBtn, playBtn, stopBtn;
    private Timer playTimer;
    private int currentIndex = 0;
    private List<int[]> snapshots;
    // cols/rows handled by model

    public KnapsackView() {
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.gridx = 0; gbc.gridy = 0; top.add(new JLabel("Pesos (coma):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; weightsField = new JTextField("1,2,5,6,7",20); top.add(weightsField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; top.add(new JLabel("Valores (coma):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; valuesField = new JTextField("1,6,18,22,28",20); top.add(valuesField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; top.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; capacitySpinner = new JSpinner(new SpinnerNumberModel(11, 1, 100, 1)); top.add(capacitySpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; runButton = new JButton("Resolver Mochila 0/1"); top.add(runButton, gbc);

        add(top, BorderLayout.NORTH);

        // Center: table
        table = new JTable();
        table.setFillsViewportHeight(true);
        tableScroll = new JScrollPane(table);
        add(tableScroll, BorderLayout.CENTER);

        // Bottom: controls and info
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        prevBtn = new JButton("Prev");
        nextBtn = new JButton("Next");
        playBtn = new JButton("Play");
        stopBtn = new JButton("Stop");
        prevBtn.setEnabled(false); nextBtn.setEnabled(false); playBtn.setEnabled(false); stopBtn.setEnabled(false);
        controls.add(prevBtn); controls.add(nextBtn); controls.add(playBtn); controls.add(stopBtn);
        bottom.add(controls, BorderLayout.NORTH);

        infoLabel = new JLabel("Resultado:");
        bottom.add(infoLabel, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        // Timer for autoplay (300ms step)
        playTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snapshots == null) return;
                if (currentIndex < snapshots.size() - 1) {
                    showIndex(currentIndex + 1);
                } else {
                    playTimer.stop();
                }
            }
        });

        // Button actions
        prevBtn.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { showIndex(currentIndex - 1); } });
        nextBtn.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { showIndex(currentIndex + 1); } });
        playBtn.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { playTimer.start(); playBtn.setEnabled(false); stopBtn.setEnabled(true); } });
        stopBtn.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { playTimer.stop(); playBtn.setEnabled(true); stopBtn.setEnabled(false); } });
    }

    public void setRunListener(ActionListener l) { runButton.addActionListener(l); }
    public String getWeights() { return weightsField.getText().trim(); }
    public String getValues() { return valuesField.getText().trim(); }
    public int getCapacity() { return (Integer) capacitySpinner.getValue(); }

    public void showResult(DPResult r) {
        if (r == null) return;
        snapshots = r.getSnapshots();
        if (snapshots == null || snapshots.isEmpty()) {
            infoLabel.setText("No hay snapshots disponibles. Mejor valor: " + r.getBestValue());
            return;
        }
        // snapshots are flattened matrices with rows = n+1, cols = W+1
        int capacity = getCapacity();
        int[] weights = parseInts(getWeights());
        int[] values = parseInts(getValues());
    int n = weights.length;

        FullDPTableModel model = new FullDPTableModel(n, capacity, weights, values, snapshots);
        table.setModel(model);
        table.setRowHeight(36);
        currentIndex = snapshots.size() - 1; // show final snapshot by default
        model.setSnapshotIndex(currentIndex);

        prevBtn.setEnabled(true); nextBtn.setEnabled(true); playBtn.setEnabled(true); stopBtn.setEnabled(false);
        infoLabel.setText(String.format("Mejor valor: %d | Items: %s | Paso: %d/%d", r.getBestValue(), r.getSelectedItems(), currentIndex + 1, snapshots.size()));

        // Show dialog with final result and selected items (1-based)
        java.util.List<Integer> sel = r.getSelectedItems();
        StringBuilder dlg = new StringBuilder();
        dlg.append("Mejor valor: ").append(r.getBestValue()).append("\nItems seleccionados: ");
        if (sel == null || sel.isEmpty()) dlg.append("(ninguno)");
        else {
            for (int i = 0; i < sel.size(); i++) {
                int idx = sel.get(i) + 1; // 1-based
                dlg.append(idx);
                if (i < sel.size() - 1) dlg.append(", ");
            }
        }
        JOptionPane.showMessageDialog(this, dlg.toString(), "Resultado Mochila 0/1", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showIndex(int idx) {
        if (snapshots == null || snapshots.isEmpty()) return;
        if (idx < 0) idx = 0;
        if (idx > snapshots.size() - 1) idx = snapshots.size() - 1;
        currentIndex = idx;
        // update model snapshot index
        if (table.getModel() instanceof FullDPTableModel) {
            FullDPTableModel m = (FullDPTableModel) table.getModel();
            m.setSnapshotIndex(currentIndex);
            table.repaint();
        }
        infoLabel.setText(String.format("Paso: %d / %d", currentIndex + 1, snapshots.size()));
    }

    // helper removed: model handles unflattening
    // helper to parse comma-separated ints
    private int[] parseInts(String s) {
        String[] parts = s.split(",");
        int[] out = new int[parts.length];
        int idx = 0;
        for (String p : parts) {
            String t = p.trim(); if (t.isEmpty()) continue;
            out[idx++] = Integer.parseInt(t);
        }
        if (idx != out.length) {
            int[] b = new int[idx]; System.arraycopy(out, 0, b, 0, idx); return b;
        }
        return out;
    }

    /**
     * FullDPTableModel shows a row per etapa (i) with columns: ETAPA, ARTICULO, VOLUMEN, BENEFICIO
     * and then capacity columns 0..W showing the dp value and a small label with chosen items (A,B,C...)
     */
    private class FullDPTableModel extends AbstractTableModel {
        private int n; private int W;
        private int[] weights; private int[] values;
        private java.util.List<int[]> snaps;
        private int snapIndex = 0;
        private String[][] labels; // labels per cell (n+1) x (W+1)

        public FullDPTableModel(int nItems, int capacity, int[] weights, int[] values, java.util.List<int[]> snapshots) {
            this.n = nItems;
            this.W = capacity;
            this.weights = weights;
            this.values = values;
            this.snaps = snapshots;
        }

        public void setSnapshotIndex(int idx) { this.snapIndex = idx; fireTableStructureChanged(); }

        private void computeLabels() {
            // build dp table from snapshot
            int[] flat = snaps.get(Math.max(0, Math.min(snapIndex, snaps.size() - 1)));
            int rows = n + 1; int cols = W + 1;
            int[][] dp = new int[rows][cols];
            for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++) dp[i][j] = flat[i * cols + j];

            labels = new String[rows][cols];
            for (int j = 0; j < cols; j++) labels[0][j] = "";

            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (dp[i][j] == dp[i-1][j]) {
                        labels[i][j] = labels[i-1][j];
                    } else {
                        int w = weights[i-1];
                        int prevCap = j - w;
                        String prevLabel = "";
                        if (prevCap >= 0) prevLabel = labels[i-1][prevCap];
                        String thisLabel = String.valueOf((char)('A' + i - 1));
                        if (prevLabel == null || prevLabel.isEmpty()) labels[i][j] = thisLabel;
                        else labels[i][j] = prevLabel + "+" + thisLabel;
                    }
                }
            }
        }

        @Override public int getRowCount() { return n + 1; }
        @Override public int getColumnCount() { return 4 + (W + 1); }

        @Override public String getColumnName(int col) {
            if (col == 0) return "ETAPA";
            if (col == 1) return "ARTICULO";
            if (col == 2) return "VOLUMEN(L)";
            if (col == 3) return "BENEFICIO(S/.)";
            return String.valueOf(col - 4);
        }

        @Override public Object getValueAt(int row, int col) {
            if (col == 0) return row; // etapa
            if (col == 1) return row == 0 ? "-" : (char)('A' + row - 1);
            if (col == 2) return row == 0 ? "ESTADO INICIAL" : weights[row - 1];
            if (col == 3) return row == 0 ? "" : values[row - 1];

            // capacity columns: show dp value and a short label (we compute from snapshot)
            int cap = col - 4;
            int[] flat = snaps.get(Math.max(0, Math.min(snapIndex, snaps.size() - 1)));
            int cols = W + 1;
            int val = flat[row * cols + cap];
            // ensure labels computed
            if (labels == null) computeLabels();
            String lab = labels[row][cap];
            if (lab == null) lab = "";
            // render as HTML: value on top, label in red small text below
            String html = "<html><div style='text-align:center'>" + val + "<br/><span style='color:#c00;font-size:10px;'>" + (lab.isEmpty() ? "" : lab) + "</span></div></html>";
            return html;
        }
    }
}
