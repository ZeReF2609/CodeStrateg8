package com.algoritmoscurso.model.bigo;

import com.algoritmoscurso.interfaces.IBigOExample;
import java.util.*;

/**
 * BigOExamples: 12 ejemplos de complejidad computacional.
 */
public class BigOExamples {

    // ==========================
    // EJEMPLO 1: Suma simple O(n)
    // ==========================
    public static class Ejemplo1 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            if (n < 0) {
                return "n debe ser >= 0";
            }
            int suma = 0;
            for (int i = 0; i < n; i++) {
                suma += i;
            }
            return String.format("Suma(0..%d) = %d", Math.max(0, n - 1), suma);
        }

        @Override
        public String getSourceCode() {
            return "****Bucle simple*****\n\n"
                    + "int sumaSimple(int n) {\n"
                    + "  int suma = 0;\n"
                    + "  for (int i = 0; i < n; i++)---------n\n"
                    + "   suma += i;------------------------O(1)\n"
                    + "  return suma;\n"
                    + "}\n\n"
                    + "Tendremos: n * O(1)  = 𝑂(𝑛)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 1";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n)";
        }
    }

    // ==========================
    // EJEMPLO 2: Bucle doble O(n^2)
    // ==========================
    public static class Ejemplo2 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input), contador = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    contador++;
                }
            }
            return "Total operaciones: " + contador;
        }

        @Override
        public String getSourceCode() {
            return "****Bucle Doble*****\n\n"
                    + "void bucleDoble(int n) {\n"
                    + " int contador = 0;"
                    + "  for (int i = 0; i < n; i++)-----------n\n"
                    + "    for (int j = 0; j < n; j++)---------n\n"
                    + "     contador ++;--------------------o(1)\n"
                    + " return contador}"
                    + "Tendremos: n * n * O(1)  = 𝑂(𝑛2)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n^2), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 2";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n^2)";
        }
    }

    // ==========================
    // EJEMPLO 3: Búsqueda binaria O(log n)
    // ==========================
    public static class Ejemplo3 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            int[] arr = {8, 7, 90, 0, 5};

            int izq = 0, der = arr.length - 1;
            while (izq <= der) {
                int medio = (izq + der) / 2;
                if (arr[medio] == n) {
                    return "Encontrado en índice: " + medio;
                } else if (arr[medio] < n) {
                    izq = medio + 1;
                } else {
                    der = medio - 1;
                }
            }
            return "No encontrado";
        }

        @Override

        public String getSourceCode() {
            return "****Busqueda binaria O(log n)*****\n\n"
                    + "int[] arr = {8, 7, 90, 0, 5};\n"
                    + "\n"
                    + "int busquedaBinaria(int[] arr, int n) {\n"
                    + "    int izq = 0, der = arr.length - 1;--------- O(1)\n"
                    + "    while (izq <= der) {------------------------O(log n)\n"
                    + "        int medio = (izq + der) / 2;------------O(1)\n"
                    + "        if (arr[medio] == n) -------------------O(1)\n"
                    + "            return medio;-----------------------O(1)\n"
                    + "        else if (arr[medio] < n)----------------O(1)\n"
                    + "            izq = medio + 1;--------------------O(1)\n"
                    + "        else                          \n"
                    + "            der = medio - 1;-------------------- O(1)\n"
                    + "    }\n"
                    + "    return -1; // No encontrado (O(1))\n"
                    + "}"
                    + "Tendremos: log n * O(1)  = 𝑂(log n)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(log n), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 3";
        }

        @Override
        public String getTimeComplexity() {
            return "O(log n)";
        }
    }

    // ==========================
    // EJEMPLO 4: Bucle división entre 2 O(log n)
    // ==========================
    public static class Ejemplo4 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input), c = 0;
            while (n > 0) {
                n /= 2;
                c++;
            }
            return "Iteraciones: " + c;
        }

        @Override

        public String getSourceCode() {
            return "*****Bucle que divide entre 2 ***********\n\n"
                    + "void bucleMitad(int n) {\n"
                    + "    while (n > 0) {---------------------------- O(log n)\n"
                    + "        n /= 2;-------------------------------- O(1)\n"
                    + "    }\n"
                    + "}"
                    + "Tendremos: log n * O(1)  = 𝑂(log n)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(log n), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 4";
        }

        @Override
        public String getTimeComplexity() {
            return "O(log n)";
        }
    }

    // ==========================
    // EJEMPLO 5: Búsqueda lineal O(n)
    // ==========================
    public static class Ejemplo5 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int[] arr = {1, 2, 3, 4, 5};
            int objetivo = aEntero(input);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == objetivo) {
                    return "Encontrado en índice: " + i;
                }
            }
            return "No encontrado";
        }

        @Override

        public String getSourceCode() {
            return "// ******** Búsqueda Lineal ********n\n"
                    + "int[] arr = {1, 2, 3, 4, 5};\n"
                    + "int busquedaLineal(int[] arr, int n) {\n"
                    + "    for (int i = 0; i < arr.length; i++) {----------- O(n)\n"
                    + "        if (arr[i] == n) return i;------------------- O(1)\n"
                    + "    }\n"
                    + "    return -1; //------------------------------------ O(1)\n"
                    + "}"
                    + "Tendremos:  n * O(1)  = 𝑂(n)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 5";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n)";
        }
    }

    // ==========================
    // EJEMPLO 6: Bucle triple O(n^3)
    // ==========================
    public static class Ejemplo6 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            int contador = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        contador++;
                    }
                }
            }
            return "Operaciones totales: " + contador;
        }

        @Override
        public String getSourceCode() {
            return "***** Bucle triple anidado *****\n\n"
                    + "void bucleTriple(int n) {\n"
                    + "    for (int i = 0; i < n; i++) {-------------- O(n)\n"
                    + "        for (int j = 0; j < n; j++) {---------- O(n)\n"
                    + "            for (int k = 0; k < n; k++) {------ O(n)\n"
                    + "                ;----------------------------- O(1)\n"
                    + "            }\n"
                    + "        }\n"
                    + "    }\n"
                    + "}\n"
                    + "Tendremos: n * n * n * O(1) = O(n^3)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n^3), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 6";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n^3)";
        }
    }

    // ==========================
    // EJEMPLO 7: Recursión simple O(n)
    // ==========================
    public static class Ejemplo7 implements IBigOExample {

        private int contador = 0;

        private void contar(int n) {
            if (n == 0) {
                return;
            }
            contador++;
            contar(n - 1);
        }

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            contador = 0;
            contar(n);
            return "Llamadas recursivas: " + contador;
        }

        @Override
        public String getSourceCode() {
            return "***** Recursión simple *****\n\n"
                    + "void contar(int n) {\n"
                    + "    if (n == 0) return;------------------------ O(1)\n"
                    + "    contar(n-1);------------------------------- O(n)\n"
                    + "}\n"
                    + "Tendremos: n llamadas * O(1) = O(n)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n), Espacio O(n) (stack)";
        }

        @Override
        public String getName() {
            return "Ejemplo 7";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n)";
        }
    }

    // ==========================
    // EJEMPLO 8: Suma aritmética O(1)
    // ==========================
    public static class Ejemplo8 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            long suma = (long) n * (n - 1) / 2;
            return "Suma de 0.." + (n - 1) + ": " + suma;
        }

        @Override
        public String getSourceCode() {
            return "***** Suma aritmética *****\n\n"
                    + "long sumaAritmetica(int n) {\n"
                    + "    return n * (n - 1) / 2;-------------------- O(1)\n"
                    + "}\n"
                    + "Tendremos: Operación constante = O(1)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(1), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 8";
        }

        @Override
        public String getTimeComplexity() {
            return "O(1)";
        }
    }

// ==========================
// EJEMPLO 9: Factorial Recursivo O(n)
// ==========================
    public static class Ejemplo9 implements IBigOExample {

        private long factorial(int n) {
            if (n <= 1) {
                return 1;
            }
            return n * factorial(n - 1);
        }

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            return "Factorial de " + n + ": " + factorial(n);
        }

        @Override
        public String getSourceCode() {
            return "***** Factorial recursivo *****\n\n"
                    + "long factorial(int n) {\n"
                    + "    if (n <= 1) return 1;---------------------- O(1)\n"
                    + "    return n * factorial(n-1);----------------- O(n)\n"
                    + "}---------------------------------------------- O(1)\n"
                    + "Tendremos: n llamadas * O(1) = O(n)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n), Espacio O(n) (stack)";
        }

        @Override
        public String getName() {
            return "Ejemplo 9";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n)";
        }
    }

// ==========================
// EJEMPLO 10: Generar todas las parejas (combinaciones) O(n^2)
// ==========================
    public static class Ejemplo10 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            List<String> parejas = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                
                for (int j = i + 1; j < n; j++) {
                    parejas.add("(" + i + "," + j + ")");
                }
            }
            return "Parejas generadas: " + parejas + "\n"+
                    "N° Parejas: " + parejas.size();
        }

        @Override
        public String getSourceCode() {
            return "***** Generar todas las parejas *****\n\n"
                    + "void generarParejas(int n) {\n"
                    + "    for (int i = 0; i < n; i++) {-------------- O(n)\n"
                    + "        for (int j = i+1; j < n; j++) {-------- O(n)\n"
                    + "            System.out.print(\"(\"+i+\",\"+j+\")\");- O(1)\n"
                    + "        }\n"
                    + "    }\n"
                    + "}---------------------------------------------- O(1)\n"
                    + "Tendremos: (n*(n-1)/2) ≈ O(n^2)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n^2), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 10";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n^2)";
        }
    }

// ==========================
// EJEMPLO 11: Bucle decreciente O(n)
// ==========================
    public static class Ejemplo11 implements IBigOExample {

        @Override
        public Object execute(Object input) {
            int n = aEntero(input);
            StringBuilder sb = new StringBuilder();
            for (int i = n; i > 0; i--) {
                sb.append(i).append(" ");
            }
            return sb.toString();
        }

        @Override
        public String getSourceCode() {
            return "***** Bucle decreciente *****\n\n"
                    + "void bucleDecreciente(int n) {\n"
                    + "    for (int i = n; i > 0; i--) {--------------- O(n)\n"
                    + "        System.out.print(i + \" \");------------ O(1)\n"
                    + "    }\n"
                    + "}\n"
                    + "Tendremos: n * O(1) = O(n)";
        }

        @Override
        public String getComplexityAnalysis() {
            return "Tiempo O(n), Espacio O(1)";
        }

        @Override
        public String getName() {
            return "Ejemplo 11";
        }

        @Override
        public String getTimeComplexity() {
            return "O(n)";
        }
    }

    // -------------------------
    // HELPER
    // -------------------------
    private static int aEntero(Object input) {
        try {
            if (input == null) {
                return 0;
            }
            if (input instanceof Integer) {
                return (Integer) input;
            }
            if (input instanceof int[]) {
                int[] arr = (int[]) input;
                return arr.length > 0 ? arr[0] : 0;
            }
            if (input instanceof String) {
                return Integer.parseInt((String) input);
            }
            if (input instanceof Number) {
                return ((Number) input).intValue();
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    // Lista de ejemplos
    public List<IBigOExample> getExamples() {
        List<IBigOExample> out = new ArrayList<>();
        out.add(new Ejemplo1());
        out.add(new Ejemplo2());
        out.add(new Ejemplo3());
        out.add(new Ejemplo4());
        out.add(new Ejemplo5());
        out.add(new Ejemplo6());
        out.add(new Ejemplo7());
        out.add(new Ejemplo8());
        out.add(new Ejemplo9());
        out.add(new Ejemplo10());
        out.add(new Ejemplo11());
        return out;
    }
}
