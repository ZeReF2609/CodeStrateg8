package com.algoritmoscurso.view.semana6;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para Semana 6: organizamos dos pestañas (Monte Carlo Pi y Miller-Rabin)
 * La vista expone componentes públicos mínimos que utiliza el controlador para
 * ejecutar y mostrar resultados y explicaciones.
 */
public class ProbabilisticView extends JPanel {
    // --- Monte Carlo Pi UI components ---
    public final JTextField piSamplesField = new JTextField("10000", 8);
    public final JButton piStartButton = new JButton("Iniciar");
    public final JButton piPauseButton = new JButton("Pausa");
    public final JButton piResetButton = new JButton("Reiniciar");
    public final JTextPane piExplanation = new JTextPane();
    public final JTextArea piOutput = new JTextArea();
    public final ScatterPanel piScatter = new ScatterPanel();
    public final ConvergencePanel piConvergence = new ConvergencePanel();

    // --- Miller-Rabin UI components ---
    public final JTextField mrNField = new JTextField("32416190071", 14);
    public final JTextField mrRoundsField = new JTextField("8", 4);
    public final JButton mrStepButton = new JButton("Siguiente paso");
    public final JButton mrAutoButton = new JButton("Auto");
    public final JButton mrResetButton = new JButton("Reiniciar");
    public final JTextPane mrExplanation = new JTextPane();
    public final JTextArea mrOutput = new JTextArea();
    public final StepsPanel mrStepsPanel = new StepsPanel();

    private final JTabbedPane tabs = new JTabbedPane();

    public ProbabilisticView() {
        super(new BorderLayout(6,6));
        initUI();
    }

    private void initUI() {
        // Top help
        JLabel title = new JLabel("Algoritmos Probabilísticos — Semana 6");
        title.setBorder(BorderFactory.createEmptyBorder(4,6,4,6));
        add(title, BorderLayout.PAGE_START);

        // Monte Carlo tab
        JPanel monte = new JPanel(new BorderLayout(6,6));
        JPanel monteControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        monteControls.setBorder(BorderFactory.createTitledBorder("Estimación de Pi - Monte Carlo"));
        monteControls.add(new JLabel("Muestras:"));
        monteControls.add(piSamplesField);
        monteControls.add(piStartButton); monteControls.add(piPauseButton); monteControls.add(piResetButton);
        monte.add(monteControls, BorderLayout.NORTH);

        JSplitPane mcSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        // left: scatter + convergence vertically
        JPanel left = new JPanel(new BorderLayout(4,4));
        left.add(new JScrollPane(piOutput), BorderLayout.SOUTH);
        left.add(piScatter, BorderLayout.CENTER);
        left.setPreferredSize(new Dimension(420, 420));
        mcSplit.setLeftComponent(left);

        // right: convergence and explanation
        JPanel right = new JPanel(new BorderLayout(4,4));
        right.add(piConvergence, BorderLayout.CENTER);
        piExplanation.setContentType("text/html");
        piExplanation.setText(getDefaultPiExplanation());
        piExplanation.setEditable(false);
        right.add(new JScrollPane(piExplanation), BorderLayout.SOUTH);
        mcSplit.setRightComponent(right);
        mcSplit.setResizeWeight(0.6);
        monte.add(mcSplit, BorderLayout.CENTER);

        // Miller-Rabin tab
        JPanel mr = new JPanel(new BorderLayout(6,6));
        JPanel mrControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mrControls.setBorder(BorderFactory.createTitledBorder("Test de Primalidad (Miller-Rabin)"));
        mrControls.add(new JLabel("Número n:")); mrControls.add(mrNField);
        mrControls.add(new JLabel("Rondas:")); mrControls.add(mrRoundsField);
        mrControls.add(mrStepButton); mrControls.add(mrAutoButton); mrControls.add(mrResetButton);
        mr.add(mrControls, BorderLayout.NORTH);

        JSplitPane mrSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mrSplit.setLeftComponent(mrStepsPanel);
        mrSplit.setRightComponent(new JScrollPane(mrOutput));
        mr.add(mrSplit, BorderLayout.CENTER);
        mrExplanation.setContentType("text/html");
        mrExplanation.setText(getDefaultMrExplanation());
        mrExplanation.setEditable(false);
        mr.add(new JScrollPane(mrExplanation), BorderLayout.SOUTH);

        // Add tabs
        tabs.addTab("Monte Carlo (Pi)", monte);
        tabs.addTab("Miller-Rabin", mr);

        add(tabs, BorderLayout.CENTER);

        // initial sizes and defaults
        piOutput.setEditable(false);
        mrOutput.setEditable(false);
        piScatter.setPreferredSize(new Dimension(420, 360));
        piConvergence.setPreferredSize(new Dimension(420, 360));
        mrStepsPanel.setPreferredSize(new Dimension(420, 420));
    }

    // Small html explanations
    private String getDefaultPiExplanation() {
        return "<html><b>Por qué Monte Carlo para Pi?</b><br>"+
                "Generamos puntos uniformes en el cuadrado [0,1]^2. La fracción dentro del cuarto de círculo de radio 1 " +
                "aproxima el área del cuarto de círculo (PI/4). Multiplicando por 4 obtenemos Pi. " +
                "Aquí mostramos puntos y la convergencia de la estimación.</html>";
    }

    private String getDefaultMrExplanation() {
        return "<html><b>Por qué Miller-Rabin?</b><br>Este es un test probabilístico que verifica si n es compuesto encontrando un testigo 'a'. " +
                "Se descompone n-1 = 2^s * d y se prueban bases aleatorias. Si alguna base muestra evidencia de compuesto, n no es primo. " +
                "Mostramos los pasos (a^d mod n, luego cuadrados sucesivos) para entender por qué.</html>";
    }

    // Methods to update view panels from controller
    public void setPiOutput(String text) { piOutput.setText(text); }
    public void appendPiOutput(String line) { piOutput.append(line + "\n"); }
    public void setPiConvergenceSeries(List<Double> s) { piConvergence.setSeries(s); }
    public void addPiPoint(double x, double y, boolean inside) { piScatter.addPoint(x, y, inside); }
    public void clearPiScatter() { piScatter.clearPoints(); }

    public void appendMrOutput(String line) { mrOutput.append(line + "\n"); }
    public void setMrSteps(List<String> steps) { mrStepsPanel.setSteps(steps); }

    // --- Simple drawing panels used by the view ---
    private static class ScatterPanel extends JPanel {
        private final java.util.List<PointData> points = new ArrayList<>();

        public void addPoint(double x, double y, boolean inside) {
            points.add(new PointData(x, y, inside));
            repaint();
        }

        public void clearPoints() {
            points.clear();
            repaint();
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth(), h = getHeight();
            Graphics2D g2 = (Graphics2D) g;
            // background
            g2.setColor(Color.WHITE); g2.fillRect(0,0,w,h);
            // draw quarter circle
            g2.setColor(new Color(230,230,255));
            g2.fillOval(0, 0, Math.min(w, h)*2, Math.min(w,h)*2); // generous
            // draw points
            for (PointData p : points) {
                int px = (int) (p.x * w);
                int py = (int) (p.y * h);
                g2.setColor(p.inside ? Color.BLUE : Color.RED);
                g2.fillOval(px-2, py-2, 4, 4);
            }
            // axes
            g2.setColor(Color.GRAY);
            g2.drawRect(0,0,w-1,h-1);
        }

        private static class PointData { double x,y; boolean inside; PointData(double x,double y,boolean in){this.x=x;this.y=y;this.inside=in;} }
    }

    private static class ConvergencePanel extends JPanel {
        private List<Double> series = new ArrayList<>();
        public void setSeries(List<Double> s) { this.series = new ArrayList<>(s); repaint(); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth(), h = getHeight(); if (w<=0||h<=0) return;
            Graphics2D g2 = (Graphics2D) g; g2.setColor(Color.WHITE); g2.fillRect(0,0,w,h);
            if (series == null || series.size()<2) return;
            double min=Double.POSITIVE_INFINITY,max=Double.NEGATIVE_INFINITY; for (Double v:series){min=Math.min(min,v);max=Math.max(max,v);} if (min==max){min-=1;max+=1;}
            int n=series.size(); int ox=20, oy=10, plotW=w-ox-10, plotH=h-oy-30;
            g2.setColor(Color.BLUE); int px=-1,py=-1; for(int i=0;i<n;i++){ double v=series.get(i); int x=ox+(int)((i/(double)(n-1))*plotW); int y=oy+(int)((max-v)/(max-min)*plotH); if(px!=-1) g2.drawLine(px,py,x,y); px=x; py=y; }
            g2.setColor(Color.BLACK); g2.drawString(String.format("%.4f", max), 2, 12); g2.drawString(String.format("%.4f", min), 2, h-18);
        }
    }

    // Panel to show steps of Miller-Rabin (simple list)
    private static class StepsPanel extends JPanel {
        private final DefaultListModel<String> model = new DefaultListModel<>();
        private final JList<String> list = new JList<>(model);
        public StepsPanel(){ setLayout(new BorderLayout()); add(new JScrollPane(list), BorderLayout.CENTER); }
        public void setSteps(java.util.List<String> steps){ model.clear(); for(String s:steps) model.addElement(s); }
    }
}
