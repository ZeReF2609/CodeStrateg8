package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.model.ApplicationModel;
import com.algoritmoscurso.view.MainView;
import com.algoritmoscurso.view.ActividadesView;
import com.algoritmoscurso.view.semana2.BigOView;
import com.algoritmoscurso.view.semana2.SortingView;

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
    
    public ApplicationController() {
        model = new ApplicationModel();
        view = new MainView();
        
        // Inicializar controladores específicos
        sortingController = new SortingController(model.getSortingModel());
        bigOController = new BigOController(model.getBigOModel());
        
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
            // For semana2 we'll add known activities
            if ("semana2".equals(week)) {
                actividadesView.addActivity(week, "bigO", "Ejemplos Big O");
                actividadesView.addActivity(week, "sorting", "Algoritmos de Ordenación");
                // Add the single activity sections
                String[] sections = model.getWeekSectionTitles(week);
                for (String sec : sections) {
                    if (sec.toLowerCase().contains("big o") || sec.toLowerCase().contains("bigo") || sec.toLowerCase().contains("big")) {
                        actividadesView.addSection(week, "bigO", sec);
                    } else if (sec.toLowerCase().contains("orden") || sec.toLowerCase().contains("ordenaci")) {
                        actividadesView.addSection(week, "sorting", sec);
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
            // Expandir todos los nodos para que el usuario vea las actividades
            actividadesView.showWeeks();
            view.showActivities();
        }
    }
}
