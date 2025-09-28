package com.algoritmoscurso.view.semana5;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DynamicProgrammingView extends JPanel implements IView {
    private KnapsackView knapsackView;
    private FloydView floydView;

    public DynamicProgrammingView() {
        setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        knapsackView = new KnapsackView();
        floydView = new FloydView();
        tabs.addTab("Mochila (0/1)", knapsackView);
        tabs.addTab("Floyd-Warshall", floydView);
        add(tabs, BorderLayout.CENTER);
    }

    public void setKnapsackListener(ActionListener l) { knapsackView.setRunListener(l); }
    public void setFloydListener(ActionListener l) { floydView.setRunListener(l); }

    public KnapsackView getKnapsackView() { return knapsackView; }
    public FloydView getFloydView() { return floydView; }

    @Override
    public void initializeView() { }

    @Override
    public void updateView(Object data) { }

    @Override
    public void showMessage(String message) { }

    @Override
    public void showError(String error) { JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE); }

    @Override
    public void setEnabled(boolean enabled) { super.setEnabled(enabled); }
}
