
# CodeStrateg8 — Proyecto del Grupo 8

Una aplicación educativa en Java para la asignatura "Análisis de Algoritmos". El proyecto organiza prácticas y ejemplos de algoritmos usando el patrón arquitectónico Modelo-Vista-Controlador (MVC) y contiene implementaciones y visualizaciones de algoritmos de ordenamiento, grafos, programación dinámica, backtracking, y más.

## Tabla de contenido

- Descripción
- Características
- Requisitos
- Estructura del repositorio
- Ejecución rápida (Windows / PowerShell)
- Ejecutar desde IDE
- Compilar y ejecutar desde línea de comandos (PowerShell)
- Notas y solución de problemas
- Cómo contribuir
- Miembros / Contacto

## Descripción

CodeStrateg8 agrupa prácticas y ejemplos de algoritmos para apoyar el aprendizaje. Las implementaciones están organizadas por tema (sorting, grafos, programación dinámica, greedy, backtracking, etc.) y la aplicación incluye vistas para interactuar con ejemplos y resultados.

## Características

- Implementaciones de algoritmos clásicos (ordenamiento, Dijkstra, Kruskal, Floyd, Knapsack, Tower of Hanoi, Knight's Tour, etc.).
- Estructura MVC: separación clara entre modelos, controladores y vistas.
- Pequeñas utilidades y adaptadores (por ejemplo, adaptadores para problemas de mochila y Floyd).
- Archivos de assets y documentación en `src/main/java/com/algoritmoscurso/assets/docs`.

## Requisitos

- Java JDK 11 o superior (recomendado). Si usas otra versión de Java, es probable que funcione con JDK 8+, pero JDK 11 es la suposición por defecto.
- IDE Java (IntelliJ IDEA, Eclipse, NetBeans) o la herramienta `javac` + `java` en la terminal.

## Estructura del repositorio

Resumen de los directorios más importantes:

- `run.bat` — script windows para ejecutar la aplicación rápidamente.
- `src/main/java/com/algoritmoscurso` — código fuente principal.
  - `controller/` — controladores que coordinan la vista y el modelo.
  - `model/` — modelos y lógica de los algoritmos; submódulos por tema (bigo, sorting, dp, graph, backtracking, etc.).
  - `view/` — vistas (UI) organizadas por semanas/temas.
  - `interfaces/` — interfaces que definen contratos entre componentes.
  - `assets/` — imágenes y documentación (PDFs) usados por la aplicación.

Ejemplo breve (estructura parcial y más completa):

```
src/main/java/com/algoritmoscurso
├── Main.java
├── assets
│   ├── docs
│   │   ├── SEMANA2.pdf
│   │   ├── SEMANA3.pdf
│   │   ├── SEMANA4.pdf
│   │   ├── SEMANA5.pdf
│   │   └── SEMANA6.pdf
│   └── img
│       └── upn_icon.png
├── controller
│   ├── ApplicationController.java
│   ├── BacktrackingController.java
│   ├── BigOController.java
│   ├── DynamicProgrammingController.java
│   ├── GraphAlgorithmsController.java
│   ├── GreedyController.java
│   ├── ProbabilisticController.java
│   ├── RecursionController.java
│   └── SortingController.java
├── interfaces
│   ├── IBacktrackingAlgorithm.java
│   ├── IBigOExample.java
│   ├── ICoinChange.java
│   ├── IController.java
│   ├── IModel.java
│   ├── IFloyd.java
│   ├── IGraphAlgorithm.java
│   ├── IGreedyAlgorithm.java
│   ├── IMochila.java
│   ├── IModel.java
│   ├── IProbabilistic.java
│   ├── ISortingAlgorithm.java
│   ├── ITravelingSalesman.java
│   └── IView.java
├── model
│   ├── ApplicationModel.java
│   ├── backtracking
│   │   ├── BacktrackingModel.java
│   │   ├── BacktrackingResult.java
│   │   ├── KnightTourAlgorithm.java
│   │   ├── KnightTourSolver.java
│   │   └── TowerOfHanoiAlgorithm.java
│   ├── bigo
│   │   ├── BigOExamples.java
│   │   └── BigOModel.java
│   ├── dp
│   │   ├── DPResult.java
│   │   ├── DynamicProgrammingModel.java
│   │   ├── FloydSolver.java
│   │   └── KnapsackSolver.java
│   ├── floyd
│   │   └── FloydAdapter.java
│   ├── graph
│   │   ├── DijkstraAlgorithm.java
│   │   ├── GraphAlgorithmsModel.java
│   │   ├── GraphModel.java
│   │   ├── GraphResult.java
│   │   └── KruskalAlgorithm.java
│   ├── greedy
│   │   ├── CoinChangeGreedy.java
│   │   ├── GreedyModel.java
│   │   └── TravelingSalesmanGreedy.java
│   ├── mochila
│   │   └── MochilaAdapter.java
│   ├── probability
│   │   └── ProbabilityModel.java
│   └── sorting
│       ├── SortingModel.java
│       ├── SortingRunner.java
│       └── algorithms
│           ├── BubbleSort.java
│           ├── HeapSort.java
│           ├── InsertionSort.java
│           ├── MergeSort.java
│           ├── QuickSort.java
│           └── SelectionSort.java
└── view
  ├── ActividadesView.java
  ├── MainView.java
  ├── semana2
  │   ├── BigOView.java
  │   └── SortingView.java
  ├── semana3
  │   ├── CoinChangeView.java
  │   ├── GreedyView.java
  │   └── TravelingSalesmanView.java
  ├── semana4
  ├── semana5
  └── semana6
```

## Ejecución rápida (Windows)

La forma más simple en Windows es usar el script incluido `run.bat`. Desde PowerShell (en la raíz del repositorio):

```powershell
# Ejecutar el script (doble clic en el Explorador de archivos también funciona)
.\run.bat
```

Si el script no funciona o prefieres compilar/ejecutar manualmente, usa las instrucciones siguientes.

## Ejecutar desde un IDE

1. Importa el proyecto en tu IDE favorito como un proyecto Java (sin necesidad de Maven/Gradle si no usas esos build tools).
2. Marca `src/main/java` como carpeta de código fuente (source root) si el IDE lo solicita.
3. Ejecuta la clase `com.algoritmoscurso.Main` como aplicación Java.

## Compilar y ejecutar desde línea de comandos (PowerShell)

Estas instrucciones asumen que `javac` y `java` están en el PATH (JDK instalado).

```powershell
# Desde la raíz del repositorio
cd .\src\main\java

# Crear carpeta para clases compiladas
New-Item -ItemType Directory -Path ..\..\..\out -Force | Out-Null

# Compilar todos los .java recursivamente y colocar los .class en el directorio out
Get-ChildItem -Path . -Recurse -Filter '*.java' | ForEach-Object { javac -d ..\..\..\out $_.FullName }

# Volver a la raíz y ejecutar
cd ..\..\..
java -cp out com.algoritmoscurso.Main
```

Notas:
- Si hay muchos archivos a compilar el comando `ForEach-Object` llamará a `javac` por archivo; en máquinas con JDK moderno puedes compilar por lotes (por ejemplo, generar una lista y pasarla a `javac @files.txt`) para mejorar rendimiento.

## Notas y solución de problemas

- Error: clase principal no encontrada — Asegúrate de que la compilación se haya realizado en el directorio `out` y que la estructura de paquetes esté preservada (`com/algoritmoscurso/Main.class`).
- Problemas de versión de Java — confirma la versión con `java -version`.
- Si usas Linux/macOS, adapta las rutas y comandos (`/` en lugar de `\`, y `mkdir` en lugar de `New-Item`).

## Cómo contribuir

- Reporta issues o errores usando el sistema de issues del repositorio.
- Para mejoras o PRs: fork -> nueva rama -> commits claros -> Pull Request.
- Sigue el estilo y la convención de nombres presentes en el código; añade tests o ejemplos pequeños cuando agregues algoritmos.

## Miembros / Contacto

- Ramos Ynca, Alvaro Omar — N00397208 — N00397208@UPN.PE
- Rojas Marin, Wilder Enrique — N00487018 — N00487018@UPN.PE
- Carlos Castro, Roger Percy — N00386640 — N00386640@UPN.PE
- Cornejo Arcaya, Luis Alberto — N00255638 — N00255638@UPN.PE
