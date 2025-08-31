package com.algoritmoscurso.model.bigo;

import com.algoritmoscurso.interfaces.IBigOExample;
import java.util.*;

/**
 * Minimal BigOExamples: only one simple example to keep the project small and clear.
 */
public class BigOExamples {

    // Simple example: suma de 0..n-1 (O(n))
    public static class SimpleSumExample implements IBigOExample {
        @Override
        public Object execute(Object input) {
            int n = toInt(input, 10);
            if (n < 0) return "n debe ser >= 0";
            long s = 0;
            for (int i = 0; i < n; i++) s += i;
            return String.format("sum(0..%d) = %d", Math.max(0, n - 1), s);
        }

        @Override
        public String getSourceCode() {
            return "long sumN(int n) {\n" +
                   "  long s = 0;\n" +
                   "  for (int i = 0; i < n; i++) s += i;\n" +
                   "  return s;\n" +
                   "}";
        }

        @Override
        public String getComplexityAnalysis() { return "Tiempo O(n), Espacio O(1)"; }

        @Override
        public String getName() { return "sum"; }

        @Override
        public String getTimeComplexity() { return "O(n)"; }
    }

    // helper para convertir entrada a int
    private static int toInt(Object input, int fallback) {
        try {
            if (input == null) return fallback;
            if (input instanceof Integer) return (Integer) input;
            if (input instanceof int[]) {
                int[] arr = (int[]) input;
                return arr.length > 0 ? arr[0] : fallback;
            }
            if (input instanceof String) return Integer.parseInt((String) input);
            if (input instanceof Number) return ((Number) input).intValue();
            return fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    // Devuelve la lista con un solo ejemplo
    public List<IBigOExample> getExamples() {
        List<IBigOExample> out = new ArrayList<>();
        out.add(new SimpleSumExample());
        return out;
    }
}
