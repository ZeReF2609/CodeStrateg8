package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.model.ApplicationModel;
import com.algoritmoscurso.view.MainView;
import com.algoritmoscurso.view.ActividadesView;
import com.algoritmoscurso.view.semana2.BigOView;
import com.algoritmoscurso.view.semana2.SortingView;
import com.algoritmoscurso.view.semana3.CoinChangeView;
import com.algoritmoscurso.view.semana3.TravelingSalesmanView;

import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionListener;

/**
 * Controlador principal de la aplicación siguiendo el patrón MVC
 * Gestiona la comunicación entre el modelo y la vista principal
 */
public class ApplicationController implements IController {
    private ApplicationModel model;
    private MainView view;
    private ActividadesView actividadesView;
    private SortingController sortingController;
    private BigOController bigOController;
    private GreedyController greedyController;
    private GraphAlgorithmsController graphController;
    private BacktrackingController backtrackingController;
    private RecursionController recursionController;
    private DynamicProgrammingController dpController;
    private ProbabilisticController probabilisticController;
    
    public ApplicationController() {
        model = new ApplicationModel();
        view = new MainView();
        
        // Inicializar controladores específicos
        sortingController = new SortingController(model.getSortingModel());
        bigOController = new BigOController(model.getBigOModel());
        greedyController = new GreedyController(model.getGreedyModel());
        graphController = new GraphAlgorithmsController();
        backtrackingController = new BacktrackingController();
        recursionController = new RecursionController();
        dpController = new DynamicProgrammingController();
    probabilisticController = new ProbabilisticController();
     
        initialize();
    }
    
    @Override
    public void initialize() {
    view.setController(this);
    // Mostrar carátula por defecto en vez de cargar una semana automáticamente
    view.showCover();
        view.setVisible(true);
    }
    
    /**
     * Configura los botones de las semanas en el sidebar
     */
    /**
     * Popula la vista de actividades con las semanas del modelo y configura listeners.
     */
    private void populateActivitiesView() {
        if (actividadesView == null) return;
        String[] weeks = model.getAvailableWeeks();
        for (String week : weeks) {
            String title = getShortWeekTitle(week);
            actividadesView.addWeekButton(week, title, null);

            String[] sections = model.getWeekSectionTitles(week);

            if ("semana2".equals(week)) {
                // Definir actividades
                actividadesView.addActivity(week, "bigO", "Ejemplos Big O");
                actividadesView.addActivity(week, "sorting", "Algoritmos de Ordenación");

                // Palabras clave por actividad
                Map<String, String[]> keywords = new HashMap<>();
                keywords.put("bigO", new String[] { "big o", "bigo", "big" });
                keywords.put("sorting", new String[] { "orden", "ordenaci" });

                // Asignar secciones según palabras clave
                for (String sec : sections) {
                    String lower = sec.toLowerCase();
                    for (Map.Entry<String, String[]> entry : keywords.entrySet()) {
                        for (String kw : entry.getValue()) {
                            if (lower.contains(kw)) {
                                actividadesView.addSection(week, entry.getKey(), sec);
                            }
                        }
                    }
                }
            }

            if ("semana3".equals(week)) {
                actividadesView.addActivity(week, "coinchange", "Cambio de Moneda");
                actividadesView.addActivity(week, "tsp", "Agente Viajero (TSP)");

                // Palabras clave por actividad
                Map<String, String[]> keywords = new HashMap<>();
                keywords.put("greedy", new String[] { "voraz", "greedy" });
                keywords.put("coinchange", new String[] { "moneda", "coin" });
                keywords.put("tsp", new String[] { "viajero", "tsp" });

                // Asignar secciones según palabras clave
                for (String sec : sections) {
                    String lower = sec.toLowerCase();
                    for (Map.Entry<String, String[]> entry : keywords.entrySet()) {
                        for (String kw : entry.getValue()) {
                            if (lower.contains(kw)) {
                                actividadesView.addSection(week, entry.getKey(), sec);
                            }
                        }
                    }
                }
            }

            if ("semana4".equals(week)) {
                actividadesView.addActivity(week, "graph", "Algoritmos de Grafos");
                actividadesView.addActivity(week, "recursion", "Algoritmos Recursivos");
                actividadesView.addActivity(week, "backtracking", "Algoritmos de Backtracking");

                Map<String, String[]> keywords = new HashMap<>();
                keywords.put("graph", new String[] { "grafo", "dijkstra", "kruskal" });
                keywords.put("recursion", new String[] { "recurs", "quicksort", "búsqueda" });
                keywords.put("backtracking", new String[] { "backtracking", "hanoi", "caballo" });

                for (String sec : sections) {
                    String lower = sec.toLowerCase();
                    for (Map.Entry<String, String[]> entry : keywords.entrySet()) {
                        for (String kw : entry.getValue()) {
                            if (lower.contains(kw)) {
                                actividadesView.addSection(week, entry.getKey(), sec);
                            }
                        }
                    }
                }
            }

            if ("semana5".equals(week)) {
                actividadesView.addActivity(week, "floyd", "Floyd-Warshall");
                actividadesView.addActivity(week, "mochila", "Mochila 0/1");

                Map<String, String[]> keywords5 = new HashMap<>();
                keywords5.put("floyd", new String[] { "floyd", "matriz", "all pairs" });
                keywords5.put("mochila", new String[] { "mochila", "knapsack", "dp" });

                for (String sec : sections) {
                    String lower = sec.toLowerCase();
                    for (Map.Entry<String, String[]> entry : keywords5.entrySet()) {
                        for (String kw : entry.getValue()) {
                            if (lower.contains(kw)) {
                                actividadesView.addSection(week, entry.getKey(), sec);
                            }
                        }
                    }
                }
            }
            if ("semana6".equals(week)) {
                actividadesView.addActivity(week, "montecarlo", "Monte Carlo (Estimación de Pi)");
                actividadesView.addActivity(week, "miller", "Test de Primalidad (Miller-Rabin)");

                Map<String, String[]> keywords6 = new HashMap<>();
                keywords6.put("montecarlo", new String[] { "monte", "pi", "monte carlo" });
                keywords6.put("miller", new String[] { "miller", "rabin", "primal" });

                for (String sec : sections) {
                    String lower = sec.toLowerCase();
                    for (Map.Entry<String, String[]> entry : keywords6.entrySet()) {
                        for (String kw : entry.getValue()) {
                            if (lower.contains(kw)) {
                                actividadesView.addSection(week, entry.getKey(), sec);
                            }
                        }
                    }
                }
            }
        }


        actividadesView.setTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                String selectedId = actividadesView.getSelectedTreeItemId();
                if (selectedId == null) return;

                // section nodes: semana/activity/section
                if (selectedId.contains("/")) {
                    String[] parts = selectedId.split("/");
                    if (parts.length == 3) {
                        // week/activity/section selected -> show that section description
                        String week = parts[0];
                        String activityId = parts[1];
                        String sectionTitle = parts[2];
                        String desc = model.getWeekSectionDescription(week, sectionTitle);
                        actividadesView.updateDescription(desc);
                        // clear other tabs so only this activity is shown
                        actividadesView.clearTabs();
                        // also set description inside the activity view and open its tab
                        if ("bigO".equals(activityId)) {
                            BigOView bigOView = bigOController.getView();
                            bigOView.setDescription(desc);
                            actividadesView.addTab("Ejemplos Big O", bigOView);
                        } else if ("sorting".equals(activityId)) {
                            SortingView sortingView = sortingController.getView();
                            sortingView.setDescription(desc);
                            actividadesView.addTab("Algoritmos de Ordenación", sortingView);
                        } else if ("coinchange".equals(activityId)) {
                            CoinChangeView coinView = greedyController.getCoinChangeView();
                            coinView.setDescription(desc);
                            actividadesView.addTab("Cambio de Moneda", coinView);
                        } else if ("tsp".equals(activityId)) {
                            TravelingSalesmanView tspView = greedyController.getTravelingSalesmanView();
                            tspView.setDescription(desc);
                            actividadesView.addTab("Agente Viajero", tspView);
                        } else if ("graph".equals(activityId)) {
                            actividadesView.addTab("Algoritmos de Grafos", graphController.getView());
                        } else if ("backtracking".equals(activityId)) {
                            actividadesView.addTab("Backtracking", backtrackingController.getView());
                        } else if ("floyd".equals(activityId)) {
                            actividadesView.addTab("Floyd-Warshall", dpController.getFloydView());
                        } else if ("mochila".equals(activityId)) {
                            actividadesView.addTab("Mochila 0/1", dpController.getKnapsackView());
                        } else if ("montecarlo".equals(activityId) || "miller".equals(activityId)) {
                            actividadesView.addTab("Algoritmos Probabilísticos", probabilisticController.getView());
                        }
                        return;
                    } else if (parts.length == 2) {
                        // activity node: semana/activity -> show primary section for that activity
                        String week = parts[0];
                        String activityId = parts[1];
                        String primary = getPrimarySectionForActivity(week, activityId);
                        if (primary != null) {
                            String desc = model.getWeekSectionDescription(week, primary);
                            actividadesView.updateDescription(desc);
                            // clear other tabs so only this activity is shown
                            actividadesView.clearTabs();
                            // also open the corresponding tab for the activity and set its description
                            if ("bigO".equals(activityId)) {
                                BigOView bigOView = bigOController.getView();
                                bigOView.setDescription(desc);
                                actividadesView.addTab("Ejemplos Big O", bigOView);
                            } else if ("sorting".equals(activityId)) {
                                SortingView sortingView = sortingController.getView();
                                sortingView.setDescription(desc);
                                actividadesView.addTab("Algoritmos de Ordenación", sortingView);
                            } else if ("coinchange".equals(activityId)) {
                                CoinChangeView coinView = greedyController.getCoinChangeView();
                                actividadesView.addTab("Cambio de Moneda", coinView);
                            } else if ("tsp".equals(activityId)) {
                                TravelingSalesmanView tspView = greedyController.getTravelingSalesmanView();
                                actividadesView.addTab("Agente Viajero", tspView);
                            } else if ("graph".equals(activityId)) {
                                actividadesView.addTab("Algoritmos de Grafos", graphController.getView());
                            } else if ("recursion".equals(activityId)) {
                                actividadesView.addTab("Algoritmos Recursivos", recursionController.getView());
                            } else if ("backtracking".equals(activityId)) {
                                actividadesView.addTab("Backtracking", backtrackingController.getView());
                            } else if ("floyd".equals(activityId)) {
                                actividadesView.addTab("Floyd-Warshall", dpController.getFloydView());
                            } else if ("mochila".equals(activityId)) {
                                actividadesView.addTab("Mochila 0/1", dpController.getKnapsackView());
                            } else if ("montecarlo".equals(activityId) || "miller".equals(activityId)) {
                                actividadesView.addTab("Algoritmos Probabilísticos", probabilisticController.getView());
                            }
                        }
                        return;
                    }
                }

                // If we reach here, it's a week node (no slashes): do NOT create views.
                // Only update title and clear description/tabs so the tree acts like a folder.
                String weekId = selectedId;
                actividadesView.updateTitle(model.getWeekTitle(weekId));
                actividadesView.updateDescription("");
                actividadesView.clearTabs();
            }
        });
    }

    // Returns the primary section title for an activity (used when selecting the activity node)
    private String getPrimarySectionForActivity(String week, String activityId) {
        if ("semana2".equals(week)) {
            if ("bigO".equals(activityId)) return "Actividad Big O";
            if ("sorting".equals(activityId)) return "Actividad Ordenación";
        }
        if ("semana3".equals(week)) {
            if ("greedy".equals(activityId)) return "Algoritmos Voraces";
            if ("coinchange".equals(activityId)) return "Cambio de Moneda";
            if ("tsp".equals(activityId)) return "Agente Viajero";
        }
        if ("semana4".equals(week)) {
            if ("graph".equals(activityId)) return "Algoritmos de Grafos";
            if ("recursion".equals(activityId)) return "Algoritmos Recursivos";
            if ("backtracking".equals(activityId)) return "Backtracking";
        }
        if ("semana5".equals(week)) {
            if ("floyd".equals(activityId)) return "Floyd-Warshall";
            if ("mochila".equals(activityId)) return "Mochila 0/1";
        }
        if ("semana6".equals(week)) {
            if ("montecarlo".equals(activityId)) return "Monte Carlo (Estimación de Pi)";
            if ("miller".equals(activityId)) return "Test de Primalidad (Miller-Rabin)";
        }
        return null;
    }
    
    /**
     * Obtiene un título corto para los botones del sidebar
     */
    private String getShortWeekTitle(String week) {
        switch (week) {
            case "semana2":
                return "Semana 2";
            case "semana3":
                return "Semana 3";
            case "semana4":
                return "Semana 4";
            case "semana5":
                return "Semana 5";
            case "semana6":
                return "Semana 6";
            default:
                return week.toUpperCase();
        }
    }
    
    /**
     * Actualiza la vista para mostrar el contenido de una semana específica
     */
    private void updateViewForWeek(String week) {
        model.setCurrentWeek(week);
        // Route updates to the actividades view (if opened)
        if (actividadesView != null) {
            // Only update the title. Do NOT populate description or tabs when selecting a week
            // — the user wants weeks to expand to show files and not show combined descriptions.
            actividadesView.updateTitle(model.getCurrentWeekTitle());
            actividadesView.updateDescription("");
            actividadesView.clearTabs();
        }
        updateView();
    }
    
    /**
     * Configura el contenido específico de la Semana 2
     */
    private void setupSemana2Content() {
        // Pestaña de ejemplos Big O
        BigOView bigOView = bigOController.getView();
    if (actividadesView != null) actividadesView.addTab("Ejemplos Big O", bigOView);
        
        // Pestaña de algoritmos de ordenación
        SortingView sortingView = sortingController.getView();
    if (actividadesView != null) actividadesView.addTab("Algoritmos de Ordenación", sortingView);
    // Forzar selección por defecto en la vista
    sortingView.selectFirstAlgorithm();
        
    // Inicializar controladores
    bigOController.initialize();
    sortingController.initialize();
    // Asegurar que la lista de algoritmos en la vista esté actualizada
    sortingController.refreshAlgorithmsList();
    }
    
    /**
     * Configura el contenido específico de la Semana 3
     */
    private void setupSemana3Content() {
        // Inicializar controlador de algoritmos voraces
        greedyController.initialize();
        
        // Las vistas específicas se acceden a través del controlador
        // No necesitamos agregarlas por defecto, se agregan cuando el usuario selecciona la actividad
    }
    
    /**
     * Configura el contenido específico de la Semana 4
     */
    private void setupSemana4Content() {
        // Inicializar controladores de semana 4
        if (graphController != null) {
            graphController.initialize();
        }
        if (backtrackingController != null) {
            backtrackingController.initialize();
        }
        
        // Las vistas específicas se acceden a través de los controladores
        // No necesitamos agregarlas por defecto, se agregan cuando el usuario selecciona la actividad
    }
    
    @Override
    public void handleEvent(String eventType, Object data) {
        switch (eventType) {
            case "WEEK_CHANGED":
                String actionCommand = (String) data;
                if (actionCommand.contains("/")) {
                    // Es una actividad específica de una semana
                    String[] parts = actionCommand.split("/");
                    String weekId = parts[0];
                    String activityId = parts[1];
                    updateViewForActivity(weekId, activityId);
                } else {
                    // Es una semana completa
                    updateViewForWeek(actionCommand);
                }
                break;
            default:
                System.out.println("Evento no manejado: " + eventType);
                break;
        }
    }
    
    /**
     * Actualiza la vista para mostrar una actividad específica
     */
    private void updateViewForActivity(String weekId, String activityId) {
        model.setCurrentWeek(weekId);
        if (actividadesView != null) {
            actividadesView.updateTitle(model.getCurrentWeekTitle());
            actividadesView.clearTabs();

            if ("semana2".equals(weekId)) {
                // get the specific section for the activity (Parte 1 / Parte 2)
                String primary = getPrimarySectionForActivity(weekId, activityId);
                String desc = primary != null ? model.getWeekSectionDescription(weekId, primary) : "";
                actividadesView.updateDescription(desc);

                if ("bigO".equals(activityId)) {
                    BigOView bigOView = bigOController.getView();
                    bigOView.setDescription(desc);
                    actividadesView.addTab("Ejemplos Big O", bigOView);
                } else if ("sorting".equals(activityId)) {
                    SortingView sortingView = sortingController.getView();
                    sortingView.setDescription(desc);
                    actividadesView.addTab("Algoritmos de Ordenación", sortingView);
                }
            }
            
            if ("semana3".equals(weekId)) {
                // get the specific section for the activity 
                String primary = getPrimarySectionForActivity(weekId, activityId);
                String desc = primary != null ? model.getWeekSectionDescription(weekId, primary) : "";
                actividadesView.updateDescription(desc);
                if ("coinchange".equals(activityId)) {
                    CoinChangeView coinView = greedyController.getCoinChangeView();
                    coinView.setDescription(desc);
                    actividadesView.addTab("Cambio de Moneda", coinView);
                } else if ("tsp".equals(activityId)) {
                    TravelingSalesmanView tspView = greedyController.getTravelingSalesmanView();
                    tspView.setDescription(desc);
                    actividadesView.addTab("Agente Viajero", tspView);
                }
            }

            if ("semana4".equals(weekId)) {
                // get the specific section for the activity 
                String primary = getPrimarySectionForActivity(weekId, activityId);
                String desc = primary != null ? model.getWeekSectionDescription(weekId, primary) : "";
                actividadesView.updateDescription(desc);
                
                if ("graph".equals(activityId)) {
                    actividadesView.addTab("Algoritmos de Grafos", graphController.getView());
                } else if ("recursion".equals(activityId)) {
                    actividadesView.addTab("Algoritmos Recursivos", recursionController.getView());
                } else if ("backtracking".equals(activityId)) {
                    actividadesView.addTab("Backtracking", backtrackingController.getView());
                }
            }
            
        }
        updateView();
    }
    
    @Override
    public void updateModel(String action, Object... parameters) {
        switch (action) {
            case "SET_CURRENT_WEEK":
                model.setCurrentWeek((String) parameters[0]);
                break;
            default:
                System.out.println("Acción no manejada: " + action);
                break;
        }
    }
    
    @Override
    public void updateView() {
        view.updateView(model);
    }
    
    /**
     * Obtiene la vista principal
     */
    public MainView getView() {
        return view;
    }
    
    /**
     * Obtiene el modelo de la aplicación
     */
    public ApplicationModel getModel() {
        return model;
    }

    /**
     * Abre una ventana separada con la vista de actividades (sidebar + pestañas).
     */
    public void openActividadesWindow() {
        // Show actividades embedded in main view
        if (view != null) {
            // Ensure actividadesView is the same instance managed by the main view
            actividadesView = view.getActividadesView();
            populateActivitiesView();
            // Ensure semana2 tabs and controllers are initialized so each actividad has its own tab
            setupSemana2Content();
            // Ensure semana3 tabs and controllers are initialized
            setupSemana3Content();
            // Ensure semana4 tabs and controllers are initialized
            setupSemana4Content();
            // Initialize semana5/6 controllers if present
            if (dpController != null) dpController.initialize();
            if (probabilisticController != null) probabilisticController.initialize();
            // Expandir todos los nodos para que el usuario vea las actividades
            actividadesView.showWeeks();
            view.showActivities();
        }
    }
}
