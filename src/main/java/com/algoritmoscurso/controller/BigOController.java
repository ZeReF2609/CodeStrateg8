package com.algoritmoscurso.controller;

import com.algoritmoscurso.interfaces.IController;
import com.algoritmoscurso.interfaces.IBigOExample;
import com.algoritmoscurso.model.bigo.BigOModel;
import com.algoritmoscurso.view.semana2.BigOView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para los ejemplos de análisis Big O
 */
public class BigOController implements IController {
    private BigOModel model;
    private BigOView view;
    
    public BigOController(BigOModel model) {
        this.model = model;
        this.view = new BigOView();
    }
    
    @Override
    public void initialize() {
        setupEventHandlers();
        updateExamplesList();
    // initialize view content but DO NOT override application-level descriptions
    updateView();
    }
    
    /**
     * Configura los ejemplos disponibles
     */
    
    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        view.setListSelectionListener(new ExampleSelectionListener());
        view.setExecuteButtonListener(new ExecuteButtonListener());
    }
    
    /**
     * Actualiza la lista de ejemplos en la vista
     */
    private void updateExamplesList() {
        String[] exampleNames = model.getExampleNames();
        view.setExamples(exampleNames);
        if (exampleNames.length > 0) {
            model.selectExample(0);
            updateSelectedExample();
        }
    }
    
    /**
     * Actualiza la vista cuando se selecciona un ejemplo
     */
    private void updateSelectedExample() {
        IBigOExample selectedExample = model.getSelectedExample();
        
        if (selectedExample != null) {
            view.setCodeText(selectedExample.getSourceCode());
            view.setAnalysisText(selectedExample.getComplexityAnalysis());
        } else {
            view.setCodeText("");
            view.setAnalysisText("");
        }
        
        view.setResultText("");
    }
    
    @Override
    public void handleEvent(String eventType, Object data) {
        switch (eventType) {
            case "EXAMPLE_SELECTED":
                int index = (Integer) data;
                model.selectExample(index);
                updateSelectedExample();
                break;
            case "EXECUTE_EXAMPLE":
                executeSelectedExample();
                break;
            default:
                System.out.println("Evento no manejado en BigOController: " + eventType);
                break;
        }
    }
    
    /**
     * Ejecuta el ejemplo seleccionado
     */
    private void executeSelectedExample() {
        try {
            String inputText = view.getInputValue();
            String inputText2 = null;
            if (view instanceof com.algoritmoscurso.view.semana2.BigOView) {
                inputText2 = ((com.algoritmoscurso.view.semana2.BigOView) view).getSecondInputValue();
            }

            if (inputText == null || inputText.isEmpty()) {
                view.showError("Por favor ingrese un valor para n.");
                return;
            }

            int n = Integer.parseInt(inputText);
            int param2 = 0;
            if (inputText2 != null && !inputText2.isEmpty()) {
                param2 = Integer.parseInt(inputText2);
            }

            if (n < 1 || n > 1000) {
                view.showError("El valor de n debe estar entre 1 y 1000.");
                return;
            }
            
            IBigOExample selectedExample = model.getSelectedExample();
            if (selectedExample == null) {
                view.showError("Por favor seleccione un ejemplo.");
                return;
            }
            
            // Ejecutar el ejemplo
            Object result = model.executeSelectedExample(new int[]{n, param2});
            
            // Mostrar resultado
            String nl = System.lineSeparator();
            StringBuilder resultText = new StringBuilder();
            resultText.append("RESULTADO DE EJECUCIÓN:").append(nl);
            resultText.append("Entrada (n): ").append(n).append(nl);
            //resultText.append("Parametro 2: ").append(param2).append(nl);
            //resultText.append("Tiempo de ejecución: ").append(model.getLastExecutionTime()).append(" ns").append(nl);
            resultText.append("Tiempo en ejecución (ms): ").append(model.getLastExecutionTime() / 1_000_000.0).append(" ms").append(nl);
            resultText.append("Complejidad: ").append(selectedExample.getTimeComplexity()).append(nl).append(nl);
            
            if (result != null) {
                String resultStr = result.toString();
                if (resultStr.length() > 1000) {
                    resultText.append("Resultado (primeros 1000 caracteres):").append(nl);
                    resultText.append(resultStr.substring(0, 1000)).append("...");
                } else {
                    resultText.append("Resultado:").append(nl);
                    resultText.append(resultStr);
                }
            }
            
            view.setResultText(resultText.toString());
            
        } catch (NumberFormatException e) {
            view.showError("Por favor ingrese un número válido.");
        } catch (Exception e) {
            view.showError("Error durante la ejecución: " + e.getMessage());
        }
    }
    
    @Override
    public void updateModel(String action, Object... parameters) {
        // Implementar según necesidades específicas
    }
    
    @Override
    public void updateView() {
    view.updateView(model);
    }
    
    /**
     * Obtiene la vista
     */
    public BigOView getView() {
        return view;
    }
    
    // Listeners internos
    private class ExampleSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = view.getSelectedExampleIndex();
                if (selectedIndex >= 0) {
                    handleEvent("EXAMPLE_SELECTED", selectedIndex);
                }
            }
        }
    }
    
    private class ExecuteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleEvent("EXECUTE_EXAMPLE", null);
        }
    }
}
