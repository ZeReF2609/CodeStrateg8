package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.model.graph.GraphAlgorithmsModel;
import com.algoritmoscurso.model.graph.GraphResult;
import com.algoritmoscurso.view.semana4.GraphAlgorithmsView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador dinámico para algoritmos de grafos.
 */
public class GraphAlgorithmsController implements IController {
    private GraphAlgorithmsModel model;
    private GraphAlgorithmsView view;

    public GraphAlgorithmsController() {
        this.model = new GraphAlgorithmsModel();
        this.view = new GraphAlgorithmsView();
        initialize();
    }

    @Override
    public void initialize() {
        setupEventListeners();
        loadAlgorithmsInView();
        model.initialize();
    }

    private void setupEventListeners() {
        // Listener para crear nuevo grafo
        view.setCreateGraphListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewGraph();
            }
        });

        // Listener para cargar grafo de muestra
        view.setLoadSampleListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSampleGraph();
            }
        });

        // Listener para agregar conexión
        view.setAddConnectionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addConnection();
            }
        });

        // Listener para remover conexión
        view.setRemoveConnectionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeConnection();
            }
        });

        // Listener para ejecutar algoritmo
        view.setExecuteAlgorithmListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeAlgorithm();
            }
        });

        // Listener para limpiar resultados
        view.setClearListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResults();
            }
        });

        // Listener para selección de algoritmo (mostrar descripción)
        view.setAlgorithmSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedAlgorithm = view.getSelectedAlgorithm();
                if (selectedAlgorithm != null) {
                    String description = model.getAlgorithmDescription(selectedAlgorithm);
                    view.setAlgorithmDescription(description);
                }
            }
        });
    }

    private void loadAlgorithmsInView() {
        java.util.List<String> algorithmNames = model.getAvailableAlgorithmNames();
        view.setAvailableAlgorithms(algorithmNames);
    }

    private void createNewGraph() {
        try {
            int size = view.getGraphSize();
            if (size <= 0 || size > 26) {
                view.showError("El tamaño del grafo debe estar entre 1 y 26 vértices");
                return;
            }
            
            model.createGraph(size);
            view.updateGraphDisplay(model.getGraph());
            view.showMessage("Grafo creado exitosamente con " + size + " vértices");
            
        } catch (NumberFormatException ex) {
            view.showError("Por favor ingrese un número válido para el tamaño del grafo");
        } catch (Exception ex) {
            view.showError("Error al crear el grafo: " + ex.getMessage());
        }
    }

    private void loadSampleGraph() {
        try {
            model.loadSampleGraph();
            view.updateGraphDisplay(model.getGraph());
            view.showMessage("Grafo de muestra cargado exitosamente");
        } catch (Exception ex) {
            view.showError("Error al cargar grafo de muestra: " + ex.getMessage());
        }
    }

    private void addConnection() {
        try {
            if (model.getGraph() == null) {
                view.showError("Primero debe crear o cargar un grafo");
                return;
            }

            int from = view.getFromVertex();
            int to = view.getToVertex();
            int weight = view.getEdgeWeight();

            if (from < 0 || from >= model.getGraph().size() || 
                to < 0 || to >= model.getGraph().size()) {
                view.showError("Los índices de vértices deben estar entre 0 y " + (model.getGraph().size() - 1));
                return;
            }

            if (weight <= 0) {
                view.showError("El peso debe ser mayor a 0");
                return;
            }

            model.setConnection(from, to, weight);
            view.updateGraphDisplay(model.getGraph());
            view.showMessage(String.format("Conexión agregada: %s-%s (peso: %d)", 
                model.getGraph().label(from), model.getGraph().label(to), weight));

        } catch (Exception ex) {
            view.showError("Error al agregar conexión: " + ex.getMessage());
        }
    }

    private void removeConnection() {
        try {
            if (model.getGraph() == null) {
                view.showError("Primero debe crear o cargar un grafo");
                return;
            }

            int from = view.getFromVertex();
            int to = view.getToVertex();

            if (from < 0 || from >= model.getGraph().size() || 
                to < 0 || to >= model.getGraph().size()) {
                view.showError("Los índices de vértices deben estar entre 0 y " + (model.getGraph().size() - 1));
                return;
            }

            model.removeConnection(from, to);
            view.updateGraphDisplay(model.getGraph());
            view.showMessage(String.format("Conexión removida: %s-%s", 
                model.getGraph().label(from), model.getGraph().label(to)));

        } catch (Exception ex) {
            view.showError("Error al remover conexión: " + ex.getMessage());
        }
    }

    private void executeAlgorithm() {
        try {
            if (model.getGraph() == null) {
                view.showError("Primero debe crear o cargar un grafo");
                return;
            }

            String selectedAlgorithm = view.getSelectedAlgorithm();
            if (selectedAlgorithm == null) {
                view.showError("Seleccione un algoritmo");
                return;
            }

            int startVertex = view.getStartVertex();
            if (startVertex < 0 || startVertex >= model.getGraph().size()) {
                view.showError("El vértice de inicio debe estar entre 0 y " + (model.getGraph().size() - 1));
                return;
            }

            GraphResult result = model.executeAlgorithm(selectedAlgorithm, startVertex);
            view.displayResult(result);

        } catch (Exception ex) {
            view.showError("Error al ejecutar algoritmo: " + ex.getMessage());
        }
    }

    private void clearResults() {
        view.clearResults();
        view.showMessage("Resultados limpiados");
    }

    @Override
    public void handleEvent(String eventType, Object data) {
        switch (eventType) {
            case "CREATE_GRAPH":
                createNewGraph();
                break;
            case "LOAD_SAMPLE":
                loadSampleGraph();
                break;
            case "ADD_CONNECTION":
                addConnection();
                break;
            case "REMOVE_CONNECTION":
                removeConnection();
                break;
            case "EXECUTE_ALGORITHM":
                executeAlgorithm();
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
                case "CREATE_GRAPH":
                    if (parameters.length > 0) {
                        model.createGraph((Integer) parameters[0]);
                    }
                    break;
                case "LOAD_SAMPLE":
                    model.loadSampleGraph();
                    break;
                case "SET_CONNECTION":
                    if (parameters.length >= 3) {
                        model.setConnection((Integer) parameters[0], 
                                          (Integer) parameters[1], 
                                          (Integer) parameters[2]);
                    }
                    break;
                case "REMOVE_CONNECTION":
                    if (parameters.length >= 2) {
                        model.removeConnection((Integer) parameters[0], (Integer) parameters[1]);
                    }
                    break;
                default:
                    view.showError("Acción no reconocida: " + action);
            }
            updateView();
        } catch (Exception e) {
            view.showError("Error al actualizar modelo: " + e.getMessage());
        }
    }

    @Override
    public void updateView() {
        if (model.getGraph() != null) {
            view.updateGraphDisplay(model.getGraph());
        }
    }

    /**
     * Obtiene la vista asociada a este controlador.
     * @return panel de la vista
     */
    public JPanel getView() {
        return view;
    }

    public GraphAlgorithmsModel getModel() {
        return model;
    }
}