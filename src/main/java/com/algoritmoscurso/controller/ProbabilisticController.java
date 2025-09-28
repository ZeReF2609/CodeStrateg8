package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.model.probability.ProbabilityModel;
import com.algoritmoscurso.view.semana6.ProbabilisticView;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/** Controller exposing a small UI for probabilistic algorithms (Semana 6) */
public class ProbabilisticController implements IController {
    private final ProbabilityModel model = new ProbabilityModel();
    private final ProbabilisticView view = new ProbabilisticView();

    public ProbabilisticController() {
        setupListeners();
    }

    // --- Monte Carlo Pi simulation state ---
    private volatile boolean piRunning = false;
    private volatile boolean piPaused = false;
    private Thread piThread;

    // --- Miller-Rabin state ---
    private volatile boolean mrAuto = false;
    private long mrN = 0L;
    private int mrRounds = 0;
    private List<String> mrSteps = new ArrayList<>();
    private int mrStepIndex = 0;

    // --- Monte Carlo Pi control methods ---
    private void startPiSimulation() {
        if (piRunning) return;
        piRunning = true; piPaused = false;
        view.appendPiOutput("Iniciando simulación Pi...");
        piThread = new Thread(new Runnable() {
            @Override public void run() {
                try {
                    final int samples = Integer.parseInt(view.piSamplesField.getText().trim());
                    long inside = 0;
                    List<Double> partial = new ArrayList<>();
                    for (int i = 1; i <= samples && piRunning; i++) {
                        while (piPaused) {
                            Thread.sleep(100);
                            if (!piRunning) break;
                        }
                        final double x = Math.random(); final double y = Math.random();
                        final boolean in = (x*x + y*y <= 1.0);
                        if (in) inside++;
                        if (i % Math.max(1, samples / 200) == 0) {
                            final int idx = i;
                            final double est = 4.0 * ((double) inside / (double) i);
                            partial.add(est);
                            final double fx = x, fy = y; final boolean fin = in;
                            SwingUtilities.invokeLater(new Runnable() { public void run() {
                                view.addPiPoint(fx, fy, fin);
                                view.appendPiOutput(String.format("n=%d -> %.6f", idx, est));
                                view.setPiConvergenceSeries(partial);
                            }});
                        }
                    }
                    final double piEst = 4.0 * ((double) inside / (double) Math.max(1, Integer.parseInt(view.piSamplesField.getText().trim())));
                    SwingUtilities.invokeLater(new Runnable() { public void run() { view.appendPiOutput(String.format("Estimación final Pi = %.6f", piEst)); }});
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(new Runnable() { public void run() { javax.swing.JOptionPane.showMessageDialog(view, "Error Pi sim: " + ex.getMessage()); }});
                } finally {
                    piRunning = false; piThread = null;
                }
            }
        });
        piThread.start();
    }

    private void pausePiSimulation() {
        if (!piRunning) return;
        piPaused = !piPaused;
        view.appendPiOutput(piPaused ? "Simulación en pausa" : "Continuando simulación");
    }

    private void resetPiSimulation() {
        piRunning = false; piPaused = false;
        if (piThread != null) { try { piThread.join(100); } catch (InterruptedException ignored) {} }
    view.appendPiOutput("Simulación reiniciada");
    view.setPiConvergenceSeries(new ArrayList<Double>());
    // clear scatter and outputs via view API
    view.clearPiScatter();
    view.setPiOutput("");
    }

    // --- Miller-Rabin interactive methods ---
    private void mrReset() {
        mrAuto = false; mrSteps.clear(); mrStepIndex = 0; mrN = 0; mrRounds = 0;
        view.appendMrOutput("Estado reiniciado");
        view.setMrSteps(new ArrayList<String>());
    }

    private void mrStep() {
        try {
            if (mrSteps.isEmpty()) {
                // initialize steps from input
                mrN = Long.parseLong(view.mrNField.getText().trim());
                mrRounds = Integer.parseInt(view.mrRoundsField.getText().trim());
                mrSteps = generateMrSteps(mrN, mrRounds);
                view.setMrSteps(mrSteps);
                view.appendMrOutput("Pasos generados. Pulse 'Siguiente paso' para avanzar.");
                mrStepIndex = 0;
                return;
            }
            if (mrStepIndex < mrSteps.size()) {
                String s = mrSteps.get(mrStepIndex++);
                view.appendMrOutput(String.format("Paso %d: %s", mrStepIndex, s));
            } else {
                view.appendMrOutput("No hay más pasos.");
            }
        } catch (Exception ex) { javax.swing.JOptionPane.showMessageDialog(view, "Error en MR paso: " + ex.getMessage()); }
    }

    private void mrAutoToggle() {
        mrAuto = !mrAuto;
        view.appendMrOutput(mrAuto ? "Auto ON" : "Auto OFF");
        if (mrAuto) {
            new Thread(new Runnable() {
                @Override public void run() {
                    while (mrAuto) {
                        mrStep();
                        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
                    }
                }
            }).start();
        }
    }

    // Create a list of textual steps that explain Miller-Rabin computation for display
    private List<String> generateMrSteps(long n, int rounds) {
        List<String> out = new ArrayList<>();
        if (n < 2) { out.add("n < 2 -> no primo"); return out; }
        long d = n - 1; int s = 0; while ((d & 1) == 0) { d >>= 1; s++; }
        out.add(String.format("Descomposición: n-1 = 2^%d * %d", s, d));
        for (int r = 0; r < rounds; r++) {
            long a = 2 + (Math.abs(new java.util.Random().nextLong()) % Math.max(2, n - 3));
            out.add(String.format("Ronda %d: base a = %d", r+1, a));
            long x = modPow(a, d, n);
            out.add(String.format("a^d mod n = %d", x));
            if (x == 1 || x == n-1) { out.add("Test passed for esta base (x == 1 o x == n-1)"); continue; }
            for (int j = 1; j < s; j++) {
                x = mulMod(x, x, n);
                out.add(String.format("Square %d: %d", j, x));
                if (x == n-1) { out.add("Se alcanzó n-1 -> esta base no prueba compositividad"); break; }
            }
        }
        out.add("Fin de las rondas (resumen: si alguna base falla -> compuesto)");
        return out;
    }

    // local modular helpers (to avoid using non-public model methods)
    private long mulMod(long a, long b, long mod) {
        return (a * b) % mod;
    }

    private long modPow(long base, long exp, long mod) {
        long result = 1 % mod;
        long b = base % mod;
        long e = exp;
        while (e > 0) {
            if ((e & 1) == 1) result = mulMod(result, b, mod);
            b = mulMod(b, b, mod);
            e >>= 1;
        }
        return result;
    }

    private void setupListeners() {
        // Monte Carlo Pi: Start / Pause / Reset
    view.piStartButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { startPiSimulation(); } });
    view.piPauseButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { pausePiSimulation(); } });
    view.piResetButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { resetPiSimulation(); } });

        // Miller-Rabin: step / auto / reset
    view.mrStepButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { mrStep(); } });
    view.mrAutoButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { mrAutoToggle(); } });
    view.mrResetButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { mrReset(); } });

        // (Integration UI removed) the view focuses on Monte Carlo Pi and Miller-Rabin
    }

    @Override public void initialize() {
        // Populate the view with a short demo so the user sees data immediately
    view.appendPiOutput("Bienvenido a los Algoritmos Probabilísticos - demo inicial");
    view.appendPiOutput("Se mostrarán estimaciones rápidas para que la vista no quede vacía.");

        // Run a small demo off the EDT to avoid blocking UI
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    // Quick Pi demo
                    int piSamples = 2000;
                    long inside = 0;
                    List<Double> seq = new ArrayList<>();
                    for (int i = 1; i <= piSamples; i++) {
                        double x = Math.random();
                        double y = Math.random();
                        if (x * x + y * y <= 1.0) inside++;
                        if (i % Math.max(1, piSamples / 200) == 0) seq.add(4.0 * ((double) inside / (double) i));
                    }
                    final double piEst = 4.0 * ((double) inside / (double) piSamples);
                    final List<Double> plot = new ArrayList<>(seq);

                    // Quick integration demo (polynomial x^3+2x-3 on [0,1])
                    final String poly = "x^3+2x-3";
                    final double a = 0.0, b = 1.0;
                    int intSamples = 2000;
                    double sum = 0.0;
                    List<Double> partial = new ArrayList<>();
                    double[] coeffs = model.parsePolynomial(poly);
                    for (int i = 1; i <= intSamples; i++) {
                        double x = a + Math.random() * (b - a);
                        sum += model.evaluatePolynomial(coeffs, x);
                        if (i % Math.max(1, intSamples / 200) == 0) partial.add((b - a) * (sum / (double) i));
                    }
                    final double mcInt = (b - a) * (sum / (double) intSamples);
                    final double analytic = model.analyticIntegralPolynomial(coeffs, a, b);

                    // Update UI on EDT
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() {
                            view.appendPiOutput(String.format("Demo Pi (n=%d): %.6f", piSamples, piEst));
                            view.appendPiOutput(String.format("(Demo) Integración polinomial en [%.1f,%.1f]: MonteCarlo=%.6f, Analítico=%.6f",
                                    a, b, mcInt, analytic));
                            view.setPiConvergenceSeries(plot);
                        }
                    });
                } catch (Exception ex) {
                    javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() {
                        javax.swing.JOptionPane.showMessageDialog(view, "Error en demo inicial: " + ex.getMessage());
                    }});
                }
            }
        }).start();
    }
    @Override public void handleEvent(String eventType, Object data) {}
    @Override public void updateModel(String action, Object... parameters) {}
    @Override public void updateView() {}

    public ProbabilisticView getView() { return view; }
}
