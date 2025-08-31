# CodeStrateg8 — Proyecto del Grupo 8

Proyecto educativo en Java (Análisis de Algoritmos) desarrollado por el Grupo 8.

Integrantes:

- Ramos Ynca, Alvaro Omar — N00397208 — N00397208@UPN.PE
- Rojas Marin, Wilder Enrique — N00487018 — N00487018@UPN.PE
- Carlos Castro, Roger Percy — N00386640 — N00386640@UPN.PE
- Cornejo Arcaya, Luis Alberto — N00255638 — N00255638@UPN.PE

Descripción breve:

Proyecto que organiza las prácticas de campo usando el patrón MVC.

Ejecución rápida:

- En Windows: ejecutar `run.bat` (doble clic o PowerShell `./run.bat`).
- Desde IDE: ejecutar la clase `com.algoritmoscurso.Main`.

Estructura relevante (resumen de carpetas):

```text
.
├── README.md
├── run.bat
└── src
	└── main
		└── java
			└── com
				└── algoritmoscurso
					├── Main.java
					├── assets
					│   └── img
					│       └── upn_icon.png
					├── controller
					│   ├── ApplicationController.java
					│   ├── BigOController.java
					│   └── SortingController.java
					├── interfaces
					│   ├── IBigOExample.java
					│   ├── IController.java
					│   ├── IModel.java
					│   ├── ISortingAlgorithm.java
					│   └── IView.java
					├── model
					│   ├── ApplicationModel.java
					│   ├── bigo
					│   │   ├── BigOExamples.java
					│   │   └── BigOModel.java
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
						└── semana2
							├── BigOView.java
							└── SortingView.java
```
