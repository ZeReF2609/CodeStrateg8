package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.model.backtracking.BacktrackingModel;
import com.algoritmoscurso.model.backtracking.BacktrackingResult;
import com.algoritmoscurso.model.backtracking.KnightTourSolver;
import com.algoritmoscurso.view.semana4.BacktrackingGraphView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para algoritmos de backtracking - Semana 4
 */
public class BacktrackingController implements IController {
    private BacktrackingModel model;
    private BacktrackingGraphView view;

    public BacktrackingController() {
        this.model = new BacktrackingModel();
        this.view = new BacktrackingGraphView();
        initialize();
    }

    @Override
    public void initialize() {
        setupEventListeners();
        model.initialize();
    }

    private void setupEventListeners() {
        // Listener para Tour del Caballo
        view.setKnightTourListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeKnightTour();
            }
        });

        // Listener para Torres de Hanoi
        view.setHanoiListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeHanoi();
            }
        });

        // Listener para limpiar resultados
        view.setClearListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResults();
            }
        });
    }

    private void executeKnightTour() {
        try {
            int boardSize = view.getBoardSize();
            int startX = view.getStartX();
            int startY = view.getStartY();
            
            // Validaciones
            if (boardSize < 4 || boardSize > 12) {
                view.showError("El tamaño del tablero debe estar entre 4 y 12");
                return;
            }
            
            if (startX < 0 || startX >= boardSize || startY < 0 || startY >= boardSize) {
                view.showError("La posición inicial debe estar dentro del tablero");
                return;
            }
            
            view.showMessage("Ejecutando Tour del Caballo...");
            // Asegurar que el visualizador esté en el tamaño correcto (UI usa X=col, Y=row)
            view.getKnightVisualizer().setSizeAndReset(boardSize);
            
            // Ejecutar el algoritmo en un hilo separado para no bloquear la UI.
            // Usamos KnightTourSolver para emitir snapshots en streaming hacia el visualizador
            // Disable controls while running
            javax.swing.SwingUtilities.invokeLater(() -> view.setControlsEnabled(false));

            new Thread(() -> {
                try {
                    KnightTourSolver solver = new KnightTourSolver();
                    // Consumer que recibe snapshots y actualiza el visualizador en EDT
                    java.util.function.Consumer<int[]> onSnapshot = flat -> {
                        int size2 = (int) Math.sqrt(flat.length);
                        int[][] boardM = new int[size2][size2];
                        for (int i = 0; i < flat.length; i++) boardM[i / size2][i % size2] = flat[i];
                        javax.swing.SwingUtilities.invokeLater(() -> view.getKnightVisualizer().setBoard(boardM));
                    };

                    BacktrackingResult result = solver.solve(boardSize, startY, startX, onSnapshot);
                    
                    // Al terminar, actualizar la vista con el resultado final (en EDT)
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        view.displayResult(result);
                        if (result.getMoves().isEmpty()) {
                            view.showError("No se encontró solución para el Tour del Caballo");
                        } else {
                            view.showMessage("Tour del Caballo completado (parcial/total)");
                        }
                        // re-enable controls
                        view.setControlsEnabled(true);
                    });
                } catch (Exception ex) {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        view.showError("Error ejecutando Tour del Caballo: " + ex.getMessage());
                        view.setControlsEnabled(true);
                    });
                }
            }).start();
            
        } catch (Exception ex) {
            view.showError("Error al iniciar Tour del Caballo: " + ex.getMessage());
        }
    }

    private void executeHanoi() {
        try {
            int disks = view.getDisksCount();
            
            if (disks < 2 || disks > 8) {
                view.showError("El número de discos debe estar entre 2 y 8");
                return;
            }
            
            view.showMessage("Ejecutando Torres de Hanoi...");
            
            // Ejecutar el algoritmo en un hilo separado
            SwingWorker<BacktrackingResult, Void> worker = new SwingWorker<BacktrackingResult, Void>() {
                @Override
                protected BacktrackingResult doInBackground() throws Exception {
                    return model.solveTowerOfHanoi(disks);
                }
                
                @Override
                protected void done() {
                    try {
                        BacktrackingResult result = get();
                        view.displayResult(result);
                        
                        if (result.getMoves().isEmpty()) {
                            view.showError("Error resolviendo Torres de Hanoi");
                        } else {
                            view.showMessage("Torres de Hanoi resuelto en " + 
                                           result.getMoves().size() + " movimientos");
                            // Convertir movimientos a pares [from,to] y animar
                            java.util.List<int[]> parsed = new java.util.ArrayList<>();
                            for (String mv : result.getMoves()) {
                                // Esperamos formatos como "Mover disco X de A a C" o "Mover disco 1 de A a C"
                                String lower = mv.toLowerCase();
                                if (lower.contains(" de ") && lower.contains(" a ")) {
                                    try {
                                        String part = lower.substring(lower.indexOf(" de ") + 4);
                                        String[] sides = part.split(" a ");
                                        if (sides.length >= 2) {
                                            int f = letterToIndex(sides[0].trim());
                                            int t = letterToIndex(sides[1].trim());
                                            if (f >= 0 && t >= 0) parsed.add(new int[] {f, t});
                                        }
                                    } catch (Exception ex) {
                                        // ignore
                                    }
                                }
                            }
                            if (!parsed.isEmpty()) {
                                view.getHanoiVisualizer().setNumberOfDisks(view.getDisksCount());
                                view.getHanoiVisualizer().animateMoves(parsed, 180);
                            }
                        }
                        
                    } catch (Exception ex) {
                        view.showError("Error ejecutando Torres de Hanoi: " + ex.getMessage());
                    }
                }
            };
            
            worker.execute();
            
        } catch (Exception ex) {
            view.showError("Error al iniciar Torres de Hanoi: " + ex.getMessage());
        }
    }

    private void clearResults() {
        view.clearResults();
        view.showMessage("Resultados limpiados");
    }

    @Override
    public void handleEvent(String eventType, Object data) {
        switch (eventType) {
            case "KNIGHT_TOUR":
                executeKnightTour();
                break;
            case "HANOI":
                executeHanoi();
                break;
            case "CLEAR":
                clearResults();
                break;
            default:
                view.showError("Evento no reconocido: " + eventType);
        }
    }

    @Override
    public void updateModel(String action, Object... parameters) {
        try {
            switch (action) {
                case "KNIGHT_TOUR":
                    if (parameters.length >= 3) {
                        BacktrackingResult result = model.solveKnightTour(
                            (Integer) parameters[0], 
                            (Integer) parameters[1], 
                            (Integer) parameters[2]
                        );
                        view.displayResult(result);
                    }
                    break;
                case "HANOI":
                    if (parameters.length >= 1) {
                        BacktrackingResult result = model.solveTowerOfHanoi((Integer) parameters[0]);
                        view.displayResult(result);
                    }
                    break;
                default:
                    view.showError("Acción no reconocida: " + action);
            }
        } catch (Exception e) {
            view.showError("Error al actualizar modelo: " + e.getMessage());
        }
    }

    private static int letterToIndex(String s) {
        if (s == null || s.isEmpty()) return -1;
        char c = Character.toUpperCase(s.charAt(0));
        if (c == 'A') return 0;
        if (c == 'B') return 1;
        if (c == 'C') return 2;
        // attempt to parse numeric
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                int num = Character.getNumericValue(ch);
                if (num >= 1 && num <= 3) return num - 1;
            }
        }
        return -1;
    }

    @Override
    public void updateView() {
        // La vista se actualiza principalmente a través de los resultados
        view.showMessage("Vista actualizada");
    }

    /**
     * Obtiene la vista asociada a este controlador.
     * @return panel de la vista
     */
    public JPanel getView() {
        return view;
    }

    public BacktrackingModel getModel() {
        return model;
    }
}