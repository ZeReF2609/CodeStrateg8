package com.algoritmoscurso.model;

import com.algoritmoscurso.interfaces.IModel;
import com.algoritmoscurso.model.sorting.SortingModel;
import com.algoritmoscurso.model.bigo.BigOModel;
import com.algoritmoscurso.model.greedy.GreedyModel;
import com.algoritmoscurso.model.graph.GraphAlgorithmsModel;
import com.algoritmoscurso.model.backtracking.BacktrackingModel;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Modelo principal de la aplicación
 * Gestiona el estado global y las diferentes semanas/módulos
 */
public class ApplicationModel implements IModel {
    private String currentWeek;
    private Map<String, String> weekTitles;
    // week -> (sectionTitle -> sectionDescription)
    private Map<String, Map<String, String>> weekSections;
    private SortingModel sortingModel;
    private BigOModel bigOModel;
    private GreedyModel greedyModel;
    private GraphAlgorithmsModel graphAlgorithmsModel;
    private BacktrackingModel backtrackingModel;
    
    public ApplicationModel() {
        weekTitles = new LinkedHashMap<>();
        weekSections = new LinkedHashMap<>();
    sortingModel = new SortingModel();
    bigOModel = new BigOModel();
    greedyModel = new GreedyModel();
    graphAlgorithmsModel = new GraphAlgorithmsModel();
    backtrackingModel = new BacktrackingModel();
        initialize();
    }
    
    @Override
    public void initialize() {
        currentWeek = "semana2";
        setupWeekData();
    }
    
    @Override
    public boolean validate() {
        return currentWeek != null && !currentWeek.isEmpty();
    }
    
    @Override
    public void clear() {
        currentWeek = "";
    sortingModel.clear();
    bigOModel.clear();
    greedyModel.clear();
    graphAlgorithmsModel.clear();
    backtrackingModel.clear();
    }
    
    /**
     * Configura los datos de las semanas
     */
    private void setupWeekData() {
        // Semana 2 - populate title and sections
        weekTitles.put("semana2", "Semana 2: Estructuras de Control y Algoritmos de Ordenación");
    Map<String, String> s2 = new LinkedHashMap<>();
        // Una sola actividad por tema; títulos legibles que se usarán como secciones en el árbol
        s2.put("Actividad Big O", "Presentar al menos 10 casos de ejemplos de estructuras de bucles y condicionales, mostrando el proceso de cálculo de la complejidad a través de la notación Big O.");
        s2.put("Actividad Ordenación", "Implementar los algoritmos de ordenación: burbuja, inserción, selección, quicksort y mergesort. Incluir un menú que permita seleccionar el método y ejecutar todos los algoritmos sobre el mismo conjunto de datos para comparar comportamiento.");
        weekSections.put("semana2", s2);
        
        // Semana 3 - Algoritmos Voraces
        weekTitles.put("semana3", "Semana 3: Algoritmos Voraces (Greedy)");
    Map<String, String> s3 = new LinkedHashMap<>();
        s3.put("Algoritmos Voraces", "Implementar algoritmos voraces para resolver problemas de optimización: cambio de moneda y el problema del agente viajero.");
        s3.put("Cambio de Moneda", "Implementar el algoritmo voraz para el problema del cambio de moneda, encontrando el número mínimo de monedas necesarias para dar el cambio de una cantidad específica.");
        s3.put("Agente Viajero", "Implementar una solución voraz para el problema del agente viajero (TSP), encontrando una ruta que visite todas las ciudades y regrese al punto de partida con la menor distancia posible.");
        weekSections.put("semana3", s3);

    // Semana 4 - Grafos, Recursión y Backtracking
    weekTitles.put("semana4", "Semana 4: Grafos, Recursión y Backtracking");
    Map<String, String> s4 = new LinkedHashMap<>();
    s4.put("Algoritmos de Grafos", "Analizar y visualizar los algoritmos de Dijkstra y Kruskal sobre un grafo ponderado, interpretando rutas más cortas y árboles de expansión mínima.");
    s4.put("Algoritmos Recursivos", "Implementar QuickSort y Búsqueda Binaria de manera recursiva, resaltando la división del problema y el seguimiento de pasos de recursión.");
    s4.put("Backtracking", "Resolver el rompecabezas de las Torres de Hanoi y el recorrido del Caballo utilizando backtracking, mostrando cada movimiento y el tablero resultante.");
    weekSections.put("semana4", s4);
        
    // Semana 5 - Programación Dinámica (Floyd-Warshall y Mochila 0/1)
    weekTitles.put("semana5", "Semana 5: Programación Dinámica");
    Map<String, String> s5 = new LinkedHashMap<>();
    s5.put("Floyd-Warshall", "Implementar el algoritmo de Floyd-Warshall para calcular las distancias mínimas entre todos los pares de vértices y visualizar la matriz de distancias.");
    s5.put("Mochila 0/1", "Resolver el problema de la mochila 0/1 mediante programación dinámica, mostrar la tabla DP y los elementos seleccionados para la solución óptima.");
    weekSections.put("semana5", s5);
        
    // Semana 6 - Algoritmos Probabilísticos
    weekTitles.put("semana6", "Semana 6: Algoritmos Probabilísticos");
    Map<String, String> s6 = new LinkedHashMap<>();
    s6.put("Monte Carlo (Estimación de Pi)", "Estimar Pi mediante métodos Monte Carlo y visualizar convergencia.");
    s6.put("Test de Primalidad (Miller-Rabin)", "Pruebas probabilísticas de primalidad usando Miller-Rabin y analizar probabilidades de error.");
    weekSections.put("semana6", s6);
    }

    /**
     * Devuelve los títulos de las secciones para una semana dada en orden indeterminado.
     */
    public String[] getWeekSectionTitles(String week) {
        Map<String, String> sections = weekSections.get(week);
        if (sections == null) return new String[0];
        return sections.keySet().toArray(new String[0]);
    }

    /**
     * Obtiene la descripción de una sección concreta dentro de una semana
     */
    public String getWeekSectionDescription(String week, String sectionTitle) {
        Map<String, String> sections = weekSections.get(week);
        if (sections == null) return "Descripción no disponible";
        return sections.getOrDefault(sectionTitle, "Descripción no disponible");
    }
    
    /**
     * Cambia la semana actual
     * @param week identificador de la semana
     */
    public void setCurrentWeek(String week) {
        if (weekTitles.containsKey(week)) {
            this.currentWeek = week;
        }
    }
    
    /**
     * Obtiene el título de una semana
     * @param week identificador de la semana
     * @return título de la semana
     */
    public String getWeekTitle(String week) {
        return weekTitles.getOrDefault(week, "Semana no encontrada");
    }
    
    /**
     * Obtiene la descripción de una semana
     * @param week identificador de la semana
     * @return descripción de la semana
     */
    public String getWeekDescription(String week) {
        Map<String, String> sections = weekSections.get(week);
        if (sections == null || sections.isEmpty()) return "Descripción no disponible";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> e : sections.entrySet()) {
            sb.append(e.getKey()).append(":\n");
            sb.append(e.getValue()).append("\n\n");
        }
        return sb.toString().trim();
    }
    
    /**
     * Obtiene todas las semanas disponibles
     * @return array con los identificadores de las semanas
     */
    public String[] getAvailableWeeks() {
        return weekTitles.keySet().toArray(new String[0]);
    }
    
    // Getters
    public String getCurrentWeek() {
        return currentWeek;
    }
    
    public String getCurrentWeekTitle() {
        return getWeekTitle(currentWeek);
    }
    
    public String getCurrentWeekDescription() {
        return getWeekDescription(currentWeek);
    }
    
    public SortingModel getSortingModel() {
        return sortingModel;
    }
    
    public BigOModel getBigOModel() {
        return bigOModel;
    }
    
    public GreedyModel getGreedyModel() {
        return greedyModel;
    }

    public GraphAlgorithmsModel getGraphAlgorithmsModel() {
        return graphAlgorithmsModel;
    }

    public BacktrackingModel getBacktrackingModel() {
        return backtrackingModel;
    }
    
    @Override
    public String toString() {
        return String.format("Aplicación: Repositorio de Algoritmos%nSemana actual: %s%nTítulo: %s",
                currentWeek,
                getCurrentWeekTitle());
    }
}
