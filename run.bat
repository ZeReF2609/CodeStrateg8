@echo off

echo Compilando aplicacion con Java 8...

rem Crear directorio de salida si no existe
if not exist "out" mkdir out

rem Generar lista de archivos fuente (sources.txt)
if exist sources.txt del sources.txt >nul 2>&1
for /R "src\main\java" %%f in (*.java) do @echo %%f>>sources.txt

rem Compilar siempre para Java 8
echo Compilando con --release 8 ...
javac --release 8 -d out -cp "src\main\java" @sources.txt

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ==========================================
    echo COMPILACION EXITOSA!
    echo ==========================================
    echo Ejecutando aplicacion...
    echo.
    java -cp out com.algoritmoscurso.Main
    del sources.txt >nul 2>&1
) else (
    echo Error en la compilacion!
    del sources.txt >nul 2>&1
    pause
)
