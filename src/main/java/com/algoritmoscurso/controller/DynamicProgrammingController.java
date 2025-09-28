package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.model.dp.DPResult;
import com.algoritmoscurso.model.dp.DynamicProgrammingModel;
import com.algoritmoscurso.view.semana5.DynamicProgrammingView;
import com.algoritmoscurso.view.semana5.KnapsackView;
import com.algoritmoscurso.view.semana5.FloydView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller that exposes the Dynamic Programming views and coordinates model <-> view.
 */
public class DynamicProgrammingController implements IController {
    private final DynamicProgrammingModel model = new DynamicProgrammingModel();
    private final DynamicProgrammingView view = new DynamicProgrammingView();
    private final KnapsackView knapsackView = view.getKnapsackView();
    private final FloydView floydView = view.getFloydView();

    public DynamicProgrammingController() {
        initialize();
    }

    @Override
    public void initialize() {
        // connect listeners
        view.setKnapsackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runKnapsack();
            }
        });
        view.setFloydListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runFloyd();
            }
        });
    }

    private void runKnapsack() {
        try {
            KnapsackView kv = view.getKnapsackView();
            int[] w = parseInts(kv.getWeights());
            int[] v = parseInts(kv.getValues());
            int cap = kv.getCapacity();
            DPResult r = model.solveKnapsack(w, v, cap);
            kv.showResult(r);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void runFloyd() {
        try {
            FloydView fv = view.getFloydView();
            String txt = fv.getMatrixText();
            String[] lines = txt.split("\\n");
            int n = lines.length;
            int[][] m = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] parts = lines[i].trim().split("\\s+");
                for (int j = 0; j < Math.min(parts.length, n); j++) {
                    m[i][j] = Integer.parseInt(parts[j]);
                }
            }
            DPResult r = model.solveFloyd(m);
            fv.showResult(r);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error parsando matriz: " + ex.getMessage());
        }
    }

    private int[] parseInts(String s) {
        String[] parts = s.split(",");
        int[] a = new int[parts.length];
        int idx = 0;
        for (String p : parts) {
            String t = p.trim();
            if (t.isEmpty()) continue;
            a[idx++] = Integer.parseInt(t);
        }
        if (idx != a.length) {
            int[] b = new int[idx]; System.arraycopy(a, 0, b, 0, idx); return b;
        }
        return a;
    }

    @Override
    public void handleEvent(String eventType, Object data) {
        // not used for now
    }

    @Override
    public void updateModel(String action, Object... parameters) {
        // not used for now
    }

    @Override
    public void updateView() {
        // not used for now
    }

    public JPanel getView() { return view; }

    public KnapsackView getKnapsackView() { return knapsackView; }

    public FloydView getFloydView() { return floydView; }
}
