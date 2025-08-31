package com.algoritmoscurso.model;

import com.algoritmoscurso.interfaces.IModel;
import com.algoritmoscurso.model.sorting.SortingModel;
import com.algoritmoscurso.model.bigo.BigOModel;
import java.util.HashMap;
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
    
    public ApplicationModel() {
        weekTitles = new HashMap<>();
    weekSections = new HashMap<>();
        sortingModel = new SortingModel();
        bigOModel = new BigOModel();
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
    }
    
    /**
     * Configura los datos de las semanas
     */
    private void setupWeekData() {
        // Semana 2 - populate title and sections
        weekTitles.put("semana2", "Semana 2: Estructuras de Control y Algoritmos de Ordenación");
    Map<String, String> s2 = new HashMap<>();
    // Una sola actividad por tema; títulos legibles que se usarán como secciones en el árbol
    s2.put("Actividad Big O", "Presentar al menos 10 casos de ejemplos de estructuras de bucles y condicionales, mostrando el proceso de cálculo de la complejidad a través de la notación Big O.");
    s2.put("Actividad Ordenación", "Implementar los algoritmos de ordenación: burbuja, inserción, selección, quicksort y mergesort. Incluir un menú que permita seleccionar el método y ejecutar todos los algoritmos sobre el mismo conjunto de datos para comparar comportamiento.");
        weekSections.put("semana2", s2);
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
    
    @Override
    public String toString() {
        return String.format("Aplicación: Repositorio de Algoritmos%nSemana actual: %s%nTítulo: %s",
                currentWeek,
                getCurrentWeekTitle());
    }
}
